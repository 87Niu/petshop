package com.petshop.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.petshop.pojo.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}
