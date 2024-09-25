package com.petshop.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.petshop.common.Result;
import com.petshop.pojo.User;
import com.petshop.service.UserService;
import com.petshop.utils.ValidateCodeUtils;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/sendMsg")
    public Result sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();

        if(StringUtils.hasLength(phone)){
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);

            //调用阿里云提供的短信服务API完成发送短信
            //SMSUtils.sendMessage("圣灵宠物店","",phone,code);

            //需要将生成的验证码保存到redis
            session.setAttribute(phone,code);

            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);

            return Result.success("手机验证码短信发送成功");
        }

        return Result.error("短信发送失败");
    }

    @PostMapping("/login")
    public Result login(HttpSession session, @RequestBody Map map) {

        String phone = map.get("phone").toString();

        String code = map.get("code").toString();

        Object codeInSession = redisTemplate.opsForValue().get(phone);
        if (codeInSession != null && codeInSession.equals(code)) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);

            User user = userService.getOne(queryWrapper);

            if (user == null) {
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());

            redisTemplate.delete(phone);

            return Result.success(user);

        }
        return Result.error("登录失败");
    }


    @PostMapping("/loginout")
    public Result loginout(HttpSession session) {
        session.removeAttribute("user");
        return Result.success("退出成功");
    }





}
