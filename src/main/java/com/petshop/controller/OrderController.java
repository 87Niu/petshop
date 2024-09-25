package com.petshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petshop.common.BaseContext;
import com.petshop.common.Result;
import com.petshop.pojo.Orders;
import com.petshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    public Result submit(@RequestBody Orders orders){
        orderService.submit(orders);
        return Result.success("下单成功");
    }

    @GetMapping("/userPage")
    public Result getPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize){
        Page pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Orders::getUserId, BaseContext.getCurrentId());
        queryWrapper.orderByDesc(Orders::getOrderTime);
        orderService.page(pageInfo, queryWrapper);
        return Result.success(pageInfo);
    }

    @GetMapping("/page")
    public Result pageinfo(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        Page pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Orders::getUserId, BaseContext.getCurrentId());
        queryWrapper.orderByDesc(Orders::getOrderTime);
        orderService.page(pageInfo, queryWrapper);
        return Result.success(pageInfo);
    }
}