package com.nhb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nhb.domain.ResponseResult;
import com.nhb.domain.dto.TagListDto;
import com.nhb.domain.entity.Tag;
import com.nhb.domain.vo.PageVo;

import java.util.List;


/**
 * 标签(Tag)服务接口
 *
 * @author 大只
 * @since 2022-10-03 15:21:02
 */
public interface TagService extends IService<Tag> {

    /**
     * 查看标签列表
     * @param pageNum 页码
     * @param pageSize 每页个数
     * @param tagListDto 标签Dto
     * @return ResponseResult<PageVo>
     */
    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    /**
     * 新增标签
     * @return ResponseResult
     */
    ResponseResult saveTag(Tag tag);

    /**
     * 删除标签
     * @param ids 标签id列表
     * @return ResponseResult
     */
    ResponseResult deleteTag(List<Long> ids);

    /**
     * 修改标签
     * @param tag 标签请求体
     * @return ResponseResult
     */
    ResponseResult updateTag(Tag tag);

    /**
     * 查看单个标签
     * @param id 标签id
     * @return ResponseResult
     */
    ResponseResult getTag(Long id);
}
