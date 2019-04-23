package com.wangjin.control;

import com.alibaba.fastjson.JSON;
import com.wangjin.input.KafkaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by WJ on 2019/4/14.
 */
@Controller
@RequestMapping("DsInfoCollectService")
public class DsInfoCollectService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @RequestMapping("webInfoCollectService")
    public void webInfoCollectService(@RequestBody String jsonstr,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        //业务开始
        request.setCharacterEncoding("UTF-8");
        System.out.println(jsonstr);
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setJsonmessage(jsonstr);
        kafkaMessage.setCount(1);
        kafkaMessage.setTimestamp(new Date().getTime());

        jsonstr = JSON.toJSONString(kafkaMessage);
        kafkaTemplate.send("test", "key", jsonstr);
        //业务结束
        PrintWriter printWriter = getWriter(response);
        response.setStatus(HttpStatus.OK.value());
        printWriter.write("success");
        closeprintwriter(printWriter);
    }
    private PrintWriter getWriter(HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        ServletOutputStream outputStream = null;
        PrintWriter printWriter = null;
        try {
            outputStream = response.getOutputStream();
            printWriter = new PrintWriter(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  printWriter;
    }
    private void closeprintwriter(PrintWriter printWriter){
        printWriter.flush();
        printWriter.close();
    }
}
