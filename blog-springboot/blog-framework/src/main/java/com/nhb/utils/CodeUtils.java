package com.nhb.utils;

import com.nhb.domain.entity.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 大只
 * @date 2022/10/7 17:50
 */
@Component
public class CodeUtils {

    @Resource
    JavaMailSender mailSender;

    @Resource
    RedisTemplate<String, User> redisTemplate;

    // 生成链接,并给接收的邮箱发送邮件
    public boolean sendCode(User user){
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);
            String token = UUID.randomUUID().toString(); // 生成UUID
            redisTemplate.opsForValue().set(token,user);
            redisTemplate.expire(token,300, TimeUnit.SECONDS);
            messageHelper.setFrom("发送方的邮箱地址"); //发送方的邮箱地址，而不是接收方的邮箱地址
            messageHelper.setTo(user.getUserName()); // 接收方的邮箱地址
            messageHelper.setSubject("注册");  // 邮箱标题
            String html = "<html>\n" +
                    "<body>\n" +
                    "<p>请点击下方链接注册</p>\n" +
                    "<a href=\"http://localhost:8081/lookCode/"+token+"\">http://localhost:8081/lookCode/"+token+"</a>" +
                    "</body>\n" +
                    "</html>";
            messageHelper.setText(html,true); // 邮箱内容
            mailSender.send(message);  // 发送邮箱
            System.out.println("发送成功");
            return true;
        }catch (Exception e){
            System.out.println("发送失败");
            return false;
        }
    }

    // 判断token是否过期
    public boolean eqToken(String token){
        return redisTemplate.hasKey(token);
    }

    // 根据token查询用户的信息
    public User findUser(String token){
        return redisTemplate.opsForValue().get(token);
    }

}
