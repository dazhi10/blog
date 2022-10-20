package com.nhb.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.nhb.annotation.SystemLog;
import com.nhb.domain.ResponseResult;
import com.nhb.domain.entity.Category;
import com.nhb.domain.vo.ExcelCategoryVo;
import com.nhb.enums.AppHttpCodeEnum;
import com.nhb.service.CategoryService;
import com.nhb.utils.BeanCopyUtils;
import com.nhb.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 分类表(Category)控制层
 *
 * @author 大只
 * @since 2022-10-03 17:18:15
 */
@RestController
@RequestMapping("/content/category")
@Api(tags = "分类模块")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation("查看所有分类列表")
    @PreAuthorize("@ps.hasPermission('category:query')")
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory() {
        return categoryService.listAllCategory();
    }

    @ApiOperation("导出分类表格")
    @PreAuthorize("@ps.hasPermission('category:query')")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx", response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    @ApiOperation("分页查看分类列表")
    @PreAuthorize("@ps.hasPermission('category:query')")
    @GetMapping("/list")
    public ResponseResult listCategory(Integer pageNum, Integer pageSize, @RequestParam(required = false) String name, @RequestParam(required = false) String status) {
        return categoryService.listCategory(pageNum, pageSize, name, status);
    }

    @ApiOperation("新增分类")
    @SystemLog(businessName = "新增分类")
    @PreAuthorize("@ps.hasPermission('category:add')")
    @PostMapping
    public ResponseResult saveCategory(@RequestBody Category category) {
        categoryService.save(category);
        return ResponseResult.okResult();
    }

    @ApiOperation("根据id查询分类")
    @PreAuthorize("@ps.hasPermission('category:query')")
    @GetMapping("/{id}")
    public ResponseResult categoryById(@PathVariable Long id) {
        return ResponseResult.okResult(categoryService.getById(id));
    }

    @ApiOperation("修改分类")
    @SystemLog(businessName = "修改分类")
    @PreAuthorize("@ps.hasPermission('category:put')")
    @PutMapping
    public ResponseResult updateCategory(@RequestBody Category category) {
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }

    @ApiOperation("删除分类")
    @SystemLog(businessName = "删除分类")
    @PreAuthorize("@ps.hasPermission('category:delete')")
    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable Long id) {
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }

}

