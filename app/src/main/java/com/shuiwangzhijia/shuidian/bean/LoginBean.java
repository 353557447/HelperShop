package com.shuiwangzhijia.shuidian.bean;



public class LoginBean {


    /**
     * token : 4bf3d2079cf9350efa4d95f6527fab2e
     * user_info : {"u_id":867,"sid":878,"name":"","phone":"13366006600","token":"e51a19d7eb091339ebbf2407850b1872","header_pic":""}
     * user_id : 867
     * did : 11
     */

    private String token;
    private UserInfoBean user_info;
    private int user_id;
    private int did;
    private WaterBean water;

    public WaterBean getWater() {
        return water;
    }

    public void setWater(WaterBean water) {
        this.water = water;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfoBean getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoBean user_info) {
        this.user_info = user_info;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public static class UserInfoBean {
        /**
         * u_id : 867
         * sid : 878
         * name :
         * phone : 13366006600
         * token : e51a19d7eb091339ebbf2407850b1872
         * header_pic :
         */

        private int u_id;
        private int sid;
        private String name;
        private String phone;
        private String token;
        private String header_pic;

        public int getU_id() {
            return u_id;
        }

        public void setU_id(int u_id) {
            this.u_id = u_id;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getHeader_pic() {
            return header_pic;
        }

        public void setHeader_pic(String header_pic) {
            this.header_pic = header_pic;
        }
    }

    public static class WaterBean {

        /**
         * phone : 13480127920
         * seat_no : 18565676759
         * header_pic :
         * sname : 九重岩水厂
         */

        private String phone;
        private String seat_no;
        private String header_pic;
        private String sname;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSeat_no() {
            return seat_no;
        }

        public void setSeat_no(String seat_no) {
            this.seat_no = seat_no;
        }

        public String getHeader_pic() {
            return header_pic;
        }

        public void setHeader_pic(String header_pic) {
            this.header_pic = header_pic;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }
    }
}
