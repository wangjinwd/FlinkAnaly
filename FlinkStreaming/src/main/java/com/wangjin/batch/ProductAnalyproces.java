package com.wangjin.batch;

import com.wangjin.entity.ProductAnaly;
import com.wangjin.map.ProductanalyMap;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;

/**
 * Created by WJ on 2019/4/16.
 */
public class ProductAnalyproces {
    public static void main(String[] args) {
        final ParameterTool params = ParameterTool.fromArgs(args);

        // set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // make parameters available in the web interface
        env.getConfig().setGlobalJobParameters(params);

        // get input data
        DataSet<String> text = env.readTextFile(params.get("input"));
        DataSet<ProductAnaly> map = text.map(new ProductanalyMap());

    }
}
