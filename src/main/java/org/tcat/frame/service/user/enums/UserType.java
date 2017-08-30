package org.tcat.frame.service.user.enums;

import org.tcat.frame.enums.EnumsMsg;
import org.tcat.frame.enums.MultiLanguage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户类型
 * <p>
 * Created by Lin on 2016/11/15.
 */
public enum UserType {
    /**
     * 普通用户 value=1
     **/
    ORDINARY(1);

    private final int value;
    private static Map<Integer, UserType> codeLookUp = new HashMap<>();
    private static List<Map<String, Object>> codeList = new ArrayList<>();
    private static Map<String, String> language = new HashMap<>();

    static {
        for (UserType userType : UserType.values()) {
            codeLookUp.put(userType.value, userType);

            Map<String, Object> codeMap = new HashMap<>();
            codeMap.put("value", userType.value);
            for (MultiLanguage multiLanguage : MultiLanguage.values()) {
                codeMap.put(
                        multiLanguage.name()
                        , EnumsMsg.getMsg(
                                multiLanguage
                                , userType.getClass().getName() + "#" + userType.name()
                        ));
                language.put(multiLanguage.name()
                        , EnumsMsg.getMsg(
                                multiLanguage
                                , userType.getClass().getName() + "#" + userType.name()
                        ));
            }
            codeList.add(codeMap);
        }
    }

    private UserType(int value) {
        this.value = value;
    }

    public static UserType findByValue(int value) {
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
