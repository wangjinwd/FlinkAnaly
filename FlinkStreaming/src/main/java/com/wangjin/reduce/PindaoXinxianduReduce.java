package com.wangjin.reduce;

import com.wangjin.entity.PidaoXinXianDu;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 * Created by WJ on 2019/4/16.
 */
public class PindaoXinxianduReduce implements ReduceFunction<PidaoXinXianDu> {

    @Override
    public PidaoXinXianDu reduce(PidaoXinXianDu value1, PidaoXinXianDu value2) throws Exception {
        System.out.println( "value1=="+value1);
        System.out.println( "value2=="+value2);
        long pingdaoid = value1.getPingdaoid();
        long timestampvalue = value1.getTimestamp();
        long timestring = value1.getTimestring();

        long newUserCount = value1.getNewUserCount();
        long oldUserCount = value1.getOldUserCount();

        long newUserCount1 = value2.getNewUserCount();
        long oldUserCount1 = value2.getOldUserCount();

        PidaoXinXianDu pidaoXinXianDu = new PidaoXinXianDu();
        pidaoXinXianDu.setPingdaoid(pingdaoid);
        pidaoXinXianDu.setTimestamp(timestampvalue);
        pidaoXinXianDu.setTimestring(timestring);

        pidaoXinXianDu.setNewUserCount(newUserCount + newUserCount1);
        pidaoXinXianDu.setOldUserCount(oldUserCount + oldUserCount1);
        return  pidaoXinXianDu;
    }
}
