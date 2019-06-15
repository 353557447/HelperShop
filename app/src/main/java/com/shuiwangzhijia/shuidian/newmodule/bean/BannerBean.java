package com.shuiwangzhijia.shuidian.newmodule.bean;

import java.util.List;

public class BannerBean {

    /**
     * data : {"index":[{"id":25,"tittle":"水店4","picturl":"20190428/e9d31c79e403600d3c9a6a6175b9f65e.png","defaults":1,"type":1,"platform":1,"url_type":0,"go_id":"","url_addr":"","is_waterworks":1,"waterworks_id":"","background_color":"#59C0F7","background_img":null,"detail":"<p style=\"text-align: center;\"><img src=\"https://testapi.fndwater.com/editor/20190428/2a24c0ecb747c10bcc859a1bf4d26c67.jpg\" alt=\"\" width=\"600\" height=\"2814\" /><\/p>\n<p><audio style=\"display: none;\" controls=\"controls\"><\/audio><\/p>","update_time":1556539437,"delete_time":null,"goods_type":null},{"id":27,"tittle":"用户5","picturl":"20190422/67fa6cdadd4acd0acbbd736b873230c8.png","defaults":1,"type":1,"platform":1,"url_type":0,"go_id":"","url_addr":"","is_waterworks":1,"waterworks_id":"","background_color":"#E4E2E2","background_img":null,"detail":"","update_time":1555922538,"delete_time":null,"goods_type":null},{"id":28,"tittle":"水店6","picturl":"20190422/73b0dab8f86a72eff2bd6006ad80edb0.png","defaults":1,"type":1,"platform":1,"url_type":1,"go_id":"","url_addr":"","is_waterworks":1,"waterworks_id":"","background_color":"#080809","background_img":null,"detail":"<p>&nbsp;<\/p>\n<p><audio style=\"display: none;\" controls=\"controls\"><\/audio><\/p>","update_time":1556068519,"delete_time":null,"goods_type":null},{"id":29,"tittle":"水店7","picturl":"20190422/dffa4406ea795290ed249e19d223b68c.png","defaults":1,"type":1,"platform":1,"url_type":2,"go_id":"","url_addr":null,"is_waterworks":1,"waterworks_id":"","background_color":"#F9D379","background_img":null,"detail":"","update_time":1555922681,"delete_time":null,"goods_type":null},{"id":30,"tittle":"水店8","picturl":"20190422/0f40b7af771a4cce1b370543a9aad0f1.png","defaults":1,"type":1,"platform":1,"url_type":3,"go_id":"","url_addr":"84539a","is_waterworks":1,"waterworks_id":"","background_color":"#FCA9B0","background_img":null,"detail":"<p style=\"text-align: center;\"><img src=\"https://testapi.fndwater.com/editor/20190430/12e371fc9536900dbae9adc54b74b23f.jpg\" alt=\"\" width=\"600\" height=\"2814\" /><\/p>\n<p style=\"text-align: center;\">dasdasdadsadsasad<\/p>","update_time":1556588715,"delete_time":null,"goods_type":null},{"id":34,"tittle":"水店banner","picturl":"20190424/fce80f3ac28542d53342023febb62aa1.png","defaults":1,"type":1,"platform":1,"url_type":0,"go_id":"","url_addr":"","is_waterworks":1,"waterworks_id":"","background_color":"#301755","background_img":null,"detail":"<p style=\"text-align: center;\"><img src=\"https://testapi.fndwater.com/editor/20190428/4bca54dc1bbe8aa2dba2acc2048d764c.jpg\" alt=\"\" width=\"600\" height=\"2814\" /><\/p>\n<p><audio style=\"display: none;\" controls=\"controls\"><\/audio><\/p>","update_time":1556418855,"delete_time":null,"goods_type":null},{"id":45,"tittle":"水店轮播","picturl":"20190429/3182c116820bc38c676975838fb19bc8.jpg","defaults":1,"type":1,"platform":1,"url_type":0,"go_id":"","url_addr":"","is_waterworks":1,"waterworks_id":"","background_color":"#935B22","background_img":null,"detail":"<p><img src=\"https://testapi.fndwater.com/editor/20190429/0e20796dfd04707fe3bda1f4f2f32363.jpg\" alt=\"\" width=\"600\" height=\"2814\" />asdsads<\/p>\n<p><audio style=\"display: none;\" controls=\"controls\"><\/audio><\/p>","update_time":1556587778,"delete_time":null,"goods_type":null},{"id":47,"tittle":"水店轮播","picturl":"20190429/29b472101c4218b9f383a0348a61a0ba.png","defaults":1,"type":1,"platform":1,"url_type":0,"go_id":"","url_addr":"","is_waterworks":1,"waterworks_id":"","background_color":"#DEE1DC","background_img":null,"detail":"","update_time":1556587410,"delete_time":null,"goods_type":null},{"id":52,"tittle":"水店首页跳链接","picturl":"20190430/97521d08da7fd1bbf040458bc487afc6.png","defaults":1,"type":1,"platform":1,"url_type":0,"go_id":"","url_addr":"","is_waterworks":1,"waterworks_id":"","background_color":"#555059","background_img":null,"detail":"<p>89685664656456456456wsdas asdas&nbsp;<\/p>","update_time":1556616325,"delete_time":null,"goods_type":null},{"id":55,"tittle":"水店banner闪退测试","picturl":"20190430/530e38dc632d84fbfeaad0bbbb7c0863.png","defaults":1,"type":1,"platform":1,"url_type":6,"go_id":"","url_addr":"http://www.baidu.com","is_waterworks":1,"waterworks_id":"","background_color":"#04203C","background_img":null,"detail":"","update_time":1556590942,"delete_time":null,"goods_type":null}],"activity":[{"id":53,"tittle":"水店首页活动","picturl":"20190430/7067922369a5e9708b9154d95fd014e6.png","defaults":1,"type":3,"platform":1,"url_type":1,"go_id":"","url_addr":null,"is_waterworks":1,"waterworks_id":"","background_color":"","background_img":null,"detail":"","update_time":1556588030,"delete_time":null,"goods_type":null},{"id":54,"tittle":"首页活动2","picturl":"20190430/4e24cb18714d01359a5194cf727940bc.png","defaults":1,"type":3,"platform":1,"url_type":6,"go_id":"","url_addr":"","is_waterworks":1,"waterworks_id":"","background_color":"","background_img":null,"detail":"","update_time":1556588746,"delete_time":null,"goods_type":null}]}
     * code : 200
     * msg : success
     */

    private DataBean data;
    private int code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private List<IndexBean> index;
        private List<ActivityBean> activity;

        public List<IndexBean> getIndex() {
            return index;
        }

        public void setIndex(List<IndexBean> index) {
            this.index = index;
        }

        public List<ActivityBean> getActivity() {
            return activity;
        }

        public void setActivity(List<ActivityBean> activity) {
            this.activity = activity;
        }

        public static class IndexBean {
            /**
             * id : 25
             * tittle : 水店4
             * picturl : 20190428/e9d31c79e403600d3c9a6a6175b9f65e.png
             * defaults : 1
             * type : 1
             * platform : 1
             * url_type : 0
             * go_id :
             * url_addr :
             * is_waterworks : 1
             * waterworks_id :
             * background_color : #59C0F7
             * background_img : null
             * detail : <p style="text-align: center;"><img src="https://testapi.fndwater.com/editor/20190428/2a24c0ecb747c10bcc859a1bf4d26c67.jpg" alt="" width="600" height="2814" /></p>
             <p><audio style="display: none;" controls="controls"></audio></p>
             * update_time : 1556539437
             * delete_time : null
             * goods_type : null
             */

            private int id;
            private String tittle;
            private String picturl;
            private int defaults;
            private int type;
            private int platform;
            private int url_type;
            private String go_id;
            private String url_addr;
            private int is_waterworks;
            private String waterworks_id;
            private String background_color;
            private Object background_img;
            private String detail;
            private int update_time;
            private Object delete_time;
            private Object goods_type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTittle() {
                return tittle;
            }

            public void setTittle(String tittle) {
                this.tittle = tittle;
            }

            public String getPicturl() {
                return picturl;
            }

            public void setPicturl(String picturl) {
                this.picturl = picturl;
            }

            public int getDefaults() {
                return defaults;
            }

            public void setDefaults(int defaults) {
                this.defaults = defaults;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getPlatform() {
                return platform;
            }

            public void setPlatform(int platform) {
                this.platform = platform;
            }

            public int getUrl_type() {
                return url_type;
            }

            public void setUrl_type(int url_type) {
                this.url_type = url_type;
            }

            public String getGo_id() {
                return go_id;
            }

            public void setGo_id(String go_id) {
                this.go_id = go_id;
            }

            public String getUrl_addr() {
                return url_addr;
            }

            public void setUrl_addr(String url_addr) {
                this.url_addr = url_addr;
            }

            public int getIs_waterworks() {
                return is_waterworks;
            }

            public void setIs_waterworks(int is_waterworks) {
                this.is_waterworks = is_waterworks;
            }

            public String getWaterworks_id() {
                return waterworks_id;
            }

            public void setWaterworks_id(String waterworks_id) {
                this.waterworks_id = waterworks_id;
            }

            public String getBackground_color() {
                return background_color;
            }

            public void setBackground_color(String background_color) {
                this.background_color = background_color;
            }

            public Object getBackground_img() {
                return background_img;
            }

            public void setBackground_img(Object background_img) {
                this.background_img = background_img;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public int getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(int update_time) {
                this.update_time = update_time;
            }

            public Object getDelete_time() {
                return delete_time;
            }

            public void setDelete_time(Object delete_time) {
                this.delete_time = delete_time;
            }

            public Object getGoods_type() {
                return goods_type;
            }

            public void setGoods_type(Object goods_type) {
                this.goods_type = goods_type;
            }
        }

        public static class ActivityBean {
            /**
             * id : 53
             * tittle : 水店首页活动
             * picturl : 20190430/7067922369a5e9708b9154d95fd014e6.png
             * defaults : 1
             * type : 3
             * platform : 1
             * url_type : 1
             * go_id :
             * url_addr : null
             * is_waterworks : 1
             * waterworks_id :
             * background_color :
             * background_img : null
             * detail :
             * update_time : 1556588030
             * delete_time : null
             * goods_type : null
             */

            private int id;
            private String tittle;
            private String picturl;
            private int defaults;
            private int type;
            private int platform;
            private int url_type;
            private String go_id;
            private Object url_addr;
            private int is_waterworks;
            private String waterworks_id;
            private String background_color;
            private Object background_img;
            private String detail;
            private int update_time;
            private Object delete_time;
            private Object goods_type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTittle() {
                return tittle;
            }

            public void setTittle(String tittle) {
                this.tittle = tittle;
            }

            public String getPicturl() {
                return picturl;
            }

            public void setPicturl(String picturl) {
                this.picturl = picturl;
            }

            public int getDefaults() {
                return defaults;
            }

            public void setDefaults(int defaults) {
                this.defaults = defaults;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getPlatform() {
                return platform;
            }

            public void setPlatform(int platform) {
                this.platform = platform;
            }

            public int getUrl_type() {
                return url_type;
            }

            public void setUrl_type(int url_type) {
                this.url_type = url_type;
            }

            public String getGo_id() {
                return go_id;
            }

            public void setGo_id(String go_id) {
                this.go_id = go_id;
            }

            public Object getUrl_addr() {
                return url_addr;
            }

            public void setUrl_addr(Object url_addr) {
                this.url_addr = url_addr;
            }

            public int getIs_waterworks() {
                return is_waterworks;
            }

            public void setIs_waterworks(int is_waterworks) {
                this.is_waterworks = is_waterworks;
            }

            public String getWaterworks_id() {
                return waterworks_id;
            }

            public void setWaterworks_id(String waterworks_id) {
                this.waterworks_id = waterworks_id;
            }

            public String getBackground_color() {
                return background_color;
            }

            public void setBackground_color(String background_color) {
                this.background_color = background_color;
            }

            public Object getBackground_img() {
                return background_img;
            }

            public void setBackground_img(Object background_img) {
                this.background_img = background_img;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public int getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(int update_time) {
                this.update_time = update_time;
            }

            public Object getDelete_time() {
                return delete_time;
            }

            public void setDelete_time(Object delete_time) {
                this.delete_time = delete_time;
            }

            public Object getGoods_type() {
                return goods_type;
            }

            public void setGoods_type(Object goods_type) {
                this.goods_type = goods_type;
            }
        }
    }
}
