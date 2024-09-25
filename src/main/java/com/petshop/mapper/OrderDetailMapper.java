package com.petshop.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.petshop.pojo.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}