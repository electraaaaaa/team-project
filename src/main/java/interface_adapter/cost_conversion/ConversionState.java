package interface_adapter.cost_conversion;

/**
 * Stores the input and result information for the conversion screen.
 */
public class ConversionState {

    private String gameName = "";
    private String bannerName = "";
    private String numberOfPulls = "";
    private String targetCurrency = "";

    private String conversionError = "";
    private String costText = "";
    private String warningMessage = "";
    private boolean showingResult = false;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getNumberOfPulls() {
        return numberOfPulls;
    }

    public void setNumberOfPulls(String numberOfPulls) {
        this.numberOfPulls = numberOfPulls;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public String getConversionError() {
        return conversionError;
    }

    public void setConversionError(String conversionError) {
        this.conversionError = conversionError;
    }

    public String getCostText() {
        return costText;
    }

    public void setCostText(String costText) {
        this.costText = costText;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    public boolean isShowingResult() {
        return showingResult;
    }

    public void setShowingResult(boolean showingResult) {
        this.showingResult = showingResult;
    }
}