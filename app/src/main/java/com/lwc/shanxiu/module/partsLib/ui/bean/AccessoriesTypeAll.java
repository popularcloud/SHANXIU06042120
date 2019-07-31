package com.lwc.shanxiu.module.partsLib.ui.bean;

import java.util.List;

/**
 * @author younge
 * @date 2018/12/29 0029
 * @email 2276559259@qq.com
 */
public class AccessoriesTypeAll {

    private List<AttributesBean> attributes;
    private List<TypesBean> types;

    public List<AttributesBean> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributesBean> attributes) {
        this.attributes = attributes;
    }

    public List<TypesBean> getTypes() {
        return types;
    }

    public void setTypes(List<TypesBean> types) {
        this.types = types;
    }

    public static class AttributesBean {
        /**
         * create_time : 2018-12-26 16:35:06
         * attribute_id : 1545813306022YW
         * modify_time : {"date":26,"day":3,"hours":16,"minutes":40,"month":11,"nanos":0,"seconds":42,"time":1545813642000,"timezoneOffset":-480,"year":118}
         * type_id : 1545380016029ER
         * attribute_name : 品牌
         * is_valid : 1
         * options : [{"options_name":"影驰","attribute_id":"1545813306022YW","options_id":"1545814673097DU"},{"options_name":"七彩虹","attribute_id":"1545813306022YW","options_id":"1545814652431JR"},{"options_name":"微星","attribute_id":"1545813306022YW","options_id":"1545814632369MW"},{"options_name":"索泰","attribute_id":"1545813306022YW","options_id":"1545814621312GR"},{"options_name":"华硕","attribute_id":"1545813306022YW","options_id":"1545814618787TC"}]
         */

        private String create_time;
        private String attribute_id;
        private ModifyTimeBean modify_time;
        private String type_id;
        private String attribute_name;
        private int is_valid;
        private List<OptionsBean> options;

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

        public ModifyTimeBean getModify_time() {
            return modify_time;
        }

        public void setModify_time(ModifyTimeBean modify_time) {
            this.modify_time = modify_time;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getAttribute_name() {
            return attribute_name;
        }

        public void setAttribute_name(String attribute_name) {
            this.attribute_name = attribute_name;
        }

        public int getIs_valid() {
            return is_valid;
        }

        public void setIs_valid(int is_valid) {
            this.is_valid = is_valid;
        }

        public List<OptionsBean> getOptions() {
            return options;
        }

        public void setOptions(List<OptionsBean> options) {
            this.options = options;
        }

        public static class ModifyTimeBean {
            /**
             * date : 26
             * day : 3
             * hours : 16
             * minutes : 40
             * month : 11
             * nanos : 0
             * seconds : 42
             * time : 1545813642000
             * timezoneOffset : -480
             * year : 118
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int nanos;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }

        public static class OptionsBean {
            /**
             * options_name : 影驰
             * attribute_id : 1545813306022YW
             * options_id : 1545814673097DU
             */

            private String options_name;
            private String attribute_id;
            private String options_id;

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

            public String getOptions_id() {
                return options_id;
            }

            public void setOptions_id(String options_id) {
                this.options_id = options_id;
            }
        }
    }

    public static class TypesBean {
        /**
         * sn : 0
         * type_icon : http://cdn.mixiu365.com/o_1cf48ms8u1t1tcn91ucb86b1quja.png
         * type_name : 显卡
         * create_time : 2018-12-21 16:13:35
         * modify_time : {"date":25,"day":2,"hours":16,"minutes":11,"month":11,"nanos":0,"seconds":31,"time":1545725491000,"timezoneOffset":-480,"year":118}
         * type_id : 1545380016029ER
         * is_valid : 1
         */

        private int sn;
        private String type_icon;
        private String type_name;
        private String create_time;
        private ModifyTimeBeanX modify_time;
        private String type_id;
        private int is_valid;

        public int getSn() {
            return sn;
        }

        public void setSn(int sn) {
            this.sn = sn;
        }

        public String getType_icon() {
            return type_icon;
        }

        public void setType_icon(String type_icon) {
            this.type_icon = type_icon;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public ModifyTimeBeanX getModify_time() {
            return modify_time;
        }

        public void setModify_time(ModifyTimeBeanX modify_time) {
            this.modify_time = modify_time;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public int getIs_valid() {
            return is_valid;
        }

        public void setIs_valid(int is_valid) {
            this.is_valid = is_valid;
        }

        public static class ModifyTimeBeanX {
            /**
             * date : 25
             * day : 2
             * hours : 16
             * minutes : 11
             * month : 11
             * nanos : 0
             * seconds : 31
             * time : 1545725491000
             * timezoneOffset : -480
             * year : 118
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int nanos;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }
    }
}
