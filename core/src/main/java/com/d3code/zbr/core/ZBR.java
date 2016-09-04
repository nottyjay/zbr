package com.d3code.zbr.core;

import com.d3code.zbr.core.handler.DefaultZBRLogMessageHandler;
import com.d3code.zbr.core.handler.ZBRLogMessageHandler;
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
 * Created by aaron on 16-9-4.
 */
public class ZBR {

    private Properties properties;
    private Map<String, Integer> topicMap;
    private Map<String, ZBRLogMessageHandler> handlerMap;
    private ZBRLogMessageHandler defaultHandler;
    private Integer consumerNum;

    public ZBR(){
        defaultHandler = new DefaultZBRLogMessageHandler();
    }

    public ZBR(Properties properties, Map<String, Integer> topicMap){
        this(properties, topicMap, null, 4);
    }

    public ZBR(Properties properties, Map<String, Integer> topicMap, Integer consumerNum){
        this(properties, topicMap, null, consumerNum);
    }

    public ZBR(Properties properties, Map<String, Integer> topicMap, Map<String, ZBRLogMessageHandler> handlerMap){
        this(properties, topicMap, handlerMap, 4);
    }

    public ZBR(Properties properties, Map<String, Integer> topicMap, Map<String, ZBRLogMessageHandler> handlerMap, Integer consumerNum){
        this.properties = properties;
        this.topicMap = topicMap;
        this.handlerMap = handlerMap;
        this.consumerNum = consumerNum;
        defaultHandler = new DefaultZBRLogMessageHandler();
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setTopicMap(Map<String, Integer> topicMap) {
        this.topicMap = topicMap;
    }

    public void setHandlerMap(Map<String, ZBRLogMessageHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public void setDefaultHandler(ZBRLogMessageHandler defaultHandler) {
        this.defaultHandler = defaultHandler;
    }

    public void setConsumerNum(Integer consumerNum) {
        this.consumerNum = consumerNum;
    }

    public void connect(){
        ConsumerConnector connector = Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
        Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = connector.createMessageStreams(topicMap);
        ExecutorService executor = Executors.newFixedThreadPool(sumAllTopicPartitionCount());
        for(final String topic : topicMap.keySet()) {
            List<KafkaStream<byte[], byte[]>> streams = messageStreams.get(topic);
            for(final KafkaStream<byte[], byte[]> stream : streams){
                executor.submit(new Runnable() {
                    private ZBRLogMessageHandler handler;
                    public void run() {
                        if(handlerMap != null && handlerMap.containsKey(topic)){
                            handler = handlerMap.get(topic);
                        }else{
                            handler = defaultHandler;
                        }
                        for(MessageAndMetadata<byte[], byte[]> msgAndMetadata : stream){
                            handler.readMessage(msgAndMetadata);
                        }
                    }
                });
            }
        }
    }

    private int sumAllTopicPartitionCount(){
        int sum = 0;
        for(int count : topicMap.values()){
            sum += count;
        }
        return sum;
    }
}
