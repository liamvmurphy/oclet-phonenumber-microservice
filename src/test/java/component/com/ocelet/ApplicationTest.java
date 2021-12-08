package component.com.ocelet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.oclet.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Application.class)
@AutoConfigureMockMvc
public class ApplicationTest {

    @Test
    public void springApplicationRun(){
        //standup springboot!
    }
}
