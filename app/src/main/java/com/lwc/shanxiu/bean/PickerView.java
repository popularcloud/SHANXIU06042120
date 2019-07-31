package com.lwc.shanxiu.bean;


import com.lwc.shanxiu.pickerview.model.IPickerViewData;

/**
 * Created by Sai on 15/11/22.
 */
public class PickerView implements IPickerViewData {
    private long id;
    private String name;

    public PickerView(long id, String name){
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //这个用来显示在PickerView上面的字符串,PickerView会通过getPickerViewText方法获取字符串显示出来。
    @Override
    public String getPickerViewText() {
        return name;
    }
}
