package tacos.controller.restful_api;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tacos.entity.Taco;
import tacos.entity.resource.TacoResource;
import tacos.entity.resource.TacoResourceAssembler;
import tacos.repository.jpa.JpaTacoRepository;

/**
 * @author Sui
 * @date 2019.04.19 13:52
 */
//替代@Controller和@ResponseBody(返回值直接写入request body中, 不经过model处理)
@RestController
//produces限制请求头accept值符合时才会处理; consumes限制请求头content-type值符合才会处理
//设置这两个值可以让controller方法路径重载, 针对不同的请求调用不同的方法
@RequestMapping(value = "/design", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")  //允许跨域请求
public class RestDesignTacoController {

    private JpaTacoRepository tacoRepository;
    public RestDesignTacoController(JpaTacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(
            //@RequestBody表示参数来自http body, 使用HttpMessageConverter转换
            @RequestBody Taco taco) {
        return tacoRepository.save(taco);
    }

    @GetMapping("/recent")
    public Resources<TacoResource> recentTacos(@PageableDefault Pageable pageable) {
        System.out.println(pageable);
        Page<Taco> tacoPage = tacoRepository.findAll(pageable);
        List<Taco> tacos = tacoPage.getContent();
        //引入超媒体, 资源被包装后自带描述自身的self属性(获取路径)
        List<TacoResource> tacoResources = new TacoResourceAssembler().toResources(tacos);
        Resources<TacoResource> recentResources = new Resources<>(tacoResources);
        recentResources.add(
                ControllerLinkBuilder.linkTo(RestDesignTacoController.class)
                        .slash("recent") //slash方法添加'/' + object
                        .withRel("recents"));
        //另一种写法, 直接导向方法
        /*recentResources.add(
                ControllerLinkBuilder.linkTo(methodOn(RestDesignTacoController.class).recentTacos())
                        .withRel("recents"));*/
        return recentResources;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optTaco = tacoRepository.findById(id);
        if (optTaco.isPresent()) {
            return ResponseEntity.ok(optTaco.get());
        }
        return ResponseEntity.notFound().build();
    }
}
