package tacos.client;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import tacos.entity.Ingredient;

/**
 * @author Sui
 * @date 2019.04.22 14:48
 */
@Slf4j
@Component
public class RestClient {

    private RestTemplate restTemplate;
    @Autowired
    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * get请求两种重载
     * @param ingredientId
     * @return
     */
    public Ingredient getIngredientById(String ingredientId) {
        //path variable按照顺序写入url中
        //也可以使用map指定key值传入参数
        /*Map<String,String> urlVariables = new HashMap<>();
        urlVariables.put("id", ingredientId);*/
        return restTemplate.getForObject("http://localhost:8888/ingredients/{id}", Ingredient.class, ingredientId);
    }

    public Ingredient getIngredientByIdWithEntity(String ingredientId) {
        //使用getForEntity获取ResponseEntity对象, 可以获得相应头等额外信息
        ResponseEntity<Ingredient> responseEntity =
                restTemplate.getForEntity("http://localhost:8080/ingredients/{id}",
                        Ingredient.class, ingredientId);
        log.info("Fetched time: " + responseEntity.getHeaders().getDate());
        return responseEntity.getBody();
    }

    /**
     * 更新
     * @param ingredient
     */
    public void updateIngredient(Ingredient ingredient) {
        restTemplate.put("http://localhost:8080/ingredients/{id}", ingredient, ingredient.getId());
    }

    /**
     * 删除
     * @param ingredient
     */
    public void deleteIngredient(Ingredient ingredient) {
        restTemplate.delete("http://localhost:8080/ingredients/{id}", ingredient.getId());
    }

    /**
     * 创建请求三种重载
     * @param ingredient
     * @return
     */
    public Ingredient createIngredient(Ingredient ingredient) {
        return restTemplate.postForObject("http://localhost:8080/ingredients", ingredient, Ingredient.class);
    }

    public URI createIngredientForLocation(Ingredient ingredient) {
        return restTemplate.postForLocation("http://localhost:8080/ingredients", ingredient);
    }

    public Ingredient createIngredientForEntity(Ingredient ingredient) {
        ResponseEntity<Ingredient> responseEntity =
                restTemplate.postForEntity("http://localhost:8080/ingredients",
                        ingredient,
                        Ingredient.class);
        log.info("New resource created at " +
                responseEntity.getHeaders().getLocation());
        return responseEntity.getBody();
    }
}
