package com.d3code.zbr.core.handler;

import kafka.message.MessageAndMetadata;

/**
 * Created by Nottyjay on 2016/9/3 0003.
 */
public abstract class ZBRLogMessageHandler {

    public abstract void readMessage(MessageAndMetadata<byte[], byte[]> message);
}
