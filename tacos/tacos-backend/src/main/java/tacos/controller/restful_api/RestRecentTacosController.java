package tacos.controller.restful_api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tacos.entity.Taco;
import tacos.entity.resource.TacoResource;
import tacos.entity.resource.TacoResourceAssembler;
import tacos.repository.jpa.JpaTacoRepository;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * 自定义Spring-data-rest controller
 *
 * @author Sui
 * @date 2019.04.22 9:38
 */
//controller自动使用spring-data-rest配置的根路径, 但不具备@RestController的@ResponseBody注解
@RepositoryRestController
public class RestRecentTacosController {

    private JpaTacoRepository jpaTacoRepository;

    @Autowired
    public RestRecentTacosController(JpaTacoRepository jpaTacoRepository) {
        this.jpaTacoRepository = jpaTacoRepository;
    }

    @GetMapping(value = "/tacos/recent", produces = "application/hal+json")
    @ResponseBody
    public Resources<TacoResource> recentTacos() {
        PageRequest page = PageRequest.of(
                0, 15, Sort.by("createdAt").descending());
        List<Taco> tacos = jpaTacoRepository.findAll(page).getContent();
        List<TacoResource> tacoResources = new TacoResourceAssembler().toResources(tacos);
        Resources<TacoResource> recentResources = new Resources<>(tacoResources);
        recentResources.add(ControllerLinkBuilder
                .linkTo(methodOn(RestRecentTacosController.class).recentTacos())
                .withRel("recents"));
        //ResponseEntity和@ReponseBody相比, 可以定制返回的状态码, 响应头等信息
        //两者返回值都不经过试图解析器解析, 直接写入响应体
        return recentResources;
    }
}
