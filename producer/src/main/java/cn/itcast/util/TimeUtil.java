package cn.itcast.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class TimeUtil {

    public static long startTime = 0L;//开市时间-9:30
    public static long endTime = 0L; //闭市时间-15:00

    static {
        //1.新建Calendar对象,设置日期
        Calendar instance = Calendar.getInstance();
//        instance.setTime(new Date());
        //2.设置开市时间
        instance.set(Calendar.HOUR_OF_DAY,5); //24小时制的5点
        instance.set(Calendar.MINUTE,30);
        instance.set(Calendar.SECOND,0);
        //获取开市时间
        startTime = instance.getTime().getTime();
        //3.设置闭市时间
        instance.set(Calendar.HOUR_OF_DAY,23); //闭市时间设置大一点,要不然数据进不来
        instance.set(Calendar.MINUTE,59);
        //获取闭市时间
        endTime = instance.getTime().getTime();
    }
//测试时间戳用的
   /* public static void main(String[] args) {
        System.out.println(startTime);
        System.out.println(endTime);
    }*/
}
