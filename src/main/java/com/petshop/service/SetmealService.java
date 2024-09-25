package com.petshop.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.petshop.dto.SetmealDto;
import com.petshop.pojo.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);


    public void removeWithDish(List<Long> ids);
}
