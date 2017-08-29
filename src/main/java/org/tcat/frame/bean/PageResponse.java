package org.tcat.frame.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据返回
 * Created by Lin on 2017/8/4.
 */
@ApiModel(value = "PageResponse", description = "分页数据返回对象")
public class PageResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码(索引从0开始)
     */
    @ApiModelProperty(value = "当前页码(索引从0开始)")
    private int number;

    /**
     * 每页查询的条数
     */
    @ApiModelProperty(value = "每页查询的条数")
    private int size;
    /**
     * 查询的总条数
     */
    @ApiModelProperty(value = "查询的总条数")
    private long totalElements;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private int totalPages;

    /**
     * 返回的分页内容
     */
    @ApiModelProperty(value = "返回的分页内容")
    private List<T> content;

    /**
     * @param number        当前页码(索引从0开始)
     * @param size          每页查询的条数
     * @param totalElements 查询的总条数
     * @param totalPages    总页数
     * @param content       返回的分页内容
     */
    public PageResponse(int number, int size, long totalElements, int totalPages, List<T> content) {
        this.number = number;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.content = content;
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
    public PageResponse setNumber(int number) {
        this.number = number;
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
    public PageResponse setSize(int size) {
        this.size = size;
        return this;
    }

    /**
     * 查询的总条数
     *
     * @return 查询的总条数
     */
    public long getTotalElements() {
        return totalElements;
    }

    /**
     * 查询的总条数
     *
     * @param totalElements 查询的总条数
     * @return this
     */
    public PageResponse setTotalElements(long totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    /**
     * 总页数
     *
     * @return 总页数
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * 总页数
     *
     * @param totalPages 总页数
     * @return this
     */
    public PageResponse setTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    /**
     * 返回的分页内容
     *
     * @return 返回的分页内容
     */
    public List<T> getContent() {
        return content;
    }

    /**
     * 返回的分页内容
     *
     * @param content 返回的分页内容
     * @return this
     */
    public PageResponse setContent(List<T> content) {
        this.content = content;
        return this;
    }

}
