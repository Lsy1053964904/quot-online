package cn.itcast.map;

import cn.itcast.avro.SseAvro;
import cn.itcast.avro.SzseAvro;
import cn.itcast.bean.CleanBean;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;

import java.math.BigDecimal;

/**
 * 沪市数据:avro对象转换成bean对象
 */
public class SzseMap implements MapFunction<SzseAvro, CleanBean> {

    @Override
    public CleanBean map(SzseAvro value) throws Exception {
        return new CleanBean(
                value.getMdStreamID().toString(),
                value.getSecurityID().toString(),
                value.getSymbol().toString(),
                value.getTradeVolume(),
                value.getTotalValueTraded(),
                BigDecimal.valueOf(value.getPreClosePx()),
                BigDecimal.valueOf(value.getOpenPrice()),
                BigDecimal.valueOf(value.getHighPrice()),
                BigDecimal.valueOf(value.getLowPrice()),
                BigDecimal.valueOf(value.getTradePrice()),
                value.getTimestamp(),
                "szse"
        );
    }
}
