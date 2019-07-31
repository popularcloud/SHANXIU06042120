package com.lwc.shanxiu.module.message.bean;

import java.util.List;

/**
 * @author younge
 * @date 2019/6/5 0005
 * @email 2276559259@qq.com
 */
public class SearchConditionBean {

    /**
     * createTime : 2019-06-04 10:57:33
     * isValid : 1
     * typeName : 打印机
     * options : [{"brand_name":"奔图","brand_id":"1559617551383IY"}]
     * typeId : 1559617053629VL
     */

    private String createTime;
    private int isValid;
    private String typeName;
    private String typeId;
    private List<OptionsBean> options;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public List<OptionsBean> getOptions() {
        return options;
    }

    public void setOptions(List<OptionsBean> options) {
        this.options = options;
    }

    public static class OptionsBean {
        /**
         * brand_name : 奔图
         * brand_id : 1559617551383IY
         */

        public OptionsBean(){}
        public OptionsBean(String brand_id,String brand_name){
            this.brand_id = brand_id;
            this.brand_name = brand_name;
        }

        private String brand_name;
        private String brand_id;

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }
    }
}
