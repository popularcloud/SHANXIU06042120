package com.lwc.shanxiu.module.lease_parts.bean;

import java.util.List;

public class SearchListWordBean {

    private List<LeaseGoodsKeywordBean> partsGoodsKeyword;
    private List<LeaseGoodsKeywordBean> partsGoodsKeywordNew;


    public List<LeaseGoodsKeywordBean> getPartsGoodsKeyword() {
        return partsGoodsKeyword;
    }

    public void setPartsGoodsKeyword(List<LeaseGoodsKeywordBean> partsGoodsKeyword) {
        this.partsGoodsKeyword = partsGoodsKeyword;
    }

    public List<LeaseGoodsKeywordBean> getPartsGoodsKeywordNew() {
        return partsGoodsKeywordNew;
    }

    public void setPartsGoodsKeywordNew(List<LeaseGoodsKeywordBean> partsGoodsKeywordNew) {
        this.partsGoodsKeywordNew = partsGoodsKeywordNew;
    }

    public static class LeaseGoodsKeywordBean {
        /**
         * count : 1
         * keyword_name : 搜索
         */

        private int count;
        private String keyword_name;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getKeyword_name() {
            return keyword_name;
        }

        public void setKeyword_name(String keyword_name) {
            this.keyword_name = keyword_name;
        }
    }
}
