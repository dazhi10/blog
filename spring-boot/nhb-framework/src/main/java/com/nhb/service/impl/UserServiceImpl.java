package com.nhb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.domain.ResponseResult;
import com.nhb.domain.entity.User;
import com.nhb.domain.entity.UserRole;
import com.nhb.domain.vo.PageVo;
import com.nhb.domain.vo.UserInfoVo;
import com.nhb.domain.vo.UserVo;
import com.nhb.enums.AppHttpCodeEnum;
import com.nhb.exception.SystemException;
import com.nhb.mapper.UserMapper;
import com.nhb.service.UserRoleService;
import com.nhb.service.UserService;
import com.nhb.utils.BeanCopyUtils;
import com.nhb.utils.RandomNum;
import com.nhb.utils.RedisCache;
import com.nhb.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户表(SysUser)表服务实现类
 *
 * @author 大只
 * @since 2022-10-01 01:47:48
 */
@Service("sysUserService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    JavaMailSender mailSender;

    @Autowired
    RedisCache redisCache;

    @Value("${spring.mail.username}")
    private String mailKey;



    @Autowired
    private UserRoleService userRoleService;

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }


    @Override
    public ResponseResult register(User user) {
        //从redis中取出验证码
        String code = redisCache.getCacheObject(user.getUserName());

        if (Objects.isNull(code)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.CODE_PAST_ERROR);
        }
        //对比验证码
        if (!code.equals(user.getCode())) {
            throw new SystemException(AppHttpCodeEnum.CODE_ERROR);
        }
        //用户随机昵称
        StringBuffer randomNum = RandomNum.getRandomNum(6);
        user.setNickName("用户" + randomNum);
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        boolean save = save(user);
        if(save){
            return ResponseResult.okResult("注册成功");
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

    }


    @Override
    public boolean checkUserNameUnique(String userName) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getUserName, userName)) == 0;
    }

    @Override
    public ResponseResult sendCode(String gmail) {
        //根据用户注册信息进行注册链接的的生成和发送
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);
            String mailId = String.valueOf(RandomNum.getRandomNum(6));
            //key存入redis中
            redisCache.setCacheObject(gmail,mailId);
            //key五分钟过期
            redisCache.expire(mailId, 300, TimeUnit.SECONDS);
            messageHelper.setFrom(mailKey); //发送方的邮箱地址，而不是接收方的邮箱地址
            messageHelper.setTo(gmail); // 接收方的邮箱地址
            messageHelper.setSubject("注册验证码");  // 邮箱标题
            String html = "<html>\n" +
                    "<body>\n" +
                    "<p>验证码5分钟内有效</p>\n" +
                    "<p>" + mailId + "</p>" +
                    "</body>\n" +
                    "</html>";
            messageHelper.setText(html, true); // 邮箱内容
            mailSender.send(message);  // 发送邮箱
            return ResponseResult.okResult("验证码发送成功,请前往邮箱查看~");

        } catch (Exception e) {
            return ResponseResult.errorResult(AppHttpCodeEnum.EMAIL_ERROR);
        }
    }

    @Override
    public ResponseResult selectUserPage(User user, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.like(StringUtils.hasText(user.getUserName()), User::getUserName, user.getUserName());
        queryWrapper.eq(StringUtils.hasText(user.getStatus()), User::getStatus, user.getStatus());

        Page<User> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        //转换成VO
        List<User> users = page.getRecords();
        List<UserVo> userVoList = users.stream()
                .map(u -> BeanCopyUtils.copyBean(u, UserVo.class))
                .collect(Collectors.toList());
        PageVo pageVo = new PageVo();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(userVoList);
        return ResponseResult.okResult(pageVo);
    }


    @Override
    @Transactional
    public ResponseResult addUser(User user) {
        //密码加密处理
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);

        if (user.getRoleIds() != null && user.getRoleIds().length > 0) {
            insertUserRole(user);
        }
        return ResponseResult.okResult();
    }

    @Override
    public void updateUser(User user) {
        // 删除用户与角色关联
        LambdaQueryWrapper<UserRole> userRoleUpdateWrapper = new LambdaQueryWrapper<>();
        userRoleUpdateWrapper.eq(UserRole::getUserId, user.getId());
        userRoleService.remove(userRoleUpdateWrapper);

        // 新增用户与角色管理
        insertUserRole(user);
        // 更新用户信息
        updateById(user);
    }


    private void insertUserRole(User user) {
        List<UserRole> sysUserRoles = Arrays.stream(user.getRoleIds())
                .map(roleId -> new UserRole(user.getId(), roleId)).collect(Collectors.toList());
        userRoleService.saveBatch(sysUserRoles);
    }
}
