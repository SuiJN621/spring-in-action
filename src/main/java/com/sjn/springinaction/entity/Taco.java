package com.sjn.springinaction.entity;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * @author Sui
 * @date 2019.04.16 9:43
 */
@Data
public class Taco {

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<String> ingredients;
}
