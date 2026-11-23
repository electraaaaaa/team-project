package view;

import interface_adapter.calculate_probability.ProbabilityController;
import interface_adapter.calculate_probability.ProbabilityState;
import interface_adapter.calculate_probability.ProbabilityViewModel;
import interface_adapter.calculate_probability.ProbabilityViewModelListener;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The View for when the player calculates their pull probability.
 */
public class ProbabilityView extends JPanel implements ProbabilityViewModelListener {

    private final ProbabilityViewModel probabilityViewModel;
    private ProbabilityController probabilityController;

    private final JComboBox<String> gameInputField;
    private final JComboBox<String> bannerTypeInputField;
    private final JTextField pityInputField = new JTextField("0", 10);
    private final JTextField pullsInputField = new JTextField("10", 10);
    private final JButton calculateButton = new JButton("Calculate");

    private final JLabel probabilityLabel = new JLabel(" ");
    private final JLabel guaranteeLabel = new JLabel(" ");
    private final JLabel errorLabel = new JLabel(" ");

    public ProbabilityView(ProbabilityViewModel probabilityViewModel,
                           List<String> gameNames, List<String> bannerTypes) {
        this.probabilityViewModel = probabilityViewModel;
        this.probabilityViewModel.addListener(this);

        gameInputField = new JComboBox<>(gameNames.toArray(new String[0]));
        bannerTypeInputField = new JComboBox<>(bannerTypes.toArray(new String[0]));

        final JLabel title = new JLabel("Calculate Pull Probability");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        form.add(new JLabel("Game"));
        form.add(gameInputField);
        form.add(new JLabel("Banner type"));
        form.add(bannerTypeInputField);
        form.add(new JLabel("Current pity (pulls since last 5-star)"));
        form.add(pityInputField);
        form.add(new JLabel("Number of planned pulls"));
        form.add(pullsInputField);

        calculateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        calculateButton.addActionListener(evt -> probabilityController.execute(
                (String) gameInputField.getSelectedItem(),
                (String) bannerTypeInputField.getSelectedItem(),
                pityInputField.getText(),
                pullsInputField.getText()));

        probabilityLabel.setFont(probabilityLabel.getFont().deriveFont(Font.BOLD, 28f));
        probabilityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        guaranteeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        this.add(title);
        this.add(Box.createVerticalStrut(12));
        this.add(form);
        this.add(Box.createVerticalStrut(12));
        this.add(calculateButton);
        this.add(Box.createVerticalStrut(12));
        this.add(probabilityLabel);
        this.add(guaranteeLabel);
        this.add(errorLabel);
    }

    @Override
    public void stateChanged() {
        final ProbabilityState state = probabilityViewModel.getState();
        probabilityLabel.setText(orBlank(state.getProbabilityText()));
        guaranteeLabel.setText(orBlank(state.getGuaranteeMessage()));
        errorLabel.setText(orBlank(state.getErrorMessage()));
    }

    public String getViewName() {
        return ProbabilityViewModel.VIEW_NAME;
    }

    public void setProbabilityController(ProbabilityController probabilityController) {
        this.probabilityController = probabilityController;
    }

    // empty label collapses, so use a space instead
    private static String orBlank(String text) {
        return text.isEmpty() ? " " : text;
    }
}
