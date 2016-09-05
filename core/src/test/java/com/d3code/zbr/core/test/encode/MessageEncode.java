package com.d3code.zbr.core.test.encode;

import com.d3code.zbr.core.serializer.JavaSerializer;
import com.d3code.zbr.core.serializer.Serializer;
import com.d3code.zbr.core.test.bean.Message;
import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

import java.io.IOException;

/**
 * Created by jileilei on 16/9/5.
 */
public class MessageEncode implements Encoder<Message> {

    public MessageEncode(VerifiableProperties props) {
    }

    public byte[] toBytes(Message message) {
        Serializer javaSerializer = new JavaSerializer();
        try {
            return javaSerializer.serialize(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
