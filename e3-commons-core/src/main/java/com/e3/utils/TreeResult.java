package com.e3.utils;

import java.io.Serializable;

/**
 * Created by zhiyuan on 2017/11/21.
 */
public class TreeResult implements Serializable {
    /**
     * id：节点ID，对加载远程数据很重要。
     text：显示节点文本。
     state：节点状态，'open' 或 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。
     */
    private Long id;
    private String text;
    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    public TreeResult() {
    }
    public TreeResult(Long id, String text, String state) {
        this.id = id;
        this.text = text;
        this.state = state;
    }
}
