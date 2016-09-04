package com.d3code.zbr.core;

/**
 * Created by Nottyjay on 2016/9/3 0003.
 */
public class ZBRLog {

    private static String ZBR_CONFIG_FILE = "zbr.config";

    public static void main(String[] args) {
        if(args.length > 0){
            ZBR_CONFIG_FILE = args[0];
        }
        ZBRLogServer server = new ZBRLogServer(ZBR_CONFIG_FILE);
        server.start();
    }
}
