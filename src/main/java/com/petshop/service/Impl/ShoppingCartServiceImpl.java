package com.petshop.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petshop.mapper.ShoppingCartMapper;
import com.petshop.pojo.ShoppingCart;
import com.petshop.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
