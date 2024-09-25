package com.petshop.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petshop.mapper.UserMapper;
import com.petshop.pojo.User;
import com.petshop.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
