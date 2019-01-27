package com.future.study.calc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Dexterleslie.Chan
 */
public class Calc {
    /**
     * 总投注额
     */
    private int totalAmount = 1000;
    /**
     * 最小盈亏比例阀值
     */
    private double valve = 0.25;

    private List<CalcBean> calcBeans = new ArrayList<>();

    public void setTotalAmount(int totalAmount) {
        if(totalAmount < 100){
            totalAmount = 100;
        }
        this.totalAmount = totalAmount;
    }

    public void setValve(double valve) {
        if(valve < 0.25){
            valve = 0.25;
        }
        this.valve = valve;
    }

    public void add(String description, double odds1, double odds2){
        CalcBean calcBean = new CalcBean(description, odds1 ,odds2);
        calcBeans.add(calcBean);
    }

    /**
     *
     * @return
     */
    public CalcBean getMaxProfit(){
        if(calcBeans == null || calcBeans.size() <= 0){
            return null;
        }
        CalcBean tempBean = null;
        for(CalcBean bean1 : calcBeans){
            cal(bean1);
            if(bean1.isFound()){
                if(tempBean == null){
                    tempBean = bean1;
                }

                double profitA = (tempBean.getProfitPortion1() + tempBean.getProfitPortion2()) / 2;
                double profitB = (bean1.getProfitPortion1() + bean1.getProfitPortion2()) / 2;
                if(profitB > profitA){
                    tempBean = bean1;
                }
            }
        }
        return tempBean;
    }

    /**
     *
     * @return
     */
    public List<CalcBean> listSorted(){
        if(this.calcBeans != null && this.calcBeans.size() > 0) {
            for(CalcBean calcBean : this.calcBeans){
                cal(calcBean);
            }
            Collections.sort(this.calcBeans, comparator);
        }
        return this.calcBeans;
    }

    private Comparator<CalcBean> comparator = new Comparator<CalcBean>() {
        @Override
        public int compare(CalcBean o1, CalcBean o2) {
            double profitA = (o1.getProfitPortion1() + o1.getProfitPortion2()) / 2;
            double profitB = (o2.getProfitPortion1() + o2.getProfitPortion2()) / 2;
            return Double.valueOf(profitB).compareTo(profitA);
        }
    };

    /**
     *
     * @param calc
     * @return
     */
    public void cal(CalcBean calc){
        double odds1 = calc.getOdds1();
        double odds2 = calc.getOdds2();

        double dMin = Double.MAX_VALUE;
        int profitAmount1 = 0;
        int profitAmount2 = 0;
        double profitPortion1 = 0;
        double profitPortion2 = 0;
        boolean isFound = false;
        for(int i = 0; i <= totalAmount; i = i + 10){
            int amount1 = totalAmount - i;

            double d1 = (odds1 - 1) * i;
            double d2 = (odds2 - 1) * amount1;

            double d11 = d1 - amount1;
            double d22 = d2 - i;

            if(d11 >= 0 && d22 >= 0){
                d11 = d11 / totalAmount;
                d22 = d22 / totalAmount;
                if(d11 >= valve && d22 >= valve){
                    double dAbs = Math.abs(d11 - d22);
                    if(dAbs <= dMin){
                        dMin = dAbs;
                        isFound = true;

                        profitAmount1 = i;
                        profitAmount2 = amount1;
                        profitPortion1 = d11;
                        profitPortion2 = d22;
                    }
                    //System.out.println(i + "@" + odds1 + "(" + d11 + "), " + amount1 + "@" + odds2  + "(" + d22 + ")");
                }
            }
        }

        if(isFound){
//            System.out.println(profitAmount1 + "@" + odds1 + "(" + profitPortion1 + "), " + profitAmount2 + "@" + odds2  + "(" + profitPortion2 + ")");
            calc.setProfitAmount1(profitAmount1);
            calc.setProfitAmount2(profitAmount2);
            calc.setProfitPortion1(profitPortion1);
            calc.setProfitPortion2(profitPortion2);
            calc.setFound(isFound);
        }
    }
}
