package tacos.entity.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import tacos.controller.restful_api.RestDesignTacoController;
import tacos.entity.Ingredient;

/**
 * @author Sui
 * @date 2019.04.19 16:19
 */
public class IngredientResourceAssembler extends ResourceAssemblerSupport<Ingredient, IngredientResource> {

    public IngredientResourceAssembler() {
        super(RestDesignTacoController.class, IngredientResource.class);
    }

    @Override
    public IngredientResource toResource(Ingredient ingredient) {
        return createResourceWithId(ingredient.getId(), ingredient);
    }
    @Override
    protected IngredientResource instantiateResource(
            Ingredient ingredient) {
        return new IngredientResource(ingredient);
    }
}
