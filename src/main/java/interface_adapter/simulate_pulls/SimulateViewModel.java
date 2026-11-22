package Interface_adapter.simulate_pulls;

import java.util.ArrayList;
import java.util.List;

public class SimulateViewModel {
    private final List<SimulateViewModelListener> listeners = new ArrayList<>();


//    private State state = new SimulateState();

//    public SimulateState getState() {
//        return this.state;
//    }
//
//    public void setState(SimulateState state) {
//        this.state = state;
//    }

    public void addListener(SimulateViewModelListener listener) {
        listeners.add(listener);
    }

    public void notifyStateChanged() {
        for (SimulateViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }
}
