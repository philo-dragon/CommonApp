package com.pfl.demo;

import java.util.ArrayList;
import java.util.List;

public class DemoLocalData {
    public List<String> getData() {

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(System.currentTimeMillis() + "");
        }
        return list;
    }

    public void save(List<String> data) {

    }
}
