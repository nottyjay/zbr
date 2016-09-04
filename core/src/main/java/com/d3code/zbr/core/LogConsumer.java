package com.d3code.zbr.core;

import com.google.common.collect.ImmutableMap;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Nottyjay on 2016/9/3 0003.
 */
public class LogConsumer {

    private static final String ZOOKEEPER = "192.168.199.218:2181";
    private static final String GROUP_NAME = "test";
    private static final String TOPIC_NAME = "kafka";
    private static final int CONSUMER_NUM = 1;
    private static final int PARTITION_NUM = 4;

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("zookeeper.connect", ZOOKEEPER);
        properties.put("zookeeper.connectiontimeout.ms", "1000000");
//        properties.put("metadata.broker.list", "192.168.199.218:9092");
        properties.put("group.id", GROUP_NAME);

        ConsumerConfig consumerConfig = new ConsumerConfig(properties);
        ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);
        Map<String, List<KafkaStream<byte[], byte[]>>> topicMessageStreams = consumerConnector.createMessageStreams(ImmutableMap.of(TOPIC_NAME, PARTITION_NUM));
        List<KafkaStream<byte[], byte[]>> streams = topicMessageStreams.get(TOPIC_NAME);

        ExecutorService executor = Executors.newFixedThreadPool(CONSUMER_NUM);

        for(final KafkaStream<byte[], byte[]> stream : streams){
            executor.submit(new Runnable() {
                public void run() {
                    for(MessageAndMetadata<byte[], byte[]> msgAndMetadata : stream){
                        System.out.println(new String(msgAndMetadata.message()));
                    }
                }
            });
        }

    }
}
