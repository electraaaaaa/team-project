package interface_adapter.compare_games;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * View model for the Compare Across Games use case.
 */
public class CompareGamesViewModel {

    private String game1;
    private String game2;
    private String probability1;
    private String probability2;

    private final PropertyChangeSupport support =
            new PropertyChangeSupport(this);

    public void setResults(String game1, String game2,
                           String probability1, String probability2) {
        this.game1 = game1;
        this.game2 = game2;
        this.probability1 = probability1;
        this.probability2 = probability2;

        support.firePropertyChange("results", null, this);
    }

    public String getGame1() {
        return game1;
    }

    public String getGame2() {
        return game2;
    }

    public String getProbability1() {
        return probability1;
    }

    public String getProbability2() {
        return probability2;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
