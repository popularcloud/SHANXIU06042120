package com.lwc.shanxiu.module.message.bean;

import java.util.List;

/**
 * @author younge
 * @date 2019/6/4 0004
 * @email 2276559259@qq.com
 */
public class KnowledgeBaseBean {

        private String knowledgeId;
        private String knowledgeImage;
        private int knowledgeImageType;
        private String createTime;
        private String knowledgeTitle;
        private String knowledgePaper;
        private String labelName;
        private int browseNum;

        public String getKnowledgeId() {
            return knowledgeId;
        }

        public void setKnowledgeId(String knowledgeId) {
            this.knowledgeId = knowledgeId;
        }

        public String getKnowledgeImage() {
            return knowledgeImage;
        }

        public void setKnowledgeImage(String knowledgeImage) {
            this.knowledgeImage = knowledgeImage;
        }

        public int getKnowledgeImageType() {
            return knowledgeImageType;
        }

        public void setKnowledgeImageType(int knowledgeImageType) {
            this.knowledgeImageType = knowledgeImageType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getKnowledgeTitle() {
            return knowledgeTitle;
        }

        public void setKnowledgeTitle(String knowledgeTitle) {
            this.knowledgeTitle = knowledgeTitle;
        }

        public String getKnowledgePaper() {
            return knowledgePaper;
        }

        public void setKnowledgePaper(String knowledgePaper) {
            this.knowledgePaper = knowledgePaper;
        }

        public int getBrowseNum() {
            return browseNum;
        }

        public void setBrowseNum(int browseNum) {
            this.browseNum = browseNum;
        }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
