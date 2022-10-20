package com.nhb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.domain.ResponseResult;
import com.nhb.domain.entity.OperationLog;
import com.nhb.domain.vo.PageVo;
import com.nhb.mapper.OperationLogMapper;
import com.nhb.service.OperationLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 操作日志表(Log)表服务实现类
 *
 * @author 大只
 * @since 2022-10-20 16:36:58
 */
@Service("operationLogService")
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    @Override
    public ResponseResult<PageVo> pageOperationLogList(Integer pageNum, Integer pageSize, String operateDescribe) {
        LambdaQueryWrapper<OperationLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Objects.nonNull(operateDescribe), OperationLog::getOperateDescribe, operateDescribe);
        queryWrapper.orderByDesc(OperationLog::getCreateTime);
        Page<OperationLog> operationLogPage = new Page<>(pageNum, pageSize);
        page(operationLogPage, queryWrapper);
        PageVo pageVo = new PageVo(operationLogPage.getRecords(), operationLogPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult deleteOperationLog(List<Long> ids) {
        removeByIds(ids);
        return ResponseResult.okResult();
    }
}
