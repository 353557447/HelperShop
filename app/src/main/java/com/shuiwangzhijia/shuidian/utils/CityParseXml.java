package com.shuiwangzhijia.shuidian.utils;

import android.util.Xml;

import com.shuiwangzhijia.shuidian.bean.CityBean;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * created by wangsuli on 2018/9/27.
 */
public class CityParseXml {

    private static String letter;

    public static List<CityBean> parserXml(InputStream inputStream) throws Exception {
        //申明集合对象
        List<CityBean> list = null;
        CityBean channel = null;

        //回去Xmlpullparser 解析的实例
        XmlPullParser parser = Xml.newPullParser();

        //设置XmlPullParser 的参数
        parser.setInput(inputStream, "utf-8");

        //获取事件类型
        int type = parser.getEventType();
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                case XmlPullParser.START_TAG://解析开始标签
                    //具体判断一下,解析到那个节点
                    if ("dict".equals(parser.getName())) {
                        //创建一个集合对象
                        list = new ArrayList<CityBean>();
                    } else if ("key".equals(parser.getName())) {
                        //创建Channel对象
                        channel = new CityBean();
                        letter = parser.nextText();
                        channel.setType(1);
                        channel.setLetter(letter);
                        list.add(channel);
                    } else if ("array".equals(parser.getName())) {
                        //不做处理
                    } else if ("string".equals(parser.getName())) {
                        //获取id值
                        channel = new CityBean();
                        String city = parser.nextText();
                        channel.setCity(city);
                        channel.setLetter(letter);
                        channel.setType(0);
                        list.add(channel);
                    }
                    break;
                case XmlPullParser.END_TAG://解析结束标签
                    //判断结束标签
                    break;
            }
            //不停向下解析
            type = parser.next();
        }
        return list;
    }

}
