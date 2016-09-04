package com.d3code.zbr.core;

import com.d3code.zbr.core.expection.ZBRException;
import com.d3code.zbr.core.handler.ZBRLogMessageHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Nottyjay on 2016/9/3 0003.
 */
public class ZBRLogServer {

    private String configFile;
    private Properties prop;

    private ZBR zbr;

    public ZBRLogServer(String configFile){
        this.configFile = configFile;
        try {
            this.prop = creaetProperties();
        }catch (IOException e){
            // TODO
        }
    }

    public void start(){
        // ======================== 准备链接用数据 =========================
        Properties kafkaProp = new Properties();
        kafkaProp.setProperty("zookeeper.connect", this.prop.getProperty("zookeeper.hosts"));
        kafkaProp.setProperty("zookeeper.connectiontimeout.ms", this.prop.getProperty("zookeeper.connection.timeout"));
        kafkaProp.setProperty("group.id", this.prop.getProperty("zookeeper.group.id"));
        String topics = this.prop.getProperty("kafka.topic");
        String[] topicTemp = null;
        String partitions = this.prop.getProperty("kafka.partition");
        String[] partitionTemp = null;
        if(topics.contains(",")){
            topicTemp = topics.replaceAll(" ", "").split(",");
            partitionTemp = partitions.replaceAll(" ", "").split(",");
        }else{
            topicTemp = new String[]{topics};
            partitionTemp = new String[]{partitions};
        }
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        Map<String, ZBRLogMessageHandler> handlerMap = new HashMap<String, ZBRLogMessageHandler>();
        for(int i = 0; i < topicTemp.length; i++){
            String topic = topicTemp[i];
            // 若没有设置对应的topic接收线程数，默认为4个
            if(partitionTemp.length < i){
                topicCountMap.put(topic, 4);
            }
            topicCountMap.put(topic, Integer.valueOf(partitionTemp[i]));

            // 查询是否有特定的handler处理。key名称为kafka.topic.${key}，value为handler的class
            String key = "kafka.topic." + topic;
            if(prop.containsKey("kafka.topic." + topic)){
                try {
                    handlerMap.put(topic, (ZBRLogMessageHandler) Class.forName(prop.getProperty(key)).newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            topic = null;
            key = null;
        }
        if(prop.containsKey("kafka.consumer")){
            zbr = new ZBR(kafkaProp, topicCountMap, handlerMap, Integer.valueOf(prop.getProperty("kafka.consumer")));
        }else{
            zbr = new ZBR(kafkaProp, topicCountMap, handlerMap);
        }
        zbr.connect();
    }

    private Properties creaetProperties() throws IOException{
        InputStream configStream = ZBRLogServer.class.getClassLoader().getParent().getResourceAsStream(configFile);
        if(configStream == null)
            configStream = ZBRLogServer.class.getResourceAsStream(configFile);
        if (configStream == null)
            throw new ZBRException("Cannot find " + this.configFile + " !!!");
        Properties properties = new Properties();
        try {
            properties.load(configStream);
        }finally {
            configStream.close();
        }
        return properties;
    }

}
