package com.nhb.controller;

import com.nhb.annotation.SystemLog;
import com.nhb.domain.ResponseResult;
import com.nhb.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 友链(Link)控制层
 *
 * @author 大只
 * @since 2022-10-01 00:52:06
 */
@RestController
@RequestMapping("/link")
@Api(tags = "友链模块")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @SystemLog(businessName = "查看友链列表")
    @ApiOperation("查看友链列表")
    @GetMapping("/getAllLink")
    public ResponseResult getAllLink() {
        return linkService.getAllLink();
    }
}

