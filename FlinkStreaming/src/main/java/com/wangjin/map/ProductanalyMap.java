package com.wangjin.map;

import com.alibaba.fastjson.JSONObject;
import com.wangjin.entity.OrderInfo;
import com.wangjin.entity.ProductAnaly;
import com.wangjin.util.DateUtil;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.Date;

/**
 * Created by WJ on 2019/4/16.
 */
public class ProductanalyMap implements MapFunction<String, ProductAnaly> {
    @Override
    public ProductAnaly map(String value) throws Exception {
        OrderInfo orderInfo = JSONObject.parseObject(value, OrderInfo.class);
        long productid = orderInfo.getProductid();
        Date date = orderInfo.getCreatetime();
        String timestring = DateUtil.getDateby(date.getTime(), "yyyyMM");
        Date paytime = orderInfo.getPaytime();
        long chengjiaocount =0l; //成交
        long weichegnjiao = 0;//未成交
        if(paytime != null){
            chengjiaocount = 1l;
        }else{
            weichegnjiao = 0l;
        }
        ProductAnaly productAnaly = new ProductAnaly();
        productAnaly.setProductid(productid);
        productAnaly.setDateString(timestring);
        productAnaly.setChengjiaocount(chengjiaocount);
        productAnaly.setWeichegnjiao(weichegnjiao);
        productAnaly.setGroupbyfield(timestring+productid);
        return  productAnaly;
    }
}
