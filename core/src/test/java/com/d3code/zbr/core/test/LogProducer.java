package com.d3code.zbr.core.test;

import com.d3code.zbr.core.test.bean.Message;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.*;

public class LogProducer {

    private static final String TOPIC = "kafka";
    private static final String CONTENT = "This is a single message";
    private static final String BROKER_LIST = "192.168.199.160:9092";
    private static final String SERIALIZER_CLASS = "com.d3code.zbr.core.test.encode.MessageEncode";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("serializer.class", SERIALIZER_CLASS);
        props.put("metadata.broker.list", BROKER_LIST);

        ProducerConfig config = new ProducerConfig(props);
        Producer<String, Message> producer = new Producer<String, Message>(config);

        //Send one message.
//        KeyedMessage<String, Message> message =
//                new KeyedMessage<String, Message>(TOPIC, CONTENT);
//        producer.send(message);

        //Send multiple messages.
        List<KeyedMessage<String,Message>> messages =
                new ArrayList<KeyedMessage<String, Message>>();
        System.out.println(new Date());
        for (int i = 0; i < 50000; i++) {
            messages.add(new KeyedMessage<String, Message>
                    (TOPIC, Message.create("This is message " + i, new Date())));
        }
        producer.send(messages);
    }
}