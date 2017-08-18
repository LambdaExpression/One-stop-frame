package org.tcat.frame.service.user.enums;

import org.tcat.frame.enums.EnumsMsg;
import org.tcat.frame.enums.MultiLanguage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户性别
 * <p>
 * Created by Lin on 2016/11/15.
 */
public enum UserGender {
    /**
     * 男 value=1
     **/
    MEN(1),

    /**
     * 女 value=2
     **/
    MRS(2);

    private final int value;
    private static Map<Integer, UserGender> codeLookUp = new HashMap<>();
    private static List<Map<String, Object>> codeList = new ArrayList<>();
    private static Map<String, String> language = new HashMap<>();

    static {
        for (UserGender userGender : UserGender.values()) {
            codeLookUp.put(userGender.value, userGender);

            Map<String, Object> codeMap = new HashMap<>();
            codeMap.put("value", userGender.value);
            for (MultiLanguage multiLanguage : MultiLanguage.values()) {
                codeMap.put(
                        multiLanguage.name()
                        , EnumsMsg.getMsg(
                                multiLanguage
                                , userGender.getClass().getName() + "#" + userGender.name()
                        ));
                language.put(multiLanguage.name()
                        , EnumsMsg.getMsg(
                                multiLanguage
                                , userGender.getClass().getName() + "#" + userGender.name()
                        ));
            }
            codeList.add(codeMap);
        }
    }

    private UserGender(int value) {
        this.value = value;
    }

    public static UserGender findByValue(int value) {
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
