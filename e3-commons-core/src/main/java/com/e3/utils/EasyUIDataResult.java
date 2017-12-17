package com.e3.utils;

import java.io.Serializable;
import java.util.List;


public class EasyUIDataResult<T> implements Serializable{

    private Integer total;
    private List<T> rows;

    public EasyUIDataResult() {
    }

    public Integer getTotal() {
        return total;
    }


    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
