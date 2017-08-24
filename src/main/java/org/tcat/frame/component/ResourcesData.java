package org.tcat.frame.component;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.tcat.frame.component.dto.ResourcesDto;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Lin on 2017/8/24.
 */
@Component("resourcesData")
public class ResourcesData implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static Map<String, ResourcesDto> resourceDtoMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    private void initResources() {
        InputStream inputStream = ResourcesData.class.getClassLoader().getResourceAsStream("resources" + File.separator + "resources.xml");
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(inputStream);
            Element employees = document.getRootElement();
            for (Iterator i = employees.elementIterator(); i.hasNext(); ) {
                Element employee = (Element) i.next();
                ResourcesDto resourcesDto = new ResourcesDto();

                resourcesDto.setId(employee.attributeValue("id"));
                resourcesDto.setName(employee.attributeValue("name"));
                resourcesDto.setPid(employee.attributeValue("pid"));
                resourcesDto.setUrl(employee.attributeValue("url"));
                resourcesDto.setRemark(employee.attributeValue("remark", ""));
                resourcesDto.setRank(toInt(employee.attributeValue("rank")));
                resourcesDto.setType(toInt(employee.attributeValue("type")));

                this.checkResource(resourcesDto, employee);

                if (resourceDtoMap.get(resourcesDto.getId()) == null) {
                    resourceDtoMap.put(resourcesDto.getId(), resourcesDto);
                } else {
                    throw new RuntimeException("id(" + resourcesDto.getId() + ") 重复 " + employee.asXML());
                }
            }
            for (Iterator i = employees.elementIterator(); i.hasNext(); ) {
                Element employee = (Element) i.next();
                if (employee.attributeValue("pid") != null && resourceDtoMap.get(employee.attributeValue("pid")) == null)
                    throw new RuntimeException("Pid(" + employee.attributeValue("pid") + ") 无对应的id " + employee.asXML());
            }
        } catch (DocumentException e) {
            throw new RuntimeException("resources.xml 资源文件 读取失败", e);
        }
        logger.info("资源 加载完成");
    }

    private Integer toInt(Object obj) {
        if (obj instanceof Byte) {
            return Integer.valueOf((Byte) obj);
        }
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        if (obj instanceof String) {
            try {
                return Integer.valueOf((String) obj);
            } catch (NumberFormatException e) {
                //不处理异常
            }
        }
        return null;
    }

    private void checkResource(ResourcesDto resourcesDto, Element employee) {
        this.checkParam(resourcesDto.getId(), "id 不能为空 " + employee.asXML());
        this.checkParam(resourcesDto.getName(), "name 不能为空 " + employee.asXML());
        this.checkParam(resourcesDto.getRank(), "rank 不能为空 " + employee.asXML());
        this.checkParam(resourcesDto.getType(), "type 不能为空 " + employee.asXML());
    }

    private void checkParam(Object obj, String eMessage) {
        if (obj == null)
            throw new RuntimeException(eMessage);
    }

    public Map<String, ResourcesDto> getResourceDTOMap() {
        if (resourceDtoMap.size() == 0) {
            initResources();
        }
        return resourceDtoMap;
    }

    /**
     * 获得一个资源
     *
     * @param resId 资源id
     * @return resource 资源对象
     */
    public ResourcesDto getResource(String resId) {
        return this.getResourceDTOMap().get(resId);
    }

}
