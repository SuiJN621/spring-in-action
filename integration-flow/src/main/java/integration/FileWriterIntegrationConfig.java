package integration;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;

/**
 * @author Sui
 * @date 2019.05.23 10:49
 */
@Configuration
public class FileWriterIntegrationConfig {


    @Bean
    //定义消息处理器
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler fileWriter() {
        FileWritingMessageHandler handler =
                new FileWritingMessageHandler(new File(
                        "C:\\Work\\练习\\spring-in-action\\integration-flow\\test.txt"));
        //设置不处理reply channel
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        return handler;
    }

    /**
     * 显式创建channel 默认不存在的channel 会被自动创建
     *
     * @return
     */
    @Bean
    public MessageChannel textInChannel() {
        return new DirectChannel();
    }

    /**
     * channel种类
     * DirectChannel(默认): 发送消息给通线程的单个consumer, 可以使用事务
     * PublishSubscribeChannel: 订阅模式, 一条消息可被所有订阅者消费
     * QueueChannel: 队列模式, FIFO, 需要在ServiceActivator上配置poller
     * PriorityChannel: 队列模式, 带优先级
     * RendezvousChannel: 队列有未被消费的消息时阻塞
     * ExecutorChannel: 异步发送消息, 事务不可用
     * FluxMessageChannel: 响应式管道
     *
     * @return
     */
    @Bean
    public MessageChannel myPublishSubscribeChannel() {
        return new PublishSubscribeChannel();
    }

    /**
     * 配置过滤器Filter
     * 也可以定义方法使用
     * @return
     */
    @Bean
    @Filter(inputChannel = "numberChannel", outputChannel = "evenNumberChannel")
    public GenericSelector<Integer> evenNumberFilter() {
        return source -> source % 2 == 0;
    }

    /**
     * 配置转换器
     *
     * @return
     */
    @Bean
    @Transformer(inputChannel = "textInChannel", outputChannel = "fileWriterChannel")
    public GenericTransformer<String, String> upperCaseTransformer() {
        return String::toUpperCase;
    }

    /**
     * 配置路由器
     *
     * @return
     */
    @Bean
    @Router(inputChannel = "numberChannel")
    public AbstractMessageRouter evenOddRouter() {
        //PayloadTypeRouter 根据类型映射channel
        return new AbstractMessageRouter() {
            @Override
            protected Collection<MessageChannel> determineTargetChannels(Message<?> message) {
                Integer number = (Integer) message.getPayload();
                if (number % 2 == 0) {
                    return Collections.singleton(new DirectChannel());
                }
                return Collections.singleton(new DirectChannel());
            }
        };
    }

    /**
     * 配置分离器
     * 用于分离Collection类型消息/复合类消息
     * 也可以声明方法使用
     * @return
     */
    @Bean
    @Splitter(inputChannel = "poChannel",
            outputChannel = "splitOrderChannel")
    public MySplitter orderSplitter() {
        return new MySplitter();
    }

    private class MySplitter {
        public Collection<Object> split(Collection parts) {
            //拆分
            return parts;
        }
    }

    /**
     * 配置Service Activator
     * 定义MessageHandler处理消息/定义GenericHandler处理消息并发送
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel="someChannel")
    public MessageHandler sysoutHandler() {
        return message -> {
            System.out.println("Message payload: " + message.getPayload());
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "orderChannel", outputChannel = "completeOrder")
    public GenericHandler<Object> orderHandler() {
        return (payload, headers) -> {
            //处理消息
            return null;
        };
    }

    /**
     * 配置通道适配器(Integration Flow的端点)
     * @param source
     * @return
     */
    @Bean
    //配置入站通道适配器
    @InboundChannelAdapter(poller = @Poller(fixedRate = "1000"), channel = "numberChannel")
    public MessageSource<Integer> numberSource(AtomicInteger source) {
        return () -> new GenericMessage<>(source.getAndIncrement());
    }

    /**
     * 整体定义Integration Flow
     *
     * @return
     */
    @Bean
    public IntegrationFlow fileWriterFlow() {
        return IntegrationFlows
                .from(MessageChannels.direct("textInChannel"))
                //定义转换器
                .<String, String>transform(String::toUpperCase)
                //定义过滤器
                .<String, String>filter((GenericSelector<String>) source -> {
                    try {
                        Integer integer = Integer.valueOf(source);
                        return integer % 2 == 0;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                })
                //定义路由器
                //.route()
                .channel(MessageChannels.direct("fileWriterChannel"))

                .handle(Files
                        .outboundAdapter(new File(
                                "C:\\Work\\练习\\spring-in-action\\integration-flow\\test.txt"))
                        .fileExistsMode(FileExistsMode.APPEND)
                        .appendNewLine(true))
                .get();
    }

}
