package com.petshop.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.petshop.pojo.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
