package interface_adapter.simulate_pulls;

import use_case.simulate_pulls.SimulateOutputBoundary;
import use_case.simulate_pulls.SimulateOutputData;
import view.SimulateView;

public class SimulatePresenter implements SimulateOutputBoundary{

    private final SimulateViewModel simulateViewModel;

    public SimulatePresenter(SimulateViewModel simulateViewModel){
        this.simulateViewModel = simulateViewModel;
    }
    @Override
    public void prepareSuccessView(SimulateOutputData data) {
        //TODO: fill in the prepare success view
        simulateViewModel.getState().setErrorState("");
        //Output Data Variables
        simulateViewModel.getState().setChart(data.getChart());
        simulateViewModel.getState().setMean(data.getMean());
        simulateViewModel.getState().setMedian(data.getQ2());
        simulateViewModel.getState().setQ1(data.getQ1());
        simulateViewModel.getState().setQ3(data.getQ3());
        simulateViewModel.notifyStateChanged();
    }

    @Override
    public void prepareFailView(String errormessage) {
        simulateViewModel.getState().setErrorState(errormessage);
        simulateViewModel.notifyStateChanged();
    }
}
