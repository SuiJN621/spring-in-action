package tacos.config.custom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import tacos.controller.restful_api.RestRecentTacosController;
import tacos.entity.Taco;
import tacos.entity.resource.TacoResource;

/**
 * @author Sui
 * @date 2019.04.22 10:04
 */
@Configuration
public class HyperLinkConfig {

    /**
     * 返回resource processor 处理指定返回类型的api, 自动添加自定义的链接地址
     *
     * @return
     */
    @Bean
    public ResourceProcessor<PagedResources<Resource<Taco>>> tacoProcessor(EntityLinks entityLinks) {
        return new ResourceProcessor<PagedResources<Resource<Taco>>>() {
            @Override
            public PagedResources<Resource<Taco>> process(PagedResources<Resource<Taco>> resource) {
                //使用ControllerLinkBuilder形式构建无法拼接/api(@RepositoryRestController)
                Resources<TacoResource> tacoResources = ControllerLinkBuilder.methodOn(RestRecentTacosController
                        .class).recentTacos();
                ControllerLinkBuilder controllerLinkBuilder = ControllerLinkBuilder.linkTo(RestRecentTacosController
                        .class);
                resource.add(
                        entityLinks.linkFor(Taco.class).slash("recent")
                                .withRel("recents"));
                return resource;
            }
        };
    }
}
