package org.tcat.frame.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.tcat.frame.exception.code.CodeMsg;
import org.tcat.frame.util.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举 多语言消息
 * Created by Lin on 2017/8/18.
 */
public final class EnumsMsg {

    private static Logger logger = LoggerFactory.getLogger(CodeMsg.class);
    private static Map<String, Map<String, String>> MULTI_LANGUAGE = new ConcurrentHashMap<>();

    private EnumsMsg() {

    }

    static {
        for (MultiLanguage multiLanguage : MultiLanguage.values()) {
            init("enums/enums_" + multiLanguage.name() + ".properties", multiLanguage.name());
        }
    }

    private static void init(String path, String language) {
        Map<String, String> map = new ConcurrentHashMap<>();
        Resource resource = new ClassPathResource(path);
        Properties props = new Properties();
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(resource.getInputStream(), "UTF-8");
            props.load(isr);
            props.forEach((k, v) -> {
                map.put(k.toString(), v.toString());
            });
            MULTI_LANGUAGE.put(language, map);
        } catch (IOException e) {
            logger.info("枚举 预加载失败");
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

    /**
     * 根据语言和枚举路径 获取对应语言的枚举
     *
     * @param multiLanguage
     * @param enumsPath
     * @return
     */
    public static String getMsg(MultiLanguage multiLanguage, String enumsPath) {
        if (multiLanguage == null || StringUtils.isEmptyByTrim(enumsPath)) {
            return null;
        }
        Map<String, String> msgMap = MULTI_LANGUAGE.get(multiLanguage.name());
        if (msgMap == null) {
            return null;
        }
        return msgMap.get(enumsPath);
    }


}
