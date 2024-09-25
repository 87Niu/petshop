package com.petshop.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.petshop.common.Result;
import com.petshop.mapper.EmployeeMapper;
import com.petshop.pojo.Employee;
import com.petshop.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Override
    public Result login(HttpServletRequest request, Employee employee) {
        String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());

        Employee emp = Db.lambdaQuery(Employee.class).eq(Employee::getUsername, employee.getUsername()).one();

        if (emp == null || !emp.getPassword().equals(password)) {
            return Result.error("用户名或密码不正确");
        }
        if (emp.getStatus() == 0) {
            return Result.error("用户已经冻结");
        }

        request.getSession().setAttribute("employee", emp.getId());
        return Result.success(emp);
    }

    @Override
    public Result logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return Result.success("退出用户成功");
    }

    @Override
    public Result saveEmployee(Employee employee) {
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        this.save(employee);
        return Result.success(employee);
    }

    @Override
    public Result EpageInfo(int page, int pageSize, String name) {
        Page pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasLength(name), Employee::getName, name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        this.page(pageInfo, queryWrapper);
        return Result.success(pageInfo);
    }
}
