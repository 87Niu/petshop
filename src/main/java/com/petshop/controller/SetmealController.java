package com.petshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petshop.common.Result;
import com.petshop.dto.SetmealDto;
import com.petshop.pojo.Category;
import com.petshop.pojo.Setmeal;
import com.petshop.service.CategoryService;
import com.petshop.service.SetmealDishService;
import com.petshop.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;

    @PostMapping
    public Result save(@RequestBody SetmealDto setmealDto) {
        setmealService.saveWithDish(setmealDto);
        return Result.success("套餐添加成功");
    }


    @GetMapping("/page")
    public Result page(int page, int pageSize, @RequestParam(value = "name", required = false) String name) {
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null, Setmeal::getName, name);

        wrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo, wrapper);

        BeanUtils.copyProperties(pageInfo, dtoPage.getRecords(), "records");

        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);
        return Result.success(dtoPage);
    }

    @PostMapping("/status/0")
    public Result stop(@RequestParam List<Long> ids) {
        for (Long id : ids) {
            Setmeal setmeal=setmealService.getById(id);
            setmeal.setStatus(0);
            LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Setmeal::getId, id);
            setmealService.update(setmeal, lambdaQueryWrapper);
        }
        return Result.success("更新状态为停售");
    }

    @PostMapping("/status/1")
    public Result startMail(@RequestParam List<Long> ids) {
        for (Long id : ids) {
            Setmeal setmeal=setmealService.getById(id);
            setmeal.setStatus(1);
            LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Setmeal::getId, id);
            setmealService.update(setmeal, lambdaQueryWrapper);
        }
        return Result.success("更新状态为起售");
    }

    @DeleteMapping()
    public Result deleteSetmeal(@RequestParam List<Long> ids){
        setmealService.removeWithDish(ids);
        return Result.success("删除成功");
    }

    @GetMapping("/list")
    public Result list(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        wrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        wrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(wrapper);
        return Result.success(list);
    }


    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        Setmeal setmeal = setmealService.getById(id);
        return Result.success(setmeal);
    }

}

