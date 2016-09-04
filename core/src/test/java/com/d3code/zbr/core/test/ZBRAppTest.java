package com.d3code.zbr.core.test;

import com.d3code.zbr.core.ZBRLogServer;

/**
 * Created by Nottyjay on 2016/9/4 0004.
 */
public class ZBRAppTest {

    public static void main(String[] args) {
        ZBRLogServer server = new ZBRLogServer("/kafka.properties");
        server.start();
    }
}
