package com.nhb.controller;

import com.nhb.domain.ResponseResult;
import com.nhb.domain.dto.TagListDto;
import com.nhb.domain.entity.Tag;
import com.nhb.domain.vo.PageVo;
import com.nhb.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签(Tag)控制层
 * @author 大只
 * @since 2022-10-03 15:21:02
 */
@RestController
@RequestMapping("/content/tag")
@Api(tags = "标签模块")
public class TagController {
    @Autowired
    private TagService tagService;

    @ApiOperation("查看标签列表")
    @PreAuthorize("@ps.hasPermission('tag:query')")
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @ApiOperation("新增标签")
    @PreAuthorize("@ps.hasPermission('tag:add')")
    @PostMapping
    public ResponseResult saveTag(@RequestBody Tag tag){
        return tagService.saveTag(tag);
    }

    @ApiOperation("删除标签")
    @PreAuthorize("@ps.hasPermission('tag:delete')")
    @DeleteMapping("/{ids}")
    public ResponseResult deleteTag(@PathVariable List<Long> ids){
        return tagService.deleteTag(ids);
    }

    @ApiOperation("查看标签")
    @PreAuthorize("@ps.hasPermission('tag:query')")
    @GetMapping("/{id}")
    public ResponseResult getTag(@PathVariable Long id){
        return tagService.getTag(id);
    }

    @ApiOperation("修改标签")
    @PreAuthorize("@ps.hasPermission('tag:put')")
    @PutMapping
    public ResponseResult updateTag(@RequestBody Tag tag){
        return tagService.updateTag(tag);
    }

    @ApiOperation("查看所有标签")
    @PreAuthorize("@ps.hasPermission('tag:query')")
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }

}

