package org.tcat.frame.service.user.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户id类型
 * <p>
 * Created by Lin on 2016/11/15.
 */
public enum UserIdType {
    /**
     * 员工 value=1
     **/
    STAFF(1),

    /**
     * 普通用户 value=2
     **/
    USER(2);

    private final int value;
    private static Map<Integer, UserIdType> codeLookUp = new HashMap<>();
    private static List<Map<String, Object>> codeList = new ArrayList<>();

    static {
        for (UserIdType goodsPCycle : UserIdType.values()) {
            codeLookUp.put(goodsPCycle.value, goodsPCycle);

            Map<String, Object> codeMap = new HashMap<>();
            codeMap.put("value", goodsPCycle.value);
            codeList.add(codeMap);
        }
    }

    private UserIdType(int value) {
        this.value = value;
    }

    public static UserIdType findByValue(int value) {
        return codeLookUp.get(value);
    }

    public int value() {
        return value;
    }

    public List<Map<String, Object>> codeList() {
        return codeList;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
