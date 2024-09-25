package com.petshop.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.petshop.common.Result;
import com.petshop.pojo.Category;

public interface CategoryService extends IService<Category> {

    Result PageInfo(int page, int pageSize);

    void remove(Long ids);

    Result Clist(Category category);
}
