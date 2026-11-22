package Interface_adapter.simulate_pulls;

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
    }

    @Override
    public void prepareFailView(String errormesage) {
        //TODO: fill in the failure success view
    }
}
