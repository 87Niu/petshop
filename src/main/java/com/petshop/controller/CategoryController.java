package com.petshop.controller;

import com.petshop.common.Result;
import com.petshop.pojo.Category;
import com.petshop.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 保存分类信息
    @PostMapping
    public Result saveC(@RequestBody Category category) {
        categoryService.save(category);
        return Result.success("新增成功");
    }

    // 分页浏览
    @GetMapping("/page")
    public Result CpageInfo(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        return categoryService.PageInfo(page, pageSize);
    }

    //按照id进行删除，需要判断是否有其他记录连接
    @DeleteMapping
    public Result delete(@RequestParam("ids") Long ids) {
        categoryService.remove(ids);
        return  Result.success("菜品分类删除成功");
    }

    // 进行更新
    @PutMapping
    public Result update(@RequestBody Category category) {
        categoryService.updateById(category);
        return Result.success("修改成功");
    }

    // 主要是APP界面
    @GetMapping("/list")
    public Result list(Category category) {
        return categoryService.Clist(category);
    }

}
