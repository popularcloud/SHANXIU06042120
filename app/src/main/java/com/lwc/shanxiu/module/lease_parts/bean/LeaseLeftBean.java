package com.lwc.shanxiu.module.lease_parts.bean;

public class LeaseLeftBean {

    public int id;
    public String name;
    public boolean isSelect;

    public LeaseLeftBean(){

    }

    public LeaseLeftBean(int id,String name,boolean isSelect){
        this.id = id;
        this.name = name;
        this.isSelect = isSelect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
