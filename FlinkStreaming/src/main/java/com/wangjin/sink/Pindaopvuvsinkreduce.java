package com.wangjin.sink;

import com.wangjin.entity.PidaoPvUv;
import com.wangjin.util.HbaseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/30 0030.
 */
public class Pindaopvuvsinkreduce implements SinkFunction<PidaoPvUv> {
    @Override
    public void invoke(PidaoPvUv value, Context context) throws Exception {
        System.out.println( "recuducesinkd --pidaoPvUv=="+value);
        long pingdaoid = value.getPingdaoid();
        long pvcount = value.getPvcount();
        long uvcount = value.getUvcount();
        String timestring = value.getTimestring();
        String pv = HbaseUtil.getdata("pindaoinfo", pingdaoid + timestring, "info", "pv");
        String uv = HbaseUtil.getdata("pindaoinfo",pingdaoid+timestring,"info","uv");
        if(StringUtils.isNotBlank(pv)){
            pvcount += pvcount + Long.valueOf(pv);
        }
        if(StringUtils.isNotBlank(uv)) {
            uvcount += uvcount + Long.valueOf(uv);
        }

        Map<String,String> datamap = new HashMap<String,String>();
        datamap.put("pv",pvcount+"");
        datamap.put("uv",uvcount+"");
        System.out.println( "pidaopvuv---- HbaseUtil.put(pindaoinfo+"+","+pingdaoid+timestring+",info"+datamap+")");
        HbaseUtil.put("pindaoinfo",pingdaoid+timestring,"info",datamap);

    }
}
