package com.petshop.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.petshop.common.Result;
import com.petshop.pojo.Employee;
import jakarta.servlet.http.HttpServletRequest;

public interface EmployeeService extends IService<Employee> {

    Result login(HttpServletRequest request, Employee employee);

    Result logout(HttpServletRequest request);

    Result saveEmployee(Employee employee);

    Result EpageInfo(int page, int pageSize, String name);
}
