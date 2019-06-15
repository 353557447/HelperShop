package com.shuiwangzhijia.shuidian.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lijn on 2018/10/26.
 */

public class GenerateList {
    public static List<Integer> getList(int size){
        List<Integer> list=new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(size);
        }
        return list;
    }
}
