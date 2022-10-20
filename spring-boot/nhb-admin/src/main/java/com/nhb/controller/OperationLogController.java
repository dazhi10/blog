package com.nhb.controller;

import com.nhb.annotation.SystemLog;
import com.nhb.domain.ResponseResult;
import com.nhb.domain.vo.PageVo;
import com.nhb.service.OperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 操作日志表(Log)控制层
 * @author 大只
 * @since 2022-10-20 16:36:56
 */
@RestController
@RequestMapping("/content/log")
@Api(tags = "日志模块")
public class OperationLogController {
    @Autowired
    private OperationLogService operationLogService;

    @ApiOperation("查看日志列表")
    @PreAuthorize("@ps.hasPermission('operationlog:query')")
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, String operateDescribe){
        return operationLogService.pageOperationLogList(pageNum,pageSize,operateDescribe);
    }

    @ApiOperation("删除日志")
    @SystemLog(businessName = "删除日志")
    @PreAuthorize("@ps.hasPermission('operationlog:delete')")
    @DeleteMapping("/{ids}")
    public ResponseResult deleteComment(@PathVariable List<Long> ids){
        return operationLogService.deleteOperationLog(ids);
    }

}

