package com.shuiwangzhijia.shuidian.event;

/**
 * created by wangsuli on 2018/9/27.
 */
public class TicketEvent {
    public int count;
    public double amount;
    public String data;
    public TicketEvent(int count, double amount, String data) {
        this.count=count;
        this.amount=amount;
        this.data=data;
    }
}
