package com.petshop.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petshop.common.CustomException;
import com.petshop.dto.SetmealDto;
import com.petshop.mapper.SetmealMapper;
import com.petshop.pojo.Setmeal;
import com.petshop.pojo.SetmealDish;
import com.petshop.service.SetmealDishService;
import com.petshop.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;


    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);

        List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes();

        setmealDishList.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishList);

    }

    @Override
    public void removeWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> querWrater = new LambdaQueryWrapper<>();
        querWrater.eq(Setmeal::getId, ids);
        querWrater.eq(Setmeal::getStatus, 1);

        int count = (int) this.count(querWrater);

        if (count > 0) {
            throw new CustomException("套餐正在售卖中, 不能删除");
        }
        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SetmealDish::getSetmealId, ids);

        setmealDishService.remove(queryWrapper);
    }


}
