package view;

import interface_adapter.cost_conversion.ConversionController;
import interface_adapter.cost_conversion.ConversionState;
import interface_adapter.cost_conversion.ConversionViewModel;
import interface_adapter.cost_conversion.ConversionViewModelListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ConversionView extends JPanel
        implements ConversionViewModelListener {

    private final ConversionViewModel viewModel;
    private ConversionController controller;

    private final CardLayout cardLayout = new CardLayout();

    private final JComboBox<String> gameDropdown = new JComboBox<>();
    private final JComboBox<String> currencyDropdown = new JComboBox<>();
    private final JTextField pullAmountField = new JTextField(10);

    private final JLabel errorLabel = new JLabel(" ");
    private final JLabel costLabel = new JLabel(" ");
    private final JLabel warningLabel = new JLabel(" ");

    private final JButton calculateButton = new JButton("Calculate");
    private final JButton cancelButton = new JButton("Cancel");
    private final JButton recalculateButton = new JButton("Recalculate");
    private final JButton exitButton = new JButton("Exit");

    public ConversionView(ConversionViewModel viewModel, List<String> gameNames, List<String> currencies) {
        this.viewModel = viewModel;
        setLayout(cardLayout);

        for (String gameName : gameNames) {
            gameDropdown.addItem(gameName);
        }

        for (String currency : currencies) {
            currencyDropdown.addItem(currency);
        }

        add(createInputPanel(), "input");
        add(createResultPanel(), "result");

        viewModel.addListener(this);

        calculateButton.addActionListener(event -> calculate());

        recalculateButton.addActionListener(event -> {
            ConversionState state = viewModel.getState();
            state.setShowingResult(false);
            state.setConversionError("");
            viewModel.notifyStateChanged();
        });

        stateChanged();
    }

    public void setConversionController(ConversionController controller) {
        this.controller = controller;
    }

    public void setExitAction(Runnable exitAction) {
        cancelButton.addActionListener(event -> exitAction.run());
        exitButton.addActionListener(event -> exitAction.run());
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Calculate Pull Cost"));
        panel.add(createRow("Game:", gameDropdown));
        panel.add(createRow("Number of pulls:", pullAmountField));
        panel.add(createRow("Currency:", currencyDropdown));
        panel.add(errorLabel);

        JPanel buttons = new JPanel();
        buttons.add(calculateButton);
        buttons.add(cancelButton);
        panel.add(buttons);

        return panel;
    }

    private JPanel createResultPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Calculated Cost"));
        panel.add(costLabel);
        panel.add(warningLabel);

        JPanel buttons = new JPanel();
        buttons.add(recalculateButton);
        buttons.add(exitButton);
        panel.add(buttons);

        return panel;
    }

    private JPanel createRow(String labelText, JComponent component) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(labelText);
        label.setLabelFor(component);

        row.add(label);
        row.add(component);

        return row;
    }

    private void calculate() {
        ConversionState state = viewModel.getState();

        state.setGameName((String) gameDropdown.getSelectedItem());
        state.setTargetCurrency((String) currencyDropdown.getSelectedItem());
        state.setNumberOfPulls(pullAmountField.getText());

        final int numberOfPulls;

        try {
            numberOfPulls = Integer.parseInt(state.getNumberOfPulls().trim());
        }
        catch (NumberFormatException exception) {
            showInputError("Enter a whole number of pulls within the supported range.");
            return;
        }

        final String gameName = state.getGameName();
        final String targetCurrency = state.getTargetCurrency();

        state.setConversionError("");
        viewModel.notifyStateChanged();
        setBusy(true);

        // Keep network requests off Swing's UI thread.
        runConversion(gameName, numberOfPulls, targetCurrency);
    }

    private void showInputError(String message) {
        ConversionState state = viewModel.getState();
        state.setConversionError(message);
        state.setShowingResult(false);
        viewModel.notifyStateChanged();
    }

    private void setBusy(boolean busy) {
        gameDropdown.setEnabled(!busy);
        currencyDropdown.setEnabled(!busy);
        pullAmountField.setEnabled(!busy);

        calculateButton.setEnabled(!busy);
        cancelButton.setEnabled(!busy);
        recalculateButton.setEnabled(!busy);
        exitButton.setEnabled(!busy);

        calculateButton.setText(busy ? "Calculating..." : "Calculate");
    }

    private void runConversion(String gameName, int numberOfPulls, String targetCurrency) {

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                controller.execute(gameName, numberOfPulls, targetCurrency);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                }
                catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    showInputError("Calculation interrupted.");
                }
                catch (ExecutionException exception) {
                    showInputError("The calculation could not be completed.");
                }
                finally {
                    setBusy(false);
                }
            }
        }.execute();
    }

    @Override
    public void stateChanged() {
        // Swing components must be updated on Swing's UI thread.
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(this::stateChanged);
            return;
        }

        ConversionState state = viewModel.getState();

        errorLabel.setText(state.getConversionError());
        costLabel.setText(state.getCostText());
        warningLabel.setText(state.getWarningMessage());

        if (state.isShowingResult()) {
            cardLayout.show(this, "result");
        }
        else {
            cardLayout.show(this, "input");
        }
    }
}