package com.petshop.dto;


import com.petshop.pojo.Dish;
import lombok.Data;

@Data
public class DishDto extends Dish {

    private String categoryName;

    private Integer copies;
}
