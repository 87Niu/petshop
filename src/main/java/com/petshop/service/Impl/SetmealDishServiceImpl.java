package com.petshop.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petshop.mapper.SetmealDishMapper;
import com.petshop.pojo.SetmealDish;
import com.petshop.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
