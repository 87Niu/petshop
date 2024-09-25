package com.petshop.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petshop.mapper.OrderDetailMapper;
import com.petshop.pojo.OrderDetail;
import com.petshop.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}