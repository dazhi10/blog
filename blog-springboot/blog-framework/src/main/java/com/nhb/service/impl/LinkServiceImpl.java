package com.nhb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.constant.SystemConstant;
import com.nhb.entity.Link;
import com.nhb.entity.domain.ResponseResult;
import com.nhb.mapper.LinkMapper;
import com.nhb.service.LinkService;
import com.nhb.utils.BeanCopyUtils;
import com.nhb.vo.LinkVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author 大只
 * @since 2022-10-01 00:52:08
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的友链
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstant.LINK_STATUS_NORMAL);
        List<Link> list = list(queryWrapper);
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(list, LinkVo.class);
        //封装vo
        return ResponseResult.okResult(linkVos);
    }
}
