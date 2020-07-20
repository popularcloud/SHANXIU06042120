package com.lwc.shanxiu.module.lease_parts.bean;

import java.util.List;

public class LeaseSearchTypeBean {
    /**
     * attributeName : cpu
     * createTime : 2020-04-30 09:50:41
     * sn : 1
     * typeDetailId : 1512440283035JG
     * attributeId : 1588211441619EJ
     * isValid : 1
     * modifyTime : {"date":9,"day":6,"hours":17,"minutes":56,"month":4,"nanos":0,"seconds":11,"time":1589018171000,"timezoneOffset":-480,"year":120}
     * options : []
     */

    private String attributeName;
    private String createTime;
    private int sn;
    private String typeDetailId;
    private String attributeId;
    private int isValid;
    private ModifyTimeBean modifyTime;
    private List<?> options;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public String getTypeDetailId() {
        return typeDetailId;
    }

    public void setTypeDetailId(String typeDetailId) {
        this.typeDetailId = typeDetailId;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public ModifyTimeBean getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(ModifyTimeBean modifyTime) {
        this.modifyTime = modifyTime;
    }

    public List<?> getOptions() {
        return options;
    }

    public void setOptions(List<?> options) {
        this.options = options;
    }

    public static class ModifyTimeBean {
        /**
         * date : 9
         * day : 6
         * hours : 17
         * minutes : 56
         * month : 4
         * nanos : 0
         * seconds : 11
         * time : 1589018171000
         * timezoneOffset : -480
         * year : 120
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
