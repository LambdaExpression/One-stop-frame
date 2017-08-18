package org.tcat.frame.service.user.enums;

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
    STAFF(1, "Men", "男"),

    /**
     * 女 value=2
     **/
    USER(2, "Ms", "女");

    private final int value;
    private final String descriptionEn;
    private final String descriptionCn;
    private static Map<Integer, UserGender> codeLookUp = new HashMap<>();
    private static List<Map<String, Object>> codeList = new ArrayList<>();

    static {
        for (UserGender goodsPCycle : UserGender.values()) {
            codeLookUp.put(goodsPCycle.value, goodsPCycle);

            Map<String, Object> codeMap = new HashMap<>();
            codeMap.put("value", goodsPCycle.value);
            codeMap.put("descriptionEn", goodsPCycle.descriptionEn);
            codeMap.put("descriptionCn", goodsPCycle.descriptionCn);
            codeList.add(codeMap);
        }
    }

    private UserGender(int value, String descriptionEn, String descriptionCn) {
        this.value = value;
        this.descriptionEn = descriptionEn;
        this.descriptionCn = descriptionCn;
    }

    public static UserGender findByValue(int value) {
        return codeLookUp.get(value);
    }

    public int value() {
        return value;
    }

    public String descriptionEn() {
        return descriptionEn;
    }

    public String descriptionCn() {
        return descriptionCn;
    }

    public List<Map<String, Object>> codeList() {
        return codeList;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
