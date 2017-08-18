package org.tcat.frame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public abstract class JunitBaseTest {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public void show(Object obj) {
        String data = formatGson.toJson(obj);
        System.out.println("#####################");
        System.out.println(data);
        System.out.println("#####################");
    }

    protected final Gson formatGson = new GsonBuilder().setPrettyPrinting().create();

}
