package tacos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)  //SpringRunner等同于SpringJUnitClassRunner
@SpringBootTest  //自动搜索@SpringBootConfiguration, 使用SpringBootContextLoader, 其他功能
public class SpringInActionApplicationTests {

    @Test
    public void contextLoads() {
    }

}
