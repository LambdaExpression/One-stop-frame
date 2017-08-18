package org.tcat.frame.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * 分页对象
 * Created by Lin on 2017/8/4.
 */
@ApiModel(value = "PageRequest", description = "分页对象")
public class PageRequest implements Pageable, Serializable {

    private static final long serialVersionUID = 1L;
    public static final int NUMBER = 0;
    public static final int SIZE = 10;

    /**
     * 当前页码(索引从0开始)
     */
    @ApiModelProperty(value = "当前页码")
    private int number = NUMBER;

    /**
     * 每页查询的条数
     */
    @ApiModelProperty(value = "每页查询的数量")
    private int size = SIZE;
    /**
     * 排序
     */
    @ApiModelProperty(hidden = true)
    private Sort sort;

    public PageRequest() {
    }

    /**
     * @param number 当前页码(索引从0开始)
     * @param size   每页查询的条数
     * @param sort   排序
     */
    public PageRequest(int number, int size, Sort sort) {
        this.setNumber(number);
        this.setSize(size);
        this.setSort(sort);
    }

    /**
     * @param number 当前页码(索引从0开始)
     * @param size   每页查询的条数
     */
    public PageRequest(int number, int size) {
        this.setNumber(number);
        this.setSize(size);
    }

    /**
     * 当前页码(索引从0开始)
     *
     * @param number 当前页码(索引从0开始)
     */
    public PageRequest(int number) {
        this.number = number;
    }

    /**
     * 当前页码(索引从0开始)
     *
     * @return 当前页码(索引从0开始)
     */
    public int getNumber() {
        return number;
    }

    /**
     * 当前页码(索引从0开始)
     *
     * @param number 当前页码(索引从0开始)
     * @return this
     */
    public PageRequest setNumber(int number) {
        this.number = number < 0 ? NUMBER : number;
        return this;
    }

    /**
     * 每页查询的条数
     *
     * @return 每页查询的条数
     */
    public int getSize() {
        return size;
    }

    /**
     * 每页查询的条数
     *
     * @param size 每页查询的条数
     * @return this
     */
    public PageRequest setSize(int size) {
        this.size = size < 1 ? SIZE : size;
        return this;
    }

    /**
     * 排序
     *
     * @param sort 排序
     * @return this
     */
    public PageRequest setSort(Sort sort) {
        this.sort = sort;
        return this;
    }

    @Override
    public int getPageNumber() {
        return number;
    }

    @Override
    public int getPageSize() {
        return size;
    }

    @Override
    public int getOffset() {
        return this.getPageNumber() * size;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new PageRequest(number + 1, size, sort);
    }

    @Override
    public Pageable previousOrFirst() {
        return number == 1 ? this : new PageRequest(number - 1, size, sort);
    }

    @Override
    public Pageable first() {
        return new PageRequest(0, size, sort);
    }

    @Override
    public boolean hasPrevious() {
        return this.number > 1;
    }
}
