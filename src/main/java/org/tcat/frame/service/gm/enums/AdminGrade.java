package org.tcat.frame.service.gm.enums;

import org.tcat.frame.enums.EnumsMsg;
import org.tcat.frame.enums.MultiLanguage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员等级
 * <p>
 * Created by Lin on 2016/11/15.
 */
public enum AdminGrade {
    /**
     * 超级管理员 value=1
     **/
    SUPER(1),

    /**
     * 高级管理员 value=2
     **/
    ADVANCED(2),

    /**
     * 普通管理员 value=3
     **/
    ORDINARY(3);

    private final int value;
    private static Map<Integer, AdminGrade> codeLookUp = new HashMap<>();
    private static List<Map<String, Object>> codeList = new ArrayList<>();
    private static Map<String, String> language = new HashMap<>();

    static {
        for (AdminGrade adminDisable : AdminGrade.values()) {
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

    private AdminGrade(int value) {
        this.value = value;
    }

    public static AdminGrade findByValue(int value) {
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
