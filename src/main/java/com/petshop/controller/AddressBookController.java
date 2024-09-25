package com.petshop.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.petshop.common.BaseContext;
import com.petshop.common.Result;
import com.petshop.pojo.AddressBook;
import com.petshop.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * The type Address book controller.
 */
@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * Save r.
     *
     * @param addressBook the address book
     * @return the r
     */
    @PostMapping
    public Result save(@RequestBody AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookService.save(addressBook);
        return Result.success(addressBook);


    }

    /**
     * Sets default.
     *
     * @param addressBook the address book
     * @return the default
     */
    @PutMapping("/default")
    public Result setDefault(@RequestBody AddressBook addressBook) {

        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        wrapper.set(AddressBook::getIsDefault, 0);

        addressBookService.update(wrapper);

        addressBook.setIsDefault(1);

        addressBookService.updateById(addressBook);
        return Result.success(addressBook);
    }

    /**
     * Get r.
     *
     * @param id the id
     * @return the r
     */
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return Result.success(addressBook);
        } else {
            return Result.error("没有找到该对象");
        }
    }

    /**
     * Gets default.
     *
     * @return the default
     */
    @GetMapping("/default")
    public Result getDefault() {
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        queryWrapper.eq(AddressBook::getIsDefault, 1);

        AddressBook addressBook = addressBookService.getOne(queryWrapper);

        if (null == addressBook) {
            return Result.error("没有找到该对象");
        } else {
            return Result.success(addressBook);
        }
    }

    /**
     * 查询指定用户的全部地址
     *
     * @param addressBook the address book
     * @return the r
     */
    @GetMapping("/list")
    public Result list(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());



        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != addressBook.getUserId(), AddressBook::getUserId, addressBook.getUserId());
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);

        return Result.success(addressBookService.list(queryWrapper));
    }
}
