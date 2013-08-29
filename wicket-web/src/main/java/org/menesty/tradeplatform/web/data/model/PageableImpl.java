package org.menesty.tradeplatform.web.data.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * User: Menesty
 * Date: 8/13/13
 * Time: 11:15 AM
 */
public class PageableImpl implements Pageable, Serializable {

    private int offset;

    private int pageSize;

    public PageableImpl(long offset, long pageSize){
        this((int) offset, (int) pageSize);
    }


    public PageableImpl(int offset, int pageSize) {
        this.offset = offset;
        this.pageSize = pageSize;
    }

    @Override
    public int getPageNumber() {
        return offset / pageSize;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return null;
    }
}
