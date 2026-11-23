package interface_adapter.simulate_pulls;

import view.SimulateView;

import java.util.ArrayList;
import java.util.List;

public class SimulateViewModel {
    private final List<SimulateViewModelListener> listeners = new ArrayList<>();

    public static final String VIEW_NAME = "Simulate Pulls";

    private SimulateState State = new SimulateState();

    public SimulateState getState(){ return State;}

    public void addListener(SimulateViewModelListener listener) {
        listeners.add(listener);
    }

    public void notifyStateChanged() {
        for (SimulateViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }
}
