package org.tcat.frame.service.gm.enums;

import org.tcat.frame.enums.EnumsMsg;
import org.tcat.frame.enums.MultiLanguage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 是否禁用
 * <p>
 * Created by Lin on 2016/11/15.
 */
public enum AdminDisable {
    /**
     * 是 value=1
     **/
    YES(1),

    /**
     * 否 value=2
     **/
    NO(2);

    private final int value;
    private static Map<Integer, AdminDisable> codeLookUp = new HashMap<>();
    private static List<Map<String, Object>> codeList = new ArrayList<>();
    private static Map<String, String> language = new HashMap<>();

    static {
        for (AdminDisable adminDisable : AdminDisable.values()) {
            codeLookUp.put(adminDisable.value, adminDisable);

            Map<String, Object> codeMap = new HashMap<>();
            codeMap.put("value", adminDisable.value);
            for (MultiLanguage multiLanguage : MultiLanguage.values()) {
                codeMap.put(
                        multiLanguage.name()
                        , EnumsMsg.getMsg(
                                multiLanguage
                                , adminDisable.getClass().getName() + "#" + adminDisable.name()
                        ));
                language.put(multiLanguage.name()
                        , EnumsMsg.getMsg(
                                multiLanguage
                                , adminDisable.getClass().getName() + "#" + adminDisable.name()
                        ));
            }
            codeList.add(codeMap);
        }
    }

    private AdminDisable(int value) {
        this.value = value;
    }

    public static AdminDisable findByValue(int value) {
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
