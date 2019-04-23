package com.wangjin.map;

import com.alibaba.fastjson.JSON;
import com.wangjin.entity.PindaoRD;
import com.wangjin.input.KafkaMessage;
import com.wangjin.log.UserscanLog;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * Created by WJ on 2019/4/15.
 */
public class PindaoKafkaMap implements MapFunction<KafkaMessage,PindaoRD> {
    @Override
    public PindaoRD map(KafkaMessage input) throws Exception {
        String jsonmessage = input.getJsonmessage();
        UserscanLog userscanLog = JSON.parseObject(jsonmessage, UserscanLog.class);
        Long pingdaoid = userscanLog.getPingdaoid();
        PindaoRD pindaoRD = new PindaoRD();
        pindaoRD.setPingdaoid(pingdaoid);
        pindaoRD.setCount(Long.valueOf(input.getCount()+""));
        return pindaoRD;
    }
}
