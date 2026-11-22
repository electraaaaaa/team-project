package interface_adapter.calculate_probability;

public class ProbabilityState {
    private String probabilityText = "";
    private String guaranteeMessage = "";
    private String errorMessage = "";

    public String getProbabilityText() {
        return probabilityText;
    }

    public void setProbabilityText(String probabilityText) {
        this.probabilityText = probabilityText;
    }

    public String getGuaranteeMessage() {
        return guaranteeMessage;
    }

    public void setGuaranteeMessage(String guaranteeMessage) {
        this.guaranteeMessage = guaranteeMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
