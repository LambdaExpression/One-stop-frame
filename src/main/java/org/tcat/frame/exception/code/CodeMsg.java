package org.tcat.frame.exception.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.tcat.frame.enums.MultiLanguage;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息错误码
 */
public final class CodeMsg {

    private static Logger logger = LoggerFactory.getLogger(CodeMsg.class);
    private static Map<String, String> map = new ConcurrentHashMap<>();

    static {
        for (MultiLanguage multiLanguage : MultiLanguage.values()) {
            init("code/code_" + multiLanguage.name() + ".properties", multiLanguage);
        }
    }

    private CodeMsg() {
    }

    private static void init(String path, MultiLanguage multiLanguage) {
        Resource resource = new ClassPathResource(path);
        Properties props = new Properties();
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(resource.getInputStream(), "UTF-8");
            props.load(isr);
            props.forEach((k, v) -> {
                map.put(multiLanguage.name() + ":" + k.toString(), v.toString());
            });
        } catch (IOException e) {
            logger.info("消息错误码 预加载失败");
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getMsg(MultiLanguage multiLanguage, String code) {
        return map.getOrDefault(multiLanguage + ":" + code, "");
    }

}
