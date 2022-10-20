package com.nhb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nhb.domain.ResponseResult;
import com.nhb.domain.entity.OperationLog;
import com.nhb.domain.vo.PageVo;

import java.util.List;


/**
 * 操作日志表(Log)服务接口
 *
 * @author 大只
 * @since 2022-10-20 16:36:58
 */
public interface OperationLogService extends IService<OperationLog> {

    /**
     * 查看日志列表
     *
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param operateDescribe 描述内容
     */
    ResponseResult<PageVo> pageOperationLogList(Integer pageNum, Integer pageSize, String operateDescribe);

    /**
     * 删除日志
     * @param ids id集
     */
    ResponseResult deleteOperationLog(List<Long> ids);
}
