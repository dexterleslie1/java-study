package com.future.study.calc;

/**
 * @author Dexterleslie.Chan
 */
public class CalcBean {
    private String description = null;
    private double odds1 = 0;
    private double odds2 = 0;

    private int profitAmount1 = 0;
    private int profitAmount2 = 0;
    private double profitPortion1 = 0;
    private double profitPortion2 = 0;

    private boolean isFound = false;

    /**
     *
     * @param description
     * @param odds1
     * @param odds2
     */
    public CalcBean(String description, double odds1, double odds2){
        this.description = description;
        this.odds1 = odds1;
        this.odds2 = odds2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getOdds1() {
        return odds1;
    }

    public void setOdds1(double odds1) {
        this.odds1 = odds1;
    }

    public double getOdds2() {
        return odds2;
    }

    public void setOdds2(double odds2) {
        this.odds2 = odds2;
    }

    public int getProfitAmount1() {
        return profitAmount1;
    }

    public void setProfitAmount1(int profitAmount1) {
        this.profitAmount1 = profitAmount1;
    }

    public int getProfitAmount2() {
        return profitAmount2;
    }

    public void setProfitAmount2(int profitAmount2) {
        this.profitAmount2 = profitAmount2;
    }

    public double getProfitPortion1() {
        return profitPortion1;
    }

    public void setProfitPortion1(double profitPortion1) {
        this.profitPortion1 = profitPortion1;
    }

    public double getProfitPortion2() {
        return profitPortion2;
    }

    public void setProfitPortion2(double profitPortion2) {
        this.profitPortion2 = profitPortion2;
    }

    public boolean isFound() {
        return isFound;
    }

    public void setFound(boolean found) {
        isFound = found;
    }

    @Override
    public String toString(){
        String string1 = "";
        if(isFound){
            string1 = description + " - " + profitAmount1 + "@" + odds1 + "(" + profitPortion1 + "), " + profitAmount2 + "@" + odds2  + "(" + profitPortion2 + ")";
        }else{
            string1 = description + " - " + "未知@" + odds1 + "(未知), 未知@" + odds2  + "(未知)";
        }
        return string1;
    }
}
