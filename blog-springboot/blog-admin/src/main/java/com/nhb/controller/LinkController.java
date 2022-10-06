package com.nhb.controller;

import com.nhb.domain.ResponseResult;
import com.nhb.domain.entity.Link;
import com.nhb.service.LinkService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 大只
 * @date 2022/10/6 20:31
 */
@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @ApiOperation("查看友联列表")
    @GetMapping("/list")
    public ResponseResult listLink(Integer pageNum, Integer pageSize, @RequestParam(required = false) String name, @RequestParam(required = false) String status){
        return linkService.listLink(pageNum,pageSize,name,status);
    }


    @ApiOperation("新增友联")
    @PostMapping
    public ResponseResult saveLink(@RequestBody Link link){
        linkService.save(link);
        return ResponseResult.okResult();
    }

    @ApiOperation("根据id查询友联")
    @GetMapping("/{id}")
    public ResponseResult linkById(@PathVariable Long id){
        return ResponseResult.okResult(linkService.getById(id));
    }

    @ApiOperation("修改友联")
    @PutMapping
    public ResponseResult updateLink(@RequestBody Link link){
        linkService.updateById(link);
        return ResponseResult.okResult();
    }

    @ApiOperation("删除友联")
    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable Long id){
        linkService.removeById(id);
        return ResponseResult.okResult();
    }
}
