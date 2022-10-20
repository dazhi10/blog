package com.nhb.domain.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 操作日志表(Log)表实体类
 *
 * @author 大只
 * @since 2022-10-20 16:36:56
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("operation_log")
public class OperationLog {
    @TableId
    private Integer id;

    //操作描述
    private String operateDescribe;
    //请求URL
    private String url;
    //请求方式
    private String requestMethod;
    //请求的类名
    private String operateMethod;
    //请求IP
    private String ip;
    //请求参数
    private String requestParam;
    //返回结果
    private String responseData;
    //操作账号
    private String operateBy;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}


