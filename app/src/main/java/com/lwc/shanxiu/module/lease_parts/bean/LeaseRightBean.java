package com.lwc.shanxiu.module.lease_parts.bean;

public class LeaseRightBean {

    public int id;
    public String name;
    public String parentId;

    public LeaseRightBean(){}

    public LeaseRightBean(int id, String name, String parentId){
        this.id = id;
        this.name = name;
        this.parentId = parentId;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
