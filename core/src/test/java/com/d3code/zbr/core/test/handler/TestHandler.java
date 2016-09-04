package com.d3code.zbr.core.test.handler;

import com.d3code.zbr.core.handler.ZBRLogMessageHandler;
import kafka.message.MessageAndMetadata;

/**
 * Created by Nottyjay on 2016/9/4 0004.
 */
public class TestHandler extends ZBRLogMessageHandler {

    public void readMessage(MessageAndMetadata<byte[], byte[]> message) {
        System.out.println("TestHandler: " + new String(message.message()));
    }
}
