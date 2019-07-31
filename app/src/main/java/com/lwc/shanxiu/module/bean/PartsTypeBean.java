package com.lwc.shanxiu.module.bean;

import java.io.Serializable;

/**
 * @author younge
 * @date 2018/12/20 0020
 * @email 2276559259@qq.com
 * @Des 配件
 */
public class PartsTypeBean implements Serializable{

        private String typeName;
        private String createTime;
        private int sn;
        private String typeIcon;
        private int isValid;
        private String typeId;

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
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

        public String getTypeIcon() {
            return typeIcon;
        }

        public void setTypeIcon(String typeIcon) {
            this.typeIcon = typeIcon;
        }

        public int getIsValid() {
            return isValid;
        }

        public void setIsValid(int isValid) {
            this.isValid = isValid;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }
}
