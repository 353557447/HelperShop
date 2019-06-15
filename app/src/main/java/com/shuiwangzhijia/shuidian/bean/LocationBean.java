package com.shuiwangzhijia.shuidian.bean;

/**
 * created by wangsuli on 2018/9/27.
 */
public class LocationBean {

    /**
     * id : 6695052091083729501
     * title : 肯德基(华南大厦餐厅)洗手间
     * address : 北京市西城区西单北大街176号中友百货地下1层
     * tel :
     * category : 基础设施:公共设施:公共厕所
     * type : 0
     * location : {"lat":39.90953,"lng":116.37437}
     * _distance : 115.98
     * ad_info : {"adcode":110102,"province":"北京市","city":"北京市","district":"西城区"}
     */

    private String id;
    private String title;
    private String address;
    private String tel;
    private String category;
    private int type;
    private LocationItemBean location;
    private double _distance;
    private AdInfoBean ad_info;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LocationItemBean getLocation() {
        return location;
    }

    public void setLocation(LocationItemBean location) {
        this.location = location;
    }

    public double get_distance() {
        return _distance;
    }

    public void set_distance(double _distance) {
        this._distance = _distance;
    }

    public AdInfoBean getAd_info() {
        return ad_info;
    }

    public void setAd_info(AdInfoBean ad_info) {
        this.ad_info = ad_info;
    }

    public static class LocationItemBean {
        /**
         * lat : 39.90953
         * lng : 116.37437
         */

        private double lat;
        private double lng;

        public String getUlnglat() {
            return lng+","+lat;
        }

        private String ulnglat;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }

    public static class AdInfoBean {
        /**
         * adcode : 110102
         * province : 北京市
         * city : 北京市
         * district : 西城区
         */

        private int adcode;
        private String province;
        private String city;
        private String district;

        public int getAdcode() {
            return adcode;
        }

        public void setAdcode(int adcode) {
            this.adcode = adcode;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }
    }
}
