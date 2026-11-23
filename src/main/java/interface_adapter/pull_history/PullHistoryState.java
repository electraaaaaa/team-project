package interface_adapter.pull_history;

/**
 * State for the pull history of one banner type.
 */
public class PullHistoryState {

    private String type = "";
    private String totalPulls = "";
    private String currentPity = "";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTotalPulls() {
        return totalPulls;
    }

    public void setTotalPulls(String totalPulls) {
        this.totalPulls = totalPulls;
    }

    public String getCurrentPity() {
        return currentPity;
    }

    public void setCurrentPity(String currentPity) {
        this.currentPity = currentPity;
    }
}
