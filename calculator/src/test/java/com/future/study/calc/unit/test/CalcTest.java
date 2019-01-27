package com.future.study.calc.unit.test;

import com.future.study.calc.Calc;
import com.future.study.calc.CalcBean;
import org.junit.Test;

import java.util.List;

/**
 * @author Dexterleslie.Chan
 */
public class CalcTest {
    @Test
    public void t(){
        Calc calc = new Calc();
        calc.add("福图纳锡塔德", 3.35, 2.06);
        calc.add("亚特兰大", 2.08, 3.4);
        calc.add("帕尔马", 2.4, 3.25);
        calc.add("国际米兰", 3.8, 2.09);
        calc.add("图卢兹", 2.23, 3.3);
        calc.add("托特纳姆热刺", 3, 2.4);
        calc.add("利沃诺", 2.44, 2.96);
        calc.add("威尼斯", 2.07, 3.6);
        calc.add("布雷西亚", 2.06, 3.7);
        calc.add("里泽士邦", 2.04, 3.5);
        calc.add("锡瓦斯体育", 2.5, 2.55);
        calc.add("布伦瑞克", 2.29, 3.05);
        calc.add("卡柏法伦斯 RJ", 2.07, 3.35);
        calc.add("桑托斯 SP", 2.49, 2.69);
        calc.add("米拉索 SP", 2.53, 2.62);
        calc.add("巴西紅牛 SP", 2.38, 3.05);
        calc.add("利瓦迪亚高斯", 2.95, 2.42);
        calc.add("拉米亚", 3.05, 2.31);
        calc.add("托卢卡", 2.39, 2.99);
        calc.add("昆雷塔罗", 2.96, 2.4);
        calc.add("阿利安萨", 2.38, 2.73);
        calc.add("瓦拉杜利德", 2.45, 3.1);
        calc.add("毕尔巴鄂竞技", 2.14, 3.75);
        calc.add("孟买城", 2.45, 2.48);
        calc.add("希杭", 2.62, 2.9);
        calc.add("塔拉哥纳", 2.89, 2.52);
        calc.add("沙克尼", 2.44, 2.52);
        calc.add("内坦亚马卡比", 2.93, 2.16);
        calc.add("莫亨巴根", 2.16, 2.98);
        calc.add("科特赖克", 2.9, 2.29);
        calc.add("洛默尔 SK", 2.01, 2.99);
        calc.add("贝尔格拉诺", 2.71, 2.88);
        calc.add("纽厄尔老男孩", 3.15, 2.37);
//        CalcBean calcBean = calc.getMaxProfit();
//        System.out.println(calcBean);
        List<CalcBean> beanList = calc.listSorted();
        for(CalcBean calcBean : beanList){
            if(calcBean.isFound()) {
                System.out.println(calcBean);
            }
        }
    }
}
