package integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * @author Sui
 * @date 2019.05.27 13:53
 */
@Configuration
public class EmailIntegrationConfig {

    @Bean
    public Transformer emailTransformer(){
        return message -> {
            //转换邮件
            return null;
        };
    }

    @Bean
    public MessageHandler emailHandler(){
        return message -> {
            //处理转换后消息
        };
    }

    @Bean
    public IntegrationFlow emailFlow() {
        return IntegrationFlows
                .from(MessageChannels.queue("email"))
                .transform(emailTransformer())
                .handle(emailHandler())
                .get();
    }
}
