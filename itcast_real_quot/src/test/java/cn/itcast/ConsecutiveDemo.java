package cn.itcast;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.List;
import java.util.Map;

/**
 * 需求：从数据源中依次提取"c","a","b"元素
 */
public class ConsecutiveDemo {

    public static void main(String[] args) throws Exception {

        /**
         * 开发步骤：
         * 1.获取流处理执行环境
         * 2.设置但并行度
         * 3.加载数据源
         * 4.设置匹配模式，匹配"c","a","b"
         *   多次匹配"a"：组合模式
         * 5.匹配数据提取Tuple3
         * 6.数据打印
         * 7.触发执行
         */
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        //3.加载数据源
        DataStreamSource<String> source = env.fromElements("c", "d", "a", "a", "a", "d", "a", "b");
        //4.设置匹配模式，匹配"c","a","b"
        //  多次匹配"a"：组合模式
        Pattern<String, String> pattern = Pattern.<String>begin("begin").where(new SimpleCondition<String>() {
            @Override
            public boolean filter(String value) throws Exception {
                return value.equals("c");
            }
        }).followedBy("middle")
                .where(new SimpleCondition<String>() {
                    @Override
                    public boolean filter(String value) throws Exception {
                        return value.equals("a");
                    }
                })
//                .oneOrMore()
               // .consecutive() //连续匹配,需要配合量词使用
//                .allowCombinations() //允许匹配到的数据是不连续的
                .followedBy("end").where(new SimpleCondition<String>() {
                    @Override
                    public boolean filter(String value) throws Exception {
                        return value.equals("b");
                    }
                });
        //5.匹配数据提取Tuple3
        PatternStream<String> cep = CEP.pattern(source, pattern);
        cep.select(new PatternSelectFunction<String, Object>() {
            @Override
            public Object select(Map<String, List<String>> pattern) throws Exception {
                List<String> begin = pattern.get("begin");
                List<String> middle = pattern.get("middle");
                List<String> end = pattern.get("end");

                return Tuple3.of(begin,middle,end);
            }
        }).print();


        env.execute();
    }
}
