package interface_adapter.simulate_pulls;

import org.jfree.chart.JFreeChart;

public class SimulateState {
    private JFreeChart chart;
    private double mean = 0;
    private double median = 0;
    private double Q1 = 0;
    private double Q3 = 0;

    // error state will show the error that has occured
    private String errorState = "";

    public String getErrorState() {
        return this.errorState;
    }
    public JFreeChart getChart() {
        return chart;
    }
    public double getMean(){
        return mean;
    }
    public double getMedian(){
        return median;
    }
    public double getQ1(){
        return Q1;
    }
    public double getQ3(){
        return Q3;
    }

    public void setErrorState(String errorState) {
        this.errorState = errorState;
    }
    public void setChart(JFreeChart chart){
        this.chart = chart;
    }
    public void setMean(double mean){
        this.mean = mean;
    }
    public void setMedian(double median){
        this.median = median;
    }
    public void setQ1(double q1){
        this.Q1 = q1;
    }
    public void setQ3(double q3){
        this.Q3 = q3;
    }

}
