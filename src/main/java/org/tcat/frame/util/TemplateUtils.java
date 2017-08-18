package org.tcat.frame.util;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 模板渲染工具
 * Created by Lin on 2016/12/18.
 */
public class TemplateUtils {

    /**
     * 模板参数检测
     *
     * @param data    数据
     * @param content 模板
     * @return true 检查通过 false 部分参数缺失
     */
    public static boolean check(Map<String, Object> data, String content) {
        return check(data, content, null);
    }

    /**
     * 模板参数检测
     *
     * @param data    数据
     * @param content 模板
     * @param logger  日志输出
     * @return true 检查通过 false 部分参数缺失
     */
    public static boolean check(Map<String, Object> data, String content, Logger logger) {
        Pattern pattern = Pattern.compile("\\$\\{[a-zA-Z]+\\}");
        Matcher matcher = pattern.matcher(content);
        boolean flag = true;
        while (matcher.find()) {
            String param = matcher.group();
            param = param.substring(2, param.length() - 1);
            if (data.get(param) == null) {
                if (logger != null)
                    logger.info("Map<String, Object> data 缺少模板参数 " + param);
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 模板渲染
     *
     * @param data    数据
     * @param content 模板
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String render(Map<String, Object> data, String content) throws IOException, TemplateException {
        if (data == null || content == null)
            throw new RuntimeException("数据或模板不能为空");
        Map<String, String> dataString = data.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue() == null ? null : e.getValue().toString()));
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate("myTemplate", content);
        cfg.setTemplateLoader(stringLoader);
        Template temp = cfg.getTemplate("myTemplate", "utf-8");
        Writer out = new StringWriter();
        temp.process(dataString, out);
        String result = out.toString();
        out.flush();
        return result;
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "world");
        String content = "Hello ${name}";
        System.out.print(TemplateUtils.render(data, content));
    }

}
