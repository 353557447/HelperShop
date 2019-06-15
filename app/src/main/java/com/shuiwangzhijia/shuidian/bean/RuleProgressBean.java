package com.shuiwangzhijia.shuidian.bean;

public class RuleProgressBean{
        //返利方式 1现金返利 2水票返利
        private int rWay;
        //返水票条件(多少桶)
        private int waterCouponCondition;
        //水票名称
        private String waterCouponName;
        //返利水票数量
        private int waterCouponCounts;
        //返现金条件(多少桶)
        private int moneyCondition;
        //返现金金额
        private String money;

        public int getrWay() {
            return rWay;
        }

        public void setrWay(int rWay) {
            this.rWay = rWay;
        }

        public int getWaterCouponCondition() {
            return waterCouponCondition;
        }

        public void setWaterCouponCondition(int waterCouponCondition) {
            this.waterCouponCondition = waterCouponCondition;
        }

        public String getWaterCouponName() {
            return waterCouponName;
        }

        public void setWaterCouponName(String waterCouponName) {
            this.waterCouponName = waterCouponName;
        }

        public int getWaterCouponCounts() {
            return waterCouponCounts;
        }

        public void setWaterCouponCounts(int waterCouponCounts) {
            this.waterCouponCounts = waterCouponCounts;
        }

        public int getMoneyCondition() {
            return moneyCondition;
        }

        public void setMoneyCondition(int moneyCondition) {
            this.moneyCondition = moneyCondition;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }