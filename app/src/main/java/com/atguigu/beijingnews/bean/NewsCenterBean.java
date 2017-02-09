package com.atguigu.beijingnews.bean;

import java.util.List;

/**
 * Created by 皇 上 on 2017/2/6.
 */

public class NewsCenterBean {

    /**
     * retcode : 200
     * data : [{"id":10000,"title":"鏂伴椈","type":1,"children":[{"id":10007,"title":"鍖椾含","type":1,"url":"/static/api/news/10007/list_1.json"},{"id":10006,"title":"涓浗","type":1,"url":"/static/api/news/10006/list_5.json"},{"id":10008,"title":"鍥介檯","type":1,"url":"/static/api/news/10008/list_7.json"},{"id":10014,"title":"鏂囧ū","type":1,"url":"/static/api/news/10014/list_3.json"},{"id":10010,"title":"浣撹偛","type":1,"url":"/static/api/news/10010/list_3.json"},{"id":10091,"title":"鐢熸椿","type":1,"url":"/static/api/news/10091/list_1.json"},{"id":10012,"title":"鏃呮父","type":1,"url":"/static/api/news/10012/list_3.json"},{"id":10095,"title":"绉戞妧","type":1,"url":"/static/api/news/10095/list_1.json"},{"id":10009,"title":"鍐涗簨","type":1,"url":"/static/api/news/10009/list_3.json"},{"id":10011,"title":"璐㈢粡","type":1,"url":"/static/api/news/10011/list_3.json"},{"id":10093,"title":"濂虫\u20ac�","type":1,"url":"/static/api/news/10093/list_1.json"},{"id":10192,"title":"鍊嶅効閫�","type":1,"url":"/static/api/news/10192/list_1.json"}]},{"id":10002,"title":"涓撻","type":10,"url":"/static/api/news/10002/list_1.json","url1":"/static/api/news/10002/list1_1.json"},{"id":10003,"title":"缁勫浘","type":2,"url":"/static/api/news/10003/list_1.json"},{"id":10004,"title":"浜掑姩","type":3,"excurl":"/static/api/news/comment/exc_1.json","dayurl":"/static/api/news/comment/day_1.json","weekurl":"/static/api/news/comment/week_1.json"},{"id":10005,"title":"鎶曠エ","type":4,"url":"/static/api/news/vote/vote_1.json"}]
     * extend : [10007,10006,10008,10014,10091,10010,10192,10009,10095,10093,10012,10011]
     */

    private int retcode;
    private List<DataBean> data;
    private List<Integer> extend;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<Integer> getExtend() {
        return extend;
    }

    public void setExtend(List<Integer> extend) {
        this.extend = extend;
    }

    public static class DataBean {
        /**
         * id : 10000
         * title : 鏂伴椈
         * type : 1
         * children : [{"id":10007,"title":"鍖椾含","type":1,"url":"/static/api/news/10007/list_1.json"},{"id":10006,"title":"涓浗","type":1,"url":"/static/api/news/10006/list_5.json"},{"id":10008,"title":"鍥介檯","type":1,"url":"/static/api/news/10008/list_7.json"},{"id":10014,"title":"鏂囧ū","type":1,"url":"/static/api/news/10014/list_3.json"},{"id":10010,"title":"浣撹偛","type":1,"url":"/static/api/news/10010/list_3.json"},{"id":10091,"title":"鐢熸椿","type":1,"url":"/static/api/news/10091/list_1.json"},{"id":10012,"title":"鏃呮父","type":1,"url":"/static/api/news/10012/list_3.json"},{"id":10095,"title":"绉戞妧","type":1,"url":"/static/api/news/10095/list_1.json"},{"id":10009,"title":"鍐涗簨","type":1,"url":"/static/api/news/10009/list_3.json"},{"id":10011,"title":"璐㈢粡","type":1,"url":"/static/api/news/10011/list_3.json"},{"id":10093,"title":"濂虫\u20ac�","type":1,"url":"/static/api/news/10093/list_1.json"},{"id":10192,"title":"鍊嶅効閫�","type":1,"url":"/static/api/news/10192/list_1.json"}]
         * url : /static/api/news/10002/list_1.json
         * url1 : /static/api/news/10002/list1_1.json
         * excurl : /static/api/news/comment/exc_1.json
         * dayurl : /static/api/news/comment/day_1.json
         * weekurl : /static/api/news/comment/week_1.json
         */

        private int id;
        private String title;
        private int type;
        private String url;
        private String url1;
        private String excurl;
        private String dayurl;
        private String weekurl;
        private List<ChildrenBean> children;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl1() {
            return url1;
        }

        public void setUrl1(String url1) {
            this.url1 = url1;
        }

        public String getExcurl() {
            return excurl;
        }

        public void setExcurl(String excurl) {
            this.excurl = excurl;
        }

        public String getDayurl() {
            return dayurl;
        }

        public void setDayurl(String dayurl) {
            this.dayurl = dayurl;
        }

        public String getWeekurl() {
            return weekurl;
        }

        public void setWeekurl(String weekurl) {
            this.weekurl = weekurl;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * id : 10007
             * title : 鍖椾含
             * type : 1
             * url : /static/api/news/10007/list_1.json
             */

            private int id;
            private String title;
            private int type;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
