package org.tcat.frame.util;

import org.springframework.data.domain.Page;
import org.tcat.frame.bean.PageResponse;

import java.io.Serializable;

/**
 * Created by Lin on 2017/8/5.
 */
public class PageUtils {

    private PageUtils() {
    }

    /**
     * 分页转换对象
     *
     * @param page 分页对象
     * @param <T>  类型
     * @return result
     */
    public static <T extends Serializable> PageResponse<T> pageConversion(Page<T> page) {
        if (page == null) {
            return null;
        }
        return new PageResponse<>(
                page.getNumber()
                , page.getSize()
                , page.getTotalElements()
                , page.getTotalPages()
                , page.getContent());
    }

}
