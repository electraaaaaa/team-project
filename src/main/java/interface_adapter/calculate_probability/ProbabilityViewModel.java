package interface_adapter.calculate_probability;
import java.util.ArrayList;
import java.util.List;

public class ProbabilityViewModel {
    public static final String VIEW_NAME = "calculate probability";

    private ProbabilityState state = new ProbabilityState();
    private final List<ProbabilityViewModelListener> listeners = new ArrayList<>();

    public ProbabilityState getState() {
        return state;
    }

    public void setState(ProbabilityState state) {
        this.state = state;
    }

    public void addListener(ProbabilityViewModelListener listener) {
        listeners.add(listener);
    }

    /**
     * Tells every listener that the state was updated.
     */
    public void fireStateChanged() {
        for (ProbabilityViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }
}
