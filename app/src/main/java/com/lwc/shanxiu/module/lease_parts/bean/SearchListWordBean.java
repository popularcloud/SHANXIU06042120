package com.lwc.shanxiu.module.lease_parts.bean;

import java.util.List;

public class SearchListWordBean {
    /**
     * leaseGoodsKeyword : [{"count":1,"keyword_name":"搜索"},{"count":1,"keyword_name":"历史"}]
     * leaseGoodsKeywordNew : {"pages":1,"count":2,"list":[{"keyword_id":"1559716408841VL","create_time":"2019-06-05 14:33:28","keyword_name":"热搜"},{"keyword_id":"1559716408840VL","create_time":"2019-06-05 14:33:28","keyword_name":"搜索"}]}
     */

    private LeaseGoodsKeywordNewBean leaseGoodsKeywordNew;
    private List<LeaseGoodsKeywordBean> leaseGoodsKeyword;

    public LeaseGoodsKeywordNewBean getLeaseGoodsKeywordNew() {
        return leaseGoodsKeywordNew;
    }

    public void setLeaseGoodsKeywordNew(LeaseGoodsKeywordNewBean leaseGoodsKeywordNew) {
        this.leaseGoodsKeywordNew = leaseGoodsKeywordNew;
    }

    public List<LeaseGoodsKeywordBean> getLeaseGoodsKeyword() {
        return leaseGoodsKeyword;
    }

    public void setLeaseGoodsKeyword(List<LeaseGoodsKeywordBean> leaseGoodsKeyword) {
        this.leaseGoodsKeyword = leaseGoodsKeyword;
    }

    public static class LeaseGoodsKeywordNewBean {
        /**
         * pages : 1
         * count : 2
         * list : [{"keyword_id":"1559716408841VL","create_time":"2019-06-05 14:33:28","keyword_name":"热搜"},{"keyword_id":"1559716408840VL","create_time":"2019-06-05 14:33:28","keyword_name":"搜索"}]
         */

        private int pages;
        private int count;
        private List<ListBean> list;

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * keyword_id : 1559716408841VL
             * create_time : 2019-06-05 14:33:28
             * keyword_name : 热搜
             */

            private String keyword_id;
            private String create_time;
            private String keyword_name;

            public String getKeyword_id() {
                return keyword_id;
            }

            public void setKeyword_id(String keyword_id) {
                this.keyword_id = keyword_id;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getKeyword_name() {
                return keyword_name;
            }

            public void setKeyword_name(String keyword_name) {
                this.keyword_name = keyword_name;
            }
        }
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
