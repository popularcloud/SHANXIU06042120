package com.lwc.shanxiu.module.partsLib.ui.bean;

import java.io.Serializable;

/**
 * @author younge
 * @date 2018/12/29 0029
 * @email 2276559259@qq.com
 */
public class MyScreenItemBean implements Serializable {

    private String name;
    private String id;
    private boolean isSelect = false;

    public MyScreenItemBean(){

    }

    public MyScreenItemBean(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
