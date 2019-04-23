package com.wangjin.streaming;

import com.wangjin.entity.PidaoPvUv;
import com.wangjin.input.KafkaMessage;
import com.wangjin.map.PindaopvuvMap;
import com.wangjin.reduce.PindaopvuvReduce;
import com.wangjin.sink.Pindaopvuvsinkreduce;
import com.wangjin.transform.CustomWatermarkExtractor;
import com.wangjin.transform.KafkaMessageSchame;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;

import java.util.Properties;

/**
 * Created by Administrator on 2018/10/27 0027.
 */
public class PvUvData {
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
        DataStream<PidaoPvUv> map = input.flatMap(new PindaopvuvMap());
        DataStream<PidaoPvUv> reduce = map.keyBy("groupbyfield")
                .countWindow(50)
                .reduce(new PindaopvuvReduce());
        reduce.addSink(new Pindaopvuvsinkreduce()).name("pdpvuvreduce");
        try {
            env.execute("pindaossfx");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
