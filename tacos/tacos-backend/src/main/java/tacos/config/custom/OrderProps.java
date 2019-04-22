package tacos.config.custom;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author Sui
 * @date 2019.04.18 16:14
 */

/**
 * 使用自定义配置类
 */
@ConfigurationProperties(prefix = "tacos.order")
@Component
@Data
public class OrderProps {

    private int pageSize = 20;
}
