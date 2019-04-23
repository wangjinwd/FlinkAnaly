package com.wangjin.transform;

import com.wangjin.input.KafkaMessage;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;
import java.text.SimpleDateFormat;

/**
 * Created by WJ on 2019/4/15.
 */
public class CustomWatermarkExtractor implements AssignerWithPeriodicWatermarks<KafkaMessage> {

    Long currentMaxTimeStamp = 0l;
    final Long maxOutOfOrderness = 10000L;// 最大允许的乱序时间是 10s
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public long extractTimestamp(KafkaMessage event, long previousElementTimestamp) {
        Long eventTimeLong = event.getTimestamp();
        currentMaxTimeStamp = Math.max(eventTimeLong, currentMaxTimeStamp);
        return eventTimeLong;
    }

    @Nullable
    @Override
    public Watermark getCurrentWatermark() {
        return new Watermark(currentMaxTimeStamp - maxOutOfOrderness);
    }
}
