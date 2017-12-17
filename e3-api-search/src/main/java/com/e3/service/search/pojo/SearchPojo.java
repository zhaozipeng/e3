package com.e3.service.search.pojo;

import com.e3.utils.TextUtils;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/28.
 */
public class SearchPojo implements Serializable{
    /**
     *  t.id,
     t.title,
     t.sell_point,
     t.price,
     t.image,
     c.`name` as catName,
     d.item_desc
     */
    private String id;
    private String title;
    private String sell_point;
    private Long price;
    private String image;
    private String [] images;
    /**
     * 自己封装图片获取单个
     * @return
     */
    public String[] getImages() {
        if(!TextUtils.isEmpty(getImage())){
            images=getImage().split(",");
        }
        return images;
    }

    private String catName;
    private String item_desc;
    public SearchPojo() {
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSell_point() {
        return sell_point;
    }

    public void setSell_point(String sell_point) {
        this.sell_point = sell_point;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }
}
