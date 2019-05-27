package integration;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * 定义Gate Way, 应用和Integration Flow交互的出入口, 可以是单向或双向的
 * @author Sui
 * @date 2019.04.23 16:02
 */
//标记接口, runtime会由spring生成实现(类似jpa), defaultRequestChannel属性指定消息发送的通道名称
@MessagingGateway(defaultRequestChannel = "textInChannel")
public interface FileWriterGateway {

    void writeToFile(
            //表示参数会被写入message header
            @Header(FileHeaders.FILENAME) String filename,
            String data);
}
