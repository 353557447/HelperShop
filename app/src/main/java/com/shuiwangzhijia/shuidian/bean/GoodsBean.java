package com.shuiwangzhijia.shuidian.bean;


import java.io.Serializable;

/**
 * 商品实体类
 * created by wangsuli on 2018/8/22.
 */
public class GoodsBean implements Serializable {
    /**
     * id : 9
     * gid : 15262627891416
     * did : 46
     * gname : 福能达空气水 瓶装水 550ml*24箱装
     * gsum : 9999737
     * is_up : 1
     * g_number : null
     * barcode : null
     * gunit : 箱
     * pprice : 24.00
     * least_p : 100
     * gprice : 48.00
     * least_g : 1
     * descrip : 福能达空气水 瓶装水 550ml*24箱装
     * g_water : 34
     * picturl : 20180514/d6c499edc7d9e431b3d81286fd39879d.jpg
     * update_time : 1528281422
     * pid : 46
     * sid : 33
     * sgrade : 3
     * sname : 深圳市天源水文章饮品有限公司
     * amount : 100.00
     * full_free : 200.00
     * is_free : 0
     * price : 18.00
     */
    private static final long serialVersionUID = 7981560250804078637l;
    private int id;

    public int getLeast_num() {
        return least_num;
    }

    public void setLeast_num(int least_num) {
        this.least_num = least_num;
    }

    private int least_num;// 验证单个水厂不得低于的数量
    /**
     * snum : 3
     */

    private int snum;//订单管理 采购订单-内部item 非 待支付

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    private String order_sn;

    public int getDistribution_status() {
        return distribution_status;
    }

    public void setDistribution_status(int distribution_status) {
        this.distribution_status = distribution_status;
    }

    private int distribution_status;

    public int getNum() {
        if (snum == 0) {
            return num;
        } else {
            return snum;
        }
    }

    public void setNum(int num) {
        this.num = num;
    }

    private int num;//订单管理 采购订单-内部item 待支付
    private String gid;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    private String addr;
    private int did;
    private String gname;
    private long gsum;
    private int is_up;
    private Object g_number;
    private Object barcode;
    private String gunit;
    private String pprice;
    private int least_p;//验证单个商品不得低于的数量
    private String gprice;
    private int least_g;
    private String descrip;
    private String g_water;
    private String picturl;
    private String update_time;
    private int pid;
    private int sid;
    private int sgrade;
    private String sname;
    private String amount;
    private String full_free;
    private int is_free;
    private String price;
    private String active;
    public String getActive() {
        return active;
    }
    public void setActive(String active) {
        this.active = active;
    }
    private boolean check;//选择状态
    private int count;//标记数量
    private int activity_type; //1满减 2满赠
    private int full;
    private int rebate_flag;

    public int getRebate_flag() {
        return rebate_flag;
    }

    public void setRebate_flag(int rebate_flag) {
        this.rebate_flag = rebate_flag;
    }

    public String getNew_price() {
        return new_price;
    }

    public void setNew_price(String new_price) {
        this.new_price = new_price;
    }

    private String new_price;

    public int getFull() {
        return full;
    }

    public void setFull(int full) {
        this.full = full;
    }

    public int getReduce() {
        return reduce;
    }

    public void setReduce(int reduce) {
        this.reduce = reduce;
    }

    private int reduce;

    public int getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(int activity_type) {
        this.activity_type = activity_type;
    }

    /**
     * gtype : 7
     * g_number : null
     * barcode : null
     * is_delete : 0
     * sh_status : 2
     * refusal : null
     * update_time : null
     * delete_time : null
     * create_time : null
     */

    private int gtype;
    private int is_delete;
    private int sh_status;
    private Object refusal;
    private String delete_time;
    private String create_time;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    private String detail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public long getGsum() {
        return gsum;
    }

    public void setGsum(long gsum) {
        this.gsum = gsum;
    }

    public int getIs_up() {
        return is_up;
    }

    public void setIs_up(int is_up) {
        this.is_up = is_up;
    }

    public Object getG_number() {
        return g_number;
    }

    public void setG_number(Object g_number) {
        this.g_number = g_number;
    }

    public Object getBarcode() {
        return barcode;
    }

    public void setBarcode(Object barcode) {
        this.barcode = barcode;
    }

    public String getGunit() {
        return gunit;
    }

    public void setGunit(String gunit) {
        this.gunit = gunit;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public int getLeast_p() {
        return least_p;
    }

    public void setLeast_p(int least_p) {
        this.least_p = least_p;
    }

    public String getGprice() {
        return gprice;
    }

    public void setGprice(String gprice) {
        this.gprice = gprice;
    }

    public int getLeast_g() {
        return least_g;
    }

    public void setLeast_g(int least_g) {
        this.least_g = least_g;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getG_water() {
        return g_water;
    }

    public void setG_water(String g_water) {
        this.g_water = g_water;
    }

    public String getPicturl() {
        return picturl;
    }

    public void setPicturl(String picturl) {
        this.picturl = picturl;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getSgrade() {
        return sgrade;
    }

    public void setSgrade(int sgrade) {
        this.sgrade = sgrade;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFull_free() {
        return full_free;
    }

    public void setFull_free(String full_free) {
        this.full_free = full_free;
    }

    public int getIs_free() {
        return is_free;
    }

    public void setIs_free(int is_free) {
        this.is_free = is_free;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getGtype() {
        return gtype;
    }

    public void setGtype(int gtype) {
        this.gtype = gtype;
    }


    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public int getSh_status() {
        return sh_status;
    }

    public void setSh_status(int sh_status) {
        this.sh_status = sh_status;
    }

    public Object getRefusal() {
        return refusal;
    }

    public void setRefusal(Object refusal) {
        this.refusal = refusal;
    }


    public String getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(String delete_time) {
        this.delete_time = delete_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }


    public int getSnum() {
        return snum;
    }

    public void setSnum(int snum) {
        this.snum = snum;
    }


    @Override
    public String toString() {
        return "GoodsBean{" +
                "id=" + id +
                ", least_num=" + least_num +
                ", snum=" + snum +
                ", order_sn='" + order_sn + '\'' +
                ", distribution_status=" + distribution_status +
                ", num=" + num +
                ", gid='" + gid + '\'' +
                ", addr='" + addr + '\'' +
                ", did=" + did +
                ", gname='" + gname + '\'' +
                ", gsum=" + gsum +
                ", is_up=" + is_up +
                ", g_number=" + g_number +
                ", barcode=" + barcode +
                ", gunit='" + gunit + '\'' +
                ", pprice='" + pprice + '\'' +
                ", least_p=" + least_p +
                ", gprice='" + gprice + '\'' +
                ", least_g=" + least_g +
                ", descrip='" + descrip + '\'' +
                ", g_water='" + g_water + '\'' +
                ", picturl='" + picturl + '\'' +
                ", update_time='" + update_time + '\'' +
                ", pid=" + pid +
                ", sid=" + sid +
                ", sgrade=" + sgrade +
                ", sname='" + sname + '\'' +
                ", amount='" + amount + '\'' +
                ", full_free='" + full_free + '\'' +
                ", is_free=" + is_free +
                ", price='" + price + '\'' +
                ", active='" + active + '\'' +
                ", check=" + check +
                ", count=" + count +
                ", activity_type=" + activity_type +
                ", full=" + full +
                ", reduce=" + reduce +
                ", gtype=" + gtype +
                ", is_delete=" + is_delete +
                ", sh_status=" + sh_status +
                ", refusal=" + refusal +
                ", delete_time='" + delete_time + '\'' +
                ", create_time='" + create_time + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoodsBean goodsBean = (GoodsBean) o;

        if (id != goodsBean.id) return false;
        if (least_num != goodsBean.least_num) return false;
        if (snum != goodsBean.snum) return false;
        if (distribution_status != goodsBean.distribution_status) return false;
        if (num != goodsBean.num) return false;
        if (did != goodsBean.did) return false;
        if (gsum != goodsBean.gsum) return false;
        if (is_up != goodsBean.is_up) return false;
        if (least_p != goodsBean.least_p) return false;
        if (least_g != goodsBean.least_g) return false;
        if (pid != goodsBean.pid) return false;
        if (sid != goodsBean.sid) return false;
        if (sgrade != goodsBean.sgrade) return false;
        if (is_free != goodsBean.is_free) return false;
        if (check != goodsBean.check) return false;
        if (count != goodsBean.count) return false;
        if (activity_type != goodsBean.activity_type) return false;
        if (full != goodsBean.full) return false;
        if (reduce != goodsBean.reduce) return false;
        if (gtype != goodsBean.gtype) return false;
        if (is_delete != goodsBean.is_delete) return false;
        if (sh_status != goodsBean.sh_status) return false;
        if (order_sn != null ? !order_sn.equals(goodsBean.order_sn) : goodsBean.order_sn != null)
            return false;
        if (gid != null ? !gid.equals(goodsBean.gid) : goodsBean.gid != null) return false;
        if (addr != null ? !addr.equals(goodsBean.addr) : goodsBean.addr != null) return false;
        if (gname != null ? !gname.equals(goodsBean.gname) : goodsBean.gname != null) return false;
        if (g_number != null ? !g_number.equals(goodsBean.g_number) : goodsBean.g_number != null)
            return false;
        if (barcode != null ? !barcode.equals(goodsBean.barcode) : goodsBean.barcode != null)
            return false;
        if (gunit != null ? !gunit.equals(goodsBean.gunit) : goodsBean.gunit != null) return false;
        if (pprice != null ? !pprice.equals(goodsBean.pprice) : goodsBean.pprice != null)
            return false;
        if (gprice != null ? !gprice.equals(goodsBean.gprice) : goodsBean.gprice != null)
            return false;
        if (descrip != null ? !descrip.equals(goodsBean.descrip) : goodsBean.descrip != null)
            return false;
        if (g_water != null ? !g_water.equals(goodsBean.g_water) : goodsBean.g_water != null)
            return false;
        if (picturl != null ? !picturl.equals(goodsBean.picturl) : goodsBean.picturl != null)
            return false;
        if (update_time != null ? !update_time.equals(goodsBean.update_time) : goodsBean.update_time != null)
            return false;
        if (sname != null ? !sname.equals(goodsBean.sname) : goodsBean.sname != null) return false;
        if (amount != null ? !amount.equals(goodsBean.amount) : goodsBean.amount != null)
            return false;
        if (full_free != null ? !full_free.equals(goodsBean.full_free) : goodsBean.full_free != null)
            return false;
        if (price != null ? !price.equals(goodsBean.price) : goodsBean.price != null) return false;
        if (active != null ? !active.equals(goodsBean.active) : goodsBean.active != null)
            return false;
        if (refusal != null ? !refusal.equals(goodsBean.refusal) : goodsBean.refusal != null)
            return false;
        if (delete_time != null ? !delete_time.equals(goodsBean.delete_time) : goodsBean.delete_time != null)
            return false;
        if (create_time != null ? !create_time.equals(goodsBean.create_time) : goodsBean.create_time != null)
            return false;
        return detail != null ? detail.equals(goodsBean.detail) : goodsBean.detail == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + least_num;
        result = 31 * result + snum;
        result = 31 * result + (order_sn != null ? order_sn.hashCode() : 0);
        result = 31 * result + distribution_status;
        result = 31 * result + num;
        result = 31 * result + (gid != null ? gid.hashCode() : 0);
        result = 31 * result + (addr != null ? addr.hashCode() : 0);
        result = 31 * result + did;
        result = 31 * result + (gname != null ? gname.hashCode() : 0);
        result = 31 * result + (int) (gsum ^ (gsum >>> 32));
        result = 31 * result + is_up;
        result = 31 * result + (g_number != null ? g_number.hashCode() : 0);
        result = 31 * result + (barcode != null ? barcode.hashCode() : 0);
        result = 31 * result + (gunit != null ? gunit.hashCode() : 0);
        result = 31 * result + (pprice != null ? pprice.hashCode() : 0);
        result = 31 * result + least_p;
        result = 31 * result + (gprice != null ? gprice.hashCode() : 0);
        result = 31 * result + least_g;
        result = 31 * result + (descrip != null ? descrip.hashCode() : 0);
        result = 31 * result + (g_water != null ? g_water.hashCode() : 0);
        result = 31 * result + (picturl != null ? picturl.hashCode() : 0);
        result = 31 * result + (update_time != null ? update_time.hashCode() : 0);
        result = 31 * result + pid;
        result = 31 * result + sid;
        result = 31 * result + sgrade;
        result = 31 * result + (sname != null ? sname.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (full_free != null ? full_free.hashCode() : 0);
        result = 31 * result + is_free;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (check ? 1 : 0);
        result = 31 * result + count;
        result = 31 * result + activity_type;
        result = 31 * result + full;
        result = 31 * result + reduce;
        result = 31 * result + gtype;
        result = 31 * result + is_delete;
        result = 31 * result + sh_status;
        result = 31 * result + (refusal != null ? refusal.hashCode() : 0);
        result = 31 * result + (delete_time != null ? delete_time.hashCode() : 0);
        result = 31 * result + (create_time != null ? create_time.hashCode() : 0);
        result = 31 * result + (detail != null ? detail.hashCode() : 0);
        return result;
    }
}
