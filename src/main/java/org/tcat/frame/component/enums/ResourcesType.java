package org.tcat.frame.component.enums;

import org.tcat.frame.enums.EnumsMsg;
import org.tcat.frame.enums.MultiLanguage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源类型
 * <p>
 * Created by Lin on 2016/11/15.
 */
public enum ResourcesType {
    /**
     * 菜单 value=1
     **/
    menu(1),
    /**
     * 功能 value=1
     */
    function(2);

    private final int value;
    private static Map<Integer, ResourcesType> codeLookUp = new HashMap<>();
    private static List<Map<String, Object>> codeList = new ArrayList<>();
    private static Map<String, String> language = new HashMap<>();

    static {
        for (ResourcesType resourcesType : ResourcesType.values()) {
            codeLookUp.put(resourcesType.value, resourcesType);

            Map<String, Object> codeMap = new HashMap<>();
            codeMap.put("value", resourcesType.value);
            for (MultiLanguage multiLanguage : MultiLanguage.values()) {
                codeMap.put(
                        multiLanguage.name()
                        , EnumsMsg.getMsg(
                                multiLanguage
                                , resourcesType.getClass().getName() + "#" + resourcesType.name()
                        ));
                language.put(multiLanguage.name()
                        , EnumsMsg.getMsg(
                                multiLanguage
                                , resourcesType.getClass().getName() + "#" + resourcesType.name()
                        ));
            }
            codeList.add(codeMap);
        }
    }

    private ResourcesType(int value) {
        this.value = value;
    }

    public static ResourcesType findByValue(int value) {
        return codeLookUp.get(value);
    }

    public int value() {
        return value;
    }

    public String getMsg(MultiLanguage multiLanguage) {
        return language.get(multiLanguage.name());
    }

    public List<Map<String, Object>> codeList() {
        return codeList;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
