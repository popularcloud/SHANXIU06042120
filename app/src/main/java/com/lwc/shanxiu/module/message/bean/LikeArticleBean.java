package com.lwc.shanxiu.module.message.bean;

import java.io.Serializable;

public class LikeArticleBean implements Serializable {

    public String knowledge_title;
    public String knowledge_id;

    public String getKnowledge_title() {
        return knowledge_title;
    }

    public void setKnowledge_title(String knowledge_title) {
        this.knowledge_title = knowledge_title;
    }

    public String getKnowledge_id() {
        return knowledge_id;
    }

    public void setKnowledge_id(String knowledge_id) {
        this.knowledge_id = knowledge_id;
    }
}
