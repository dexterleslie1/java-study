package com.future.study.calc.unit.test;

import org.junit.Test;

/**
 * @author Dexterleslie.Chan
 */
public class CalcTest {
    @Test
    public void t(){
        double odds1 = 2.16;
        double odds2 = 3.2;
        int totalAmount = 1000;
        double valve1 = 0.25;

        double dMin = Double.MAX_VALUE;
        int profitAmount1 = 0;
        int profitAmount2 = 0;
        double profitPortion1 = 0;
        double profitPortion2 = 0;
        boolean isFound = false;
        for(int i = 0; i <= totalAmount; i = i + 5){
            int amount1 = totalAmount - i;

            double d1 = (odds1 - 1) * i;
            double d2 = (odds2 - 1) * amount1;

            double d11 = d1 - amount1;
            double d22 = d2 - i;

            if(d11 >= 0 && d22 >= 0){
                d11 = d11 / totalAmount;
                d22 = d22 / totalAmount;
                if(d11 >= valve1 && d22 >= valve1){
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
            System.out.println(profitAmount1 + "@" + odds1 + "(" + profitPortion1 + "), " + profitAmount2 + "@" + odds2  + "(" + profitPortion2 + ")");
        }
    }
}
