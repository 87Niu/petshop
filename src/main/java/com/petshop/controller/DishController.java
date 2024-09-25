package com.petshop.controller;

import com.petshop.common.Result;
import com.petshop.dto.DishDto;
import com.petshop.pojo.Dish;
import com.petshop.service.CategoryService;
import com.petshop.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    // 新增商品信息
    @PostMapping
    public Result save(@RequestBody DishDto dishdto) {
        dishService.save(dishdto);
        return Result.success("新增商品成功");
    }

    // 按照分页进行浏览
    @GetMapping("/page")
    public Result page(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize, @RequestParam(value = "name", required = false)  String name) {
        log.info("{} {} {}", page, pageSize, name);
        return dishService.DPageInfo(page, pageSize, name);
    }

    // 按照id进行查询 需要同步其他信息
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        Dish dish = dishService.getById(id);
        return Result.success(dish);
    }

    @PutMapping
    public Result update(@RequestBody DishDto dishDto) {
        dishService.updateById(dishDto);

//        String keys = "dish_" + dishDto.getCategoryId() + "_1";
//        redisTemplate.delete(keys);
        return Result.success("修改商品成功");
    }

    // 停售
    @PostMapping("/status/0")
    public Result stop(@RequestParam List<Long> ids) {
        return dishService.stopSell(ids);

    }

    @PostMapping("/status/1")
    public Result start(@RequestParam List<Long> ids) {
        return dishService.start(ids);

    }

    @GetMapping("/list")
    public Result list(Dish dish){
        return dishService.dishlist(dish);
    }


    @Transactional
    @DeleteMapping
    public  Result delete(@RequestParam String[] ids) {
        dishService.removeByIds(Arrays.asList(ids));
        return Result.success("删除成功");
    }




}
