package org.tcat.frame.exception.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

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
    private static Map<String, String> mapCn = new ConcurrentHashMap<>();
    private static Map<String, String> mapEn = new ConcurrentHashMap<>();

    static {
        // init cn.properties
        init("code/code_cn.properties", mapCn);
        // init en.properties
        init("code/code_en.properties", mapEn);
    }

    private CodeMsg() {
    }

    private static void init(String path, Map map) {
        Resource resource = new ClassPathResource(path);
        Properties props = new Properties();
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(resource.getInputStream(), "UTF-8");
            props.load(isr);
            props.forEach((k, v) -> {
                map.put(k.toString(), v.toString());
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

    public static String getMsgEn(String code) {
        return mapEn.getOrDefault(code, "");
    }

    public static String getMsgCn(String code) {
        return mapCn.getOrDefault(code, "");
    }

}
