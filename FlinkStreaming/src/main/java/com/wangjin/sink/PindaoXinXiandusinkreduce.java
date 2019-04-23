package com.wangjin.sink;

import com.wangjin.entity.PidaoXinXianDu;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

/**
 * Created by WJ on 2019/4/16.
 */
public class PindaoXinXiandusinkreduce implements SinkFunction<PidaoXinXianDu> {

    @Override
    public void invoke(PidaoXinXianDu value, Context context) throws Exception {

    }


}
