package com.lwc.shanxiu.module.bean;

import java.util.List;

/**
 * @author younge
 * @date 2019/5/24 0024
 * @email 2276559259@qq.com
 */
public class FeeStandardBean {

    /**
     * data : [{"data":[{"exampleId":"1530067400517XO","maintainCost":10000,"isFixation":0,"exampleName":"复印机维修","visitCost":2000}],"typeName":"复印机","typeId":"2"}]
     * remark : 备注
     */

    private String remark;
    private List<DataBeanX> data;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<DataBeanX> getData() {
        return data;
    }

    public void setData(List<DataBeanX> data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * data : [{"exampleId":"1530067400517XO","maintainCost":10000,"isFixation":0,"exampleName":"复印机维修","visitCost":2000}]
         * typeName : 复印机
         * typeId : 2
         */

        private String typeName;
        private String typeId;
        private List<DataBean> data;

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

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * exampleId : 1530067400517XO
             * maintainCost : 10000
             * isFixation : 0
             * exampleName : 复印机维修
             * visitCost : 2000
             */

            private String exampleId;
            private int maintainCost;
            private int isFixation;
            private String exampleName;
            private int visitCost;

            public String getExampleId() {
                return exampleId;
            }

            public void setExampleId(String exampleId) {
                this.exampleId = exampleId;
            }

            public int getMaintainCost() {
                return maintainCost;
            }

            public void setMaintainCost(int maintainCost) {
                this.maintainCost = maintainCost;
            }

            public int getIsFixation() {
                return isFixation;
            }

            public void setIsFixation(int isFixation) {
                this.isFixation = isFixation;
            }

            public String getExampleName() {
                return exampleName;
            }

            public void setExampleName(String exampleName) {
                this.exampleName = exampleName;
            }

            public int getVisitCost() {
                return visitCost;
            }

            public void setVisitCost(int visitCost) {
                this.visitCost = visitCost;
            }
        }
    }
}
