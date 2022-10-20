package com.nhb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nhb.domain.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;


/**
 * 操作日志表(Log)表数据库访问层
 *
 * @author 大只
 * @since 2022-10-20 16:36:56
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {

}


