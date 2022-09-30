package com.nhb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nhb.domain.entity.Link;
import com.nhb.domain.ResponseResult;


/**
 * 友链(Link)服务接口
 *
 * @author 大只
 * @since 2022-10-01 00:52:08
 */
public interface LinkService extends IService<Link> {

    /**
     * 查看友链列表
     * @return ResponseResult
     */
    ResponseResult getAllLink();
}
