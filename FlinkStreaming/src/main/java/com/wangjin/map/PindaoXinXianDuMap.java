package com.wangjin.map;

import com.alibaba.fastjson.JSON;
import com.wangjin.Dao.PdvisterDao;
import com.wangjin.Dao.UserState;
import com.wangjin.entity.PidaoXinXianDu;
import com.wangjin.input.KafkaMessage;
import com.wangjin.log.UserscanLog;
import com.wangjin.util.DateUtil;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

/**
 * Created by WJ on 2019/4/16.
 */
public class PindaoXinXianDuMap implements FlatMapFunction<KafkaMessage,PidaoXinXianDu> {
    @Override
    public void flatMap(KafkaMessage kafkaMessage, Collector<PidaoXinXianDu> out) throws Exception {
        String jsonmessage = kafkaMessage.getJsonmessage();
        UserscanLog userscanLog = JSON.parseObject(jsonmessage, UserscanLog.class);

        Long timestamp = kafkaMessage.getTimestamp();
        Long userid = userscanLog.getUserid();
        Long pingdaoid = userscanLog.getPingdaoid();
        PidaoXinXianDu pidaoXinXianDu = new PidaoXinXianDu();
        pidaoXinXianDu.setTimestamp(timestamp);
        pidaoXinXianDu.setPingdaoid(userscanLog.getPingdaoid());


        long hourTime = DateUtil.getDatebyConditon(timestamp, "yyyyMMddhh");
        long dayTime = DateUtil.getDatebyConditon(timestamp, "yyyyMMdd");
        long monthTime = DateUtil.getDatebyConditon(timestamp, "yyyyMM");

        UserState userState = PdvisterDao.getUserSatebyvistertime(userid + "", timestamp);
        boolean isFirsthour = userState.isFisrthour();
        boolean isFisrtday = userState.isFisrtday();
        boolean isFisrtmonth = userState.isFisrtmonth();

        /**
         * 新增用户
         */
        long newuser = 0l;
        if(userState.isnew()){
            newuser = 1l;
        }
        pidaoXinXianDu.setNewUserCount(newuser);

        /**
         * 小时
         */
        long olduser = 0l;
        if(!userState.isnew()&&isFirsthour){
            olduser = 1l;
        }
        pidaoXinXianDu.setOldUserCount(olduser);
        pidaoXinXianDu.setTimestring(hourTime);
        pidaoXinXianDu.setGroupByFields(hourTime + pingdaoid);
        out.collect(pidaoXinXianDu);
        System.out.println("小时=="+pidaoXinXianDu);
        /**
         * 天
         */
        olduser = 0l;
        if(!userState.isnew()&&isFisrtday){
            olduser = 1l;
        }
        pidaoXinXianDu.setOldUserCount(olduser);
        pidaoXinXianDu.setTimestring(dayTime);
        pidaoXinXianDu.setGroupByFields(dayTime+pingdaoid);
        out.collect(pidaoXinXianDu);
        System.out.println("小时=="+pidaoXinXianDu);
        /**
         * 月
         */
        olduser = 0l;
        if(!userState.isnew()&&isFisrtmonth){
            olduser = 1l;
        }
        pidaoXinXianDu.setOldUserCount(olduser);
        pidaoXinXianDu.setTimestring(monthTime);
        pidaoXinXianDu.setGroupByFields(monthTime + pingdaoid);
        out.collect(pidaoXinXianDu);
        System.out.println("小时=="+pidaoXinXianDu);


    }
}
