package com.petshop.controller;

import com.petshop.common.Result;
import com.petshop.pojo.Employee;
import com.petshop.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The type Employee controller.
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Login result.
     *
     * @param request  the request
     * @param employee the employee
     * @return the result
     */
    @PostMapping("/login")
    public Result login(HttpServletRequest request, @RequestBody Employee employee) {
        return employeeService.login(request, employee);
    }

    /**
     * Logout result.
     *
     * @param request the request
     * @return the result
     */
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        return employeeService.logout(request);
    }

    /**
     * Save result.
     *
     * @param employee the employee
     * @return the result
     */
    @PostMapping
    public Result save(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/page")
    public Result page(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize, @RequestParam(value = "name", required = false) String name) {
        return employeeService.EpageInfo(page, pageSize, name);
    }

    @PutMapping
    public Result update(@RequestBody Employee employee) {
        employeeService.updateById(employee);
        return Result.success("修改成功");
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") int id) {
        Employee emp = employeeService.getById(id);
        if (emp != null) {
            return Result.success(emp);
        }
        return Result.error("没有该用户");
    }



}
