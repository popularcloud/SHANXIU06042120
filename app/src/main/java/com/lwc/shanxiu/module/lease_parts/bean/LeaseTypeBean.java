package com.lwc.shanxiu.module.lease_parts.bean;

import java.util.List;

public class LeaseTypeBean {


    private List<TypesBean> types;
    private List<AttributesBean> attributes;

    public List<TypesBean> getTypes() {
        return types;
    }

    public void setTypes(List<TypesBean> types) {
        this.types = types;
    }

    public List<AttributesBean> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributesBean> attributes) {
        this.attributes = attributes;
    }

    public static class TypesBean {
        /**
         * type_detail_id : 1512440283035JG
         * device_type_name : 电脑
         * create_time : 2017-12-05 10:18:03
         * device_type_id : 1574065937820CK
         * is_valid : 1
         * type_detail_name : 台式电脑
         * sn : 1
         * type_detail_icon : https://cdn.mixiu365.com/0_1590377672347.jpg
         */

        private String typeDetailId;
        private String deviceTypeName;
        private String create_time;
        private String deviceTypeId;
        private int is_valid;
        private String typeDetailName;
        private int sn;
        private String typeDetailIcon;

        public String getTypeDetailId() {
            return typeDetailId;
        }

        public void setTypeDetailId(String typeDetailId) {
            this.typeDetailId = typeDetailId;
        }

        public String getDeviceTypeName() {
            return deviceTypeName;
        }

        public void setDeviceTypeName(String deviceTypeName) {
            this.deviceTypeName = deviceTypeName;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getDeviceTypeId() {
            return deviceTypeId;
        }

        public void setDeviceTypeId(String deviceTypeId) {
            this.deviceTypeId = deviceTypeId;
        }

        public int getIs_valid() {
            return is_valid;
        }

        public void setIs_valid(int is_valid) {
            this.is_valid = is_valid;
        }

        public String getTypeDetailName() {
            return typeDetailName;
        }

        public void setTypeDetailName(String typeDetailName) {
            this.typeDetailName = typeDetailName;
        }

        public int getSn() {
            return sn;
        }

        public void setSn(int sn) {
            this.sn = sn;
        }

        public String getTypeDetailIcon() {
            return typeDetailIcon;
        }

        public void setTypeDetailIcon(String typeDetailIcon) {
            this.typeDetailIcon = typeDetailIcon;
        }
    }

    public static class AttributesBean {
        /**
         * type_detail_id : 1592472878347CX
         * create_time : 2020-06-18 17:39:42
         * attribute_id : 1592473182816EP
         * is_valid : 1
         * options : [{"options_id":"1592473201128KY","options_name":"奔腾","attribute_id":"1592473182816EP","sn":0},{"options_id":"1592473220762DN","options_name":"奔图","attribute_id":"1592473182816EP","sn":1}]
         * attribute_name : 品牌
         * sn : 0
         */

        private String type_detail_id;
        private String create_time;
        private String attribute_id;
        private int is_valid;
        private String attributeName;
        private int sn;
        private List<OptionsBean> options;

        public String getType_detail_id() {
            return type_detail_id;
        }

        public void setType_detail_id(String type_detail_id) {
            this.type_detail_id = type_detail_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getAttribute_id() {
            return attribute_id;
        }

        public void setAttribute_id(String attribute_id) {
            this.attribute_id = attribute_id;
        }

        public int getIs_valid() {
            return is_valid;
        }

        public void setIs_valid(int is_valid) {
            this.is_valid = is_valid;
        }


        public String getAttributeName() {
            return attributeName;
        }

        public void setAttributeName(String attributeName) {
            this.attributeName = attributeName;
        }

        public int getSn() {
            return sn;
        }

        public void setSn(int sn) {
            this.sn = sn;
        }

        public List<OptionsBean> getOptions() {
            return options;
        }

        public void setOptions(List<OptionsBean> options) {
            this.options = options;
        }

        public static class OptionsBean {
            /**
             * options_id : 1592473201128KY
             * options_name : 奔腾
             * attribute_id : 1592473182816EP
             * sn : 0
             */

            private String options_id;
            private String options_name;
            private String attribute_id;
            private int sn;

            public String getOptions_id() {
                return options_id;
            }

            public void setOptions_id(String options_id) {
                this.options_id = options_id;
            }

            public String getOptions_name() {
                return options_name;
            }

            public void setOptions_name(String options_name) {
                this.options_name = options_name;
            }

            public String getAttribute_id() {
                return attribute_id;
            }

            public void setAttribute_id(String attribute_id) {
                this.attribute_id = attribute_id;
            }

            public int getSn() {
                return sn;
            }

            public void setSn(int sn) {
                this.sn = sn;
            }
        }
    }
}
