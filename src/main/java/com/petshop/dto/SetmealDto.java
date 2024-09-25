package com.petshop.dto;


import com.petshop.pojo.Setmeal;
import com.petshop.pojo.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
