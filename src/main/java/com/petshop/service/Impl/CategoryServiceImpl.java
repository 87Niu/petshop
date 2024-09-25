package com.petshop.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petshop.common.CustomException;
import com.petshop.common.Result;
import com.petshop.mapper.CategoryMapper;
import com.petshop.pojo.Category;
import com.petshop.pojo.Dish;
import com.petshop.pojo.Setmeal;
import com.petshop.service.CategoryService;
import com.petshop.service.DishService;
import com.petshop.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    // 分页浏览信息
    @Override
    public Result PageInfo(int page, int pageSize) {
        Page<Category> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();

        wrapper.orderByAsc(Category::getSort);
        wrapper.orderByDesc(Category::getUpdateTime);

        this.page(pageInfo, wrapper);
        return Result.success(pageInfo);
    }

    // 按照id进行删除，需要判断是否有其他表连接该记录
    @Override
    public void remove(Long ids) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dish::getCategoryId, ids);

        int count = (int) dishService.count(wrapper);
        if (count > 0) {
            throw new CustomException("当前分类下有菜品,无法删除");
        }

        LambdaQueryWrapper<Setmeal> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Setmeal::getCategoryId, ids);

        int count2 = (int) setmealService.count(wrapper1);
        if (count2 > 0) {
            throw new CustomException("当前分类下有套餐,无法删除");
        }
        super.removeById(ids);
    }

    @Override
    public Result Clist(Category category) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(category.getType() != null, Category::getType, category.getType());
        wrapper.orderByAsc(Category::getSort).orderByAsc(Category::getUpdateTime);
        List<Category> list = this.list(wrapper);
        return Result.success(list);
    }


}
