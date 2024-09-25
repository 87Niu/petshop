package com.petshop;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@MapperScan("com.petshop.mapper")
@SpringBootApplication
public class PetShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetShopApplication.class, args);
        log.info("项目启动成功");
    }
}
