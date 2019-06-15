package com.shuiwangzhijia.shuidian.bean;

import java.util.List;

/**
 * Created by Lijn on 2019/4/17.
 */

public class MyEquipBean {


    /**
     * code : 200.0
     * msg : success
     * data : {"list":[{"id":1,"sid":673,"dname":"设备1","serial_number":"aaa111","equipment_number":"aaa111","main":"1","longitude":"114.089820","latitude":"22.541407","province":"广东省","city":"深圳市","district":"罗湖区","address":"某某大厦1号","device_pic":"111.jpg","is_delete":0,"is_show":1,"add_time":1.55546764E9,"lattice_no":15,"lattice_ok":3,"total_price":57,"day_price":57,"total_sales":9,"day_sales":3}]}
     * scode : 0.0
     */

    private int code;
    private String msg;
    private DataBean data;
    private int scode;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getScode() {
        return scode;
    }

    public void setScode(int scode) {
        this.scode = scode;
    }

    public static class DataBean {
        private TotalBean total;
        private List<ListBean> list;

        public TotalBean getTotal() {
            return total;
        }

        public void setTotal(TotalBean total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 1.0
             * sid : 673.0
             * dname : 设备1
             * serial_number : aaa111
             * equipment_number : aaa111
             * main : 1
             * longitude : 114.089820
             * latitude : 22.541407
             * province : 广东省
             * city : 深圳市
             * district : 罗湖区
             * address : 某某大厦1号
             * device_pic : 111.jpg
             * is_delete : 0.0
             * is_show : 1.0
             * add_time : 1.55546764E9
             * lattice_no : 15.0
             * lattice_ok : 3.0
             * total_price : 57.0
             * day_price : 57.0
             * total_sales : 9.0
             * day_sales : 3.0
             */

            private int id;
            private int sid;
            private String dname;
            private String serial_number;
            private String equipment_number;
            private String main;
            private String longitude;
            private String latitude;
            private String province;
            private String city;
            private String district;
            private String address;
            private String device_pic;
            private int is_delete;
            private int is_show;
            private long add_time;
            private int lattice_no;
            private int lattice_ok;
            private double total_price;
            private double day_price;
            private int total_sales;
            private int day_sales;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSid() {
                return sid;
            }

            public void setSid(int sid) {
                this.sid = sid;
            }

            public String getDname() {
                return dname;
            }

            public void setDname(String dname) {
                this.dname = dname;
            }

            public String getSerial_number() {
                return serial_number;
            }

            public void setSerial_number(String serial_number) {
                this.serial_number = serial_number;
            }

            public String getEquipment_number() {
                return equipment_number;
            }

            public void setEquipment_number(String equipment_number) {
                this.equipment_number = equipment_number;
            }

            public String getMain() {
                return main;
            }

            public void setMain(String main) {
                this.main = main;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getDevice_pic() {
                return device_pic;
            }

            public void setDevice_pic(String device_pic) {
                this.device_pic = device_pic;
            }

            public int getIs_delete() {
                return is_delete;
            }

            public void setIs_delete(int is_delete) {
                this.is_delete = is_delete;
            }

            public int getIs_show() {
                return is_show;
            }

            public void setIs_show(int is_show) {
                this.is_show = is_show;
            }

            public long getAdd_time() {
                return add_time;
            }

            public void setAdd_time(long add_time) {
                this.add_time = add_time;
            }

            public int getLattice_no() {
                return lattice_no;
            }

            public void setLattice_no(int lattice_no) {
                this.lattice_no = lattice_no;
            }

            public int getLattice_ok() {
                return lattice_ok;
            }

            public void setLattice_ok(int lattice_ok) {
                this.lattice_ok = lattice_ok;
            }

            public double getTotal_price() {
                return total_price;
            }

            public void setTotal_price(double total_price) {
                this.total_price = total_price;
            }

            public double getDay_price() {
                return day_price;
            }

            public void setDay_price(double day_price) {
                this.day_price = day_price;
            }

            public int getTotal_sales() {
                return total_sales;
            }

            public void setTotal_sales(int total_sales) {
                this.total_sales = total_sales;
            }

            public int getDay_sales() {
                return day_sales;
            }

            public void setDay_sales(int day_sales) {
                this.day_sales = day_sales;
            }
        }

        public class TotalBean {
            private double total_price;
            private double day_price;
            private int day_sales;

            public double getTotal_price() {
                return total_price;
            }

            public void setTotal_price(double total_price) {
                this.total_price = total_price;
            }

            public double getDay_price() {
                return day_price;
            }

            public void setDay_price(double day_price) {
                this.day_price = day_price;
            }

            public int getDay_sales() {
                return day_sales;
            }

            public void setDay_sales(int day_sales) {
                this.day_sales = day_sales;
            }
        }
    }
}
