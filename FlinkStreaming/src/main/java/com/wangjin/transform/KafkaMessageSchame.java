package com.wangjin.transform;

import com.alibaba.fastjson.JSON;
import com.wangjin.input.KafkaMessage;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

/**
 * Created by WJ on 2019/4/15.
 */
public class KafkaMessageSchame implements DeserializationSchema<KafkaMessage>, SerializationSchema<KafkaMessage> {

    private static final long serialVersionUID = 6154188370181669758L;

    @Override
    public byte[] serialize(KafkaMessage event) {
        String jsonstring = JSON.toJSONString(event);
        return jsonstring.getBytes();
    }

    @Override
    public KafkaMessage deserialize(byte[] message) throws IOException {
        String jsonString = new String(message);
        KafkaMessage kafkaMessage = JSON.parseObject(jsonString, KafkaMessage.class);
        return kafkaMessage;
    }

    @Override
    public boolean isEndOfStream(KafkaMessage nextElement) {
        return false;
    }

    @Override
    public TypeInformation<KafkaMessage> getProducedType() {
        return TypeInformation.of(KafkaMessage.class);
    }
}