package com.petshop.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petshop.mapper.AddressBookMapper;
import com.petshop.pojo.AddressBook;
import com.petshop.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
