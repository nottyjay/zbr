package com.d3code.zbr.core.handler;

import kafka.message.MessageAndMetadata;

/**
 * Created by aaron on 16-9-4.
 */
public class DefaultZBRLogMessageHandler extends ZBRLogMessageHandler {

    public void readMessage(MessageAndMetadata<byte[], byte[]> message){
        System.out.println(new String(message.message()));
    }
}
