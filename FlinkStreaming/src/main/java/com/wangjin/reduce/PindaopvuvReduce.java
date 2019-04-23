package com.wangjin.reduce;

import com.wangjin.entity.PidaoPvUv;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 * Created by Administrator on 2018/10/28 0028.
 */
public class PindaopvuvReduce implements ReduceFunction<PidaoPvUv> {

    @Override
    public PidaoPvUv reduce(PidaoPvUv value1,PidaoPvUv value2) throws Exception {
        System.out.println( "value1=="+value1);
        System.out.println( "value2=="+value2);
        long pingdaoid = value1.getPingdaoid();
        long timestampvalue = value1.getTimestamp();
        String timestring = value1.getTimestring();
        long pvcountvalue1 = value1.getPvcount();
        long uvcountvalue1 = value1.getUvcount();

        long pvcountvalue2 = value2.getPvcount();
        long uvcountvalue2 = value2.getUvcount();

        PidaoPvUv pidaoPvUv = new PidaoPvUv();
        pidaoPvUv.setPingdaoid(pingdaoid);
        pidaoPvUv.setTimestamp(timestampvalue);
        pidaoPvUv.setTimestring(timestring);
        pidaoPvUv.setPvcount(pvcountvalue1+pvcountvalue2);
        pidaoPvUv.setUvcount(uvcountvalue1+uvcountvalue2);
        System.out.println( "recuduce --pidaoPvUv=="+pidaoPvUv);
        return  pidaoPvUv;
    }
}
