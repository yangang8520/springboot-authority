package com.sxbang.springbootauthority.base.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageTableRequest implements Serializable {

    private Integer page;
    private Integer limit;
    private Integer offset;

    public void countOffset(){
        if (this.page == null || this.limit == null) {
            this.offset = 0;
            return;
        }
        this.offset = (this.page - 1) * this.limit;
    }
}
