package interface_adapter.cost_conversion;

import java.util.ArrayList;
import java.util.List;

/**
 * The view model for the conversion input screen.
 */
public class ConversionViewModel {

    public static final String VIEW_NAME = "cost conversion";

    private final List<ConversionViewModelListener> listeners =
            new ArrayList<>();

    private ConversionState state = new ConversionState();

    public ConversionState getState() {
        return state;
    }

    public void setState(ConversionState state) {
        this.state = state;
    }

    public void addListener(ConversionViewModelListener listener) {
        listeners.add(listener);
    }

    public void notifyStateChanged() {
        for (ConversionViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }
}