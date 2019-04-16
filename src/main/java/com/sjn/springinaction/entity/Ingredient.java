package com.sjn.springinaction.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Sui
 * @date 2019.04.15 16:34
 */
@Data
@RequiredArgsConstructor
public class Ingredient {

    private final String id;
    private final String name;
    private final Type type;

    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
