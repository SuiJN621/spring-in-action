package tacos.client;

import java.net.URI;
import java.util.Collection;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;

import tacos.entity.Ingredient;
import tacos.entity.Taco;

/**
 * @author Sui
 * @date 2019.04.22 15:12
 */
public class TraversonClient {

    public static void main(String[] args) {
        //传入超媒体根路径创建Traverson
        Traverson traverson = new Traverson(URI.create("http://localhost:8888/api"), MediaTypes.HAL_JSON);

        //ParameterizedTypeReference用于在runtime中获取对象泛型类型, 一般使用方式是创建内名类
        ParameterizedTypeReference<Resources<Ingredient>> ingredientType =
                new ParameterizedTypeReference<Resources<Ingredient>>() {};

        Resources<Ingredient> ingredientRes = traverson
                //请求link名称为指定名称的资源
                .follow("ingredients")
                //将资源转换为指定类型
                .toObject(ingredientType);
        Collection<Ingredient> ingredients = ingredientRes.getContent();


        ParameterizedTypeReference<Resources<Taco>> tacoType =
                new ParameterizedTypeReference<Resources<Taco>>() {};
        Resources<Taco> tacoRes = traverson
                        .follow("tacos")
                        .follow("recents")
                        .toObject(tacoType);
        Collection<Taco> tacos = tacoRes.getContent();

    }
}
