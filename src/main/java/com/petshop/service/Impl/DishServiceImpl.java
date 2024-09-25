package com.petshop.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petshop.common.Result;
import com.petshop.dto.DishDto;
import com.petshop.mapper.CategoryMapper;
import com.petshop.mapper.DishMapper;
import com.petshop.pojo.Category;
import com.petshop.pojo.Dish;
import com.petshop.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Result DPageInfo(int page, int pageSize, String name) {

        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Dish::getName, name);

        queryWrapper.orderByDesc(Dish::getUpdateTime);

        this.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
//        return Result.success(pageInfo);

        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map((item)-> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryMapper.selectById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);
        return Result.success(dishDtoPage);
    }

    @Override
    public Result stopSell(List<Long> ids) {
        for (Long id : ids) {
            LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Dish::getId, id);
            Dish dish = this.getOne(queryWrapper);
            dish.setStatus(0);
            this.updateById(dish);
        }
        return Result.success("停售成功");
    }

    @Override
    public Result start(List<Long> ids) {
        for (Long id : ids) {
            LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Dish::getId, id);
            Dish dish = this.getOne(queryWrapper);
            dish.setStatus(1);
            this.updateById(dish);
            this.updateById(dish);
        }
        return Result.success("启售成功");
    }

    @Override
    public Result dishlist(Dish dish) {
        List<DishDto> dishDtoList = null;
        String key = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();

//        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);

//        if (dishDtoList != null) {
//
//            return R.success(dishDtoList);
//        }


        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null ,Dish::getCategoryId,dish.getCategoryId());
        //添加条件，查询状态为1（起售状态）的菜品
        queryWrapper.eq(Dish::getStatus,1);

        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);


        List<Dish> list = this.list(queryWrapper);

        dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryMapper.selectById(categoryId);

            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            return dishDto;
        }).collect(Collectors.toList());


//        redisTemplate.opsForValue().set(key, dishDtoList, 60, TimeUnit.MINUTES);
        return Result.success(dishDtoList);
    }


}
