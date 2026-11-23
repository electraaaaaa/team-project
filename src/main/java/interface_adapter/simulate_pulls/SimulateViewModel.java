package interface_adapter.simulate_pulls;

import view.SimulateView;

import java.util.ArrayList;
import java.util.List;

public class SimulateViewModel {
    private final List<SimulateViewModelListener> listeners = new ArrayList<>();

    private double[] results;
    private double mean = 0;
    private double median = 0;
    private double Q1 = 0;
    private double Q3 = 0;

    // error state will show the error that has occured
    private String errorState = "";

    public String getErrorState() {
        return this.errorState;
    }
    public double[] getResults() {
        return results;
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
    public void setResults(double[] results){
        this.results = results;
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

    public void addListener(SimulateViewModelListener listener) {
        listeners.add(listener);
    }

    public void notifyStateChanged() {
        for (SimulateViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }
}
