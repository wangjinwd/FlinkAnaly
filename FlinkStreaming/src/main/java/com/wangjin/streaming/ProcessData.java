package com.wangjin.streaming;

import com.wangjin.entity.PindaoRD;
import com.wangjin.input.KafkaMessage;
import com.wangjin.map.PindaoKafkaMap;
import com.wangjin.reduce.PindaoReduce;
import com.wangjin.transform.CustomWatermarkExtractor;
import com.wangjin.transform.KafkaMessageSchame;
import com.wangjin.util.RedisUtil;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import redis.clients.jedis.Jedis;

import java.util.Properties;

/**
 * Created by WJ on 2019/4/15.
 */
public class ProcessData {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //checkpoint
        env.enableCheckpointing(60000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(30000);
        env.getCheckpointConfig().setCheckpointTimeout(10000);
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        //读取kafka数据
        String topic = "test";
        Properties prop = new Properties();
        prop.setProperty("bootstrap.servers", "198.168.184.203:9092,198.168.184.204:9092,198.168.184.205:9092");
        prop.setProperty("group.id", "con1");
        FlinkKafkaConsumer010  flinkKafkaConsumer = new FlinkKafkaConsumer010<KafkaMessage>(topic, new KafkaMessageSchame(), prop);
        DataStream<KafkaMessage> input = env.addSource(flinkKafkaConsumer).assignTimestampsAndWatermarks(new CustomWatermarkExtractor());
        //数据处理
        DataStream<PindaoRD> map = input.map(new PindaoKafkaMap());
        DataStream<PindaoRD> reduce = map.keyBy("pingdaoid").countWindow(Long.valueOf(10), Long.valueOf(5)).reduce(new PindaoReduce());

        //数据下沉
        reduce.addSink(new SinkFunction<PindaoRD>() {
            @Override
            public void invoke(PindaoRD value) {
                long count = value.getCount();
                long pindaoid = value.getPingdaoid();
                System.out.println("输出==pindaoid"+pindaoid+":"+count);
                Jedis jedis = RedisUtil.getJedis();
                jedis.lpush("pingdaord:" + pindaoid, count + "");
                RedisUtil.closeResource(jedis);
            }
        }).name("pdrdreduce");
        try {
            env.execute("pindaoredian");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}
