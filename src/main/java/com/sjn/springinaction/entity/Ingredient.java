package com.sjn.springinaction.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @author Sui
 * @date 2019.04.15 16:34
 */
@Data
//required arg包括final属性或约束(@NonNull修饰)属性
@RequiredArgsConstructor
//JPA需要调用无参构造方法, access设置生成构造方法为private, force会自动设置final属性为默认值(null/0/false), 否则会报错
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@Entity
public class Ingredient {

    @Id
    private final String id;
    private final String name;
    //默认枚举类型以int类型储存
    @Enumerated(EnumType.STRING)
    private final Type type;

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
