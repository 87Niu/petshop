package com.petshop.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.petshop.common.Result;
import com.petshop.pojo.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {



    Result DPageInfo(int page, int pageSize, String name);
    

    Result stopSell(List<Long> ids);

    Result start(List<Long> ids);

    Result dishlist(Dish dish);
}
