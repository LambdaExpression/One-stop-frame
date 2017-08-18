package org.tcat.frame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Created by Lin on 2017/8/2.
 */
@SpringBootApplication
@ServletComponentScan
public class WebApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebApplication.class, args);
    }

}
