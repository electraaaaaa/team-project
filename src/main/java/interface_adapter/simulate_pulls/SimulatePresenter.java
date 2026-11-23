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
        simulateViewModel.setErrorState("");
        //Output Data Variables
        simulateViewModel.setResults(data.getResults());
        simulateViewModel.setMean(data.getMean());
        simulateViewModel.setMedian(data.getQ2());
        simulateViewModel.setQ1(data.getQ1());
        simulateViewModel.setQ3(data.getQ3());
        simulateViewModel.notifyStateChanged();
    }

    @Override
    public void prepareFailView(String errormessage) {
        simulateViewModel.setErrorState(errormessage);
        simulateViewModel.notifyStateChanged();
    }
}
