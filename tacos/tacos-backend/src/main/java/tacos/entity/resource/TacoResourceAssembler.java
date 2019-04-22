package tacos.entity.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import tacos.controller.restful_api.RestDesignTacoController;
import tacos.entity.Taco;

/**
 * @author Sui
 * @date 2019.04.19 16:10
 */
public class TacoResourceAssembler extends ResourceAssemblerSupport<Taco, TacoResource> {

    public TacoResourceAssembler() {
        super(RestDesignTacoController.class, TacoResource.class);
    }

    /**
     * 初始化, 会被toResource调用
     * @param taco
     * @return
     */
    @Override
    protected TacoResource instantiateResource(Taco taco) {
        return new TacoResource(taco);
    }

    /**
     * 生成链接
     * @param taco
     * @return
     */
    @Override
    public TacoResource toResource(Taco taco) {
        return createResourceWithId(taco.getId(), taco);
    }
}
