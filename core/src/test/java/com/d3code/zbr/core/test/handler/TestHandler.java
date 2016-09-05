package com.d3code.zbr.core.test.handler;

import com.d3code.zbr.core.handler.ZBRLogMessageHandler;
import com.d3code.zbr.core.serializer.JavaSerializer;
import com.d3code.zbr.core.serializer.Serializer;
import com.d3code.zbr.core.test.bean.Message;
import kafka.message.MessageAndMetadata;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;

/**
 * Created by Nottyjay on 2016/9/4 0004.
 */
public class TestHandler extends ZBRLogMessageHandler {

    public void readMessage(MessageAndMetadata<byte[], byte[]> message) {
        Serializer javaSerializer = new JavaSerializer();
        Message obj = null;
        try {
            obj = (Message) javaSerializer.deserialize(message.message());
            if(obj != null) {
                System.out.println("TestHandler: " + obj.toString());
                System.out.println(new Date());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
