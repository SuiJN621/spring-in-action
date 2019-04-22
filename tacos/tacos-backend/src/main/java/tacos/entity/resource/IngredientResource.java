package tacos.entity.resource;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import tacos.entity.Ingredient;

/**
 * @author Sui
 * @date 2019.04.19 16:19
 */
public class IngredientResource extends ResourceSupport {

    @Getter
    private String name;
    @Getter
    private Ingredient.Type type;

    public IngredientResource(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }
}
