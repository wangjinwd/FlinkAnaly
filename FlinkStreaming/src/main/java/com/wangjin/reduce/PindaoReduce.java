package com.wangjin.reduce;

import com.wangjin.entity.PindaoRD;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 * Created by WJ on 2019/4/15.
 */
public class PindaoReduce implements ReduceFunction<PindaoRD> {
    @Override
    public PindaoRD reduce(PindaoRD value1, PindaoRD value2) throws Exception {
        PindaoRD pindaoRD = new PindaoRD();
        pindaoRD.setPingdaoid(value1.getPingdaoid());
        pindaoRD.setCount(value1.getCount()+value2.getCount());
        return pindaoRD;
    }
}
