package view;

import interface_adapter.cost_conversion.ConversionController;
import interface_adapter.cost_conversion.ConversionState;
import interface_adapter.cost_conversion.ConversionViewModel;
import interface_adapter.cost_conversion.ConversionViewModelListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ConversionView extends JPanel
        implements ConversionViewModelListener {

    private static final Color BACKGROUND = new Color(243, 245, 249);
    private static final Color CARD_BACKGROUND = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(30, 41, 59);
    private static final Color SECONDARY_TEXT = new Color(100, 116, 139);
    private static final Color ACCENT = new Color(37, 99, 235);
    private static final Color ERROR_COLOR = new Color(180, 35, 35);
    private static final Color WARNING_BACKGROUND = new Color(255, 247, 225);
    private static final Color WARNING_TEXT = new Color(130, 80, 0);

    private final ConversionViewModel viewModel;
    private ConversionController controller;

    private final CardLayout cardLayout = new CardLayout();

    private final JComboBox<String> gameDropdown = new JComboBox<>();
    private final JComboBox<String> currencyDropdown = new JComboBox<>();
    private final JTextField pullAmountField = new JTextField(10);

    private final JLabel errorLabel = new JLabel(" ");
    private final JLabel costLabel = new JLabel(" ");
    private final JLabel summaryLabel = new JLabel(" ");
    private final JLabel warningLabel = new JLabel(" ");

    private final JButton calculateButton = new JButton("Calculate Cost");
    private final JButton cancelButton = new JButton("Cancel");
    private final JButton recalculateButton = new JButton("Recalculate");
    private final JButton exitButton = new JButton("Back to Probability");

    public ConversionView(ConversionViewModel viewModel, List<String> gameNames, List<String> currencies) {
        this.viewModel = viewModel;
        setLayout(cardLayout);

        for (String gameName : gameNames) {
            gameDropdown.addItem(gameName);
        }

        for (String currency : currencies) {
            currencyDropdown.addItem(currency);
        }

        if (currencies.contains("CAD")) {
            currencyDropdown.setSelectedItem("CAD");
        }

        styleButton(calculateButton, true);
        styleButton(cancelButton, false);
        styleButton(recalculateButton, true);
        styleButton(exitButton, false);

        errorLabel.setForeground(ERROR_COLOR);
        errorLabel.setFont(errorLabel.getFont().deriveFont(13f));

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
        JPanel card = createCard();

        JLabel title = new JLabel("Pull Cost Calculator");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        title.setForeground(TEXT_COLOR);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Estimate the cost of your planned pulls.");
        subtitle.setFont(subtitle.getFont().deriveFont(14f));
        subtitle.setForeground(SECONDARY_TEXT);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(title);
        card.add(Box.createVerticalStrut(8));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(25));

        card.add(createRow("Game", gameDropdown));
        card.add(Box.createVerticalStrut(16));
        card.add(createRow("Number of pulls", pullAmountField));
        card.add(Box.createVerticalStrut(16));
        card.add(createRow("Display currency", currencyDropdown));
        card.add(Box.createVerticalStrut(12));

        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(errorLabel);
        card.add(Box.createVerticalStrut(12));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttons.setOpaque(false);
        buttons.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttons.add(calculateButton);
        buttons.add(Box.createHorizontalStrut(12));
        buttons.add(cancelButton);
        card.add(buttons);

        return createScreen(card);
    }

    private JPanel createResultPanel() {
        JPanel card = createCard();

        JLabel title = new JLabel("Estimated Pull Cost");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        title.setForeground(TEXT_COLOR);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        costLabel.setFont(costLabel.getFont().deriveFont(Font.BOLD, 38f));
        costLabel.setForeground(ACCENT);
        costLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        summaryLabel.setFont(summaryLabel.getFont().deriveFont(14f));
        summaryLabel.setForeground(SECONDARY_TEXT);
        summaryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        warningLabel.setOpaque(true);
        warningLabel.setBackground(WARNING_BACKGROUND);
        warningLabel.setForeground(WARNING_TEXT);
        warningLabel.setFont(warningLabel.getFont().deriveFont(13f));
        warningLabel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        warningLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        warningLabel.setVisible(false);

        JLabel explanation = new JLabel(
                "<html>Based on the best-value package's unit price.<br>"
                        + "Actual purchase total may differ.</html>");
        explanation.setFont(explanation.getFont().deriveFont(13f));
        explanation.setForeground(SECONDARY_TEXT);
        explanation.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(title);
        card.add(Box.createVerticalStrut(24));
        card.add(costLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(summaryLabel);
        card.add(Box.createVerticalStrut(20));
        card.add(warningLabel);
        card.add(Box.createVerticalStrut(16));
        card.add(explanation);
        card.add(Box.createVerticalStrut(25));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttons.setOpaque(false);
        buttons.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttons.add(recalculateButton);
        buttons.add(Box.createHorizontalStrut(12));
        buttons.add(exitButton);
        card.add(buttons);

        return createScreen(card);
    }

    private JPanel createCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 233)),
                BorderFactory.createEmptyBorder(28, 28, 28, 28)));

        return card;
    }

    private JPanel createScreen(JPanel card) {
        JPanel screen = new JPanel(new BorderLayout());
        screen.setBackground(BACKGROUND);
        screen.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        container.setOpaque(false);
        container.add(card);

        screen.add(container, BorderLayout.NORTH);
        return screen;
    }

    private JPanel createRow(String labelText, JComponent component) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 14f));
        label.setForeground(TEXT_COLOR);
        label.setLabelFor(component);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        component.setFont(component.getFont().deriveFont(14f));
        component.setPreferredSize(new Dimension(380, 36));
        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        component.setAlignmentX(Component.LEFT_ALIGNMENT);

        row.add(label);
        row.add(Box.createVerticalStrut(7));
        row.add(component);

        return row;
    }

    private void styleButton(JButton button, boolean primary) {
        button.setFont(button.getFont().deriveFont(Font.BOLD, 13f));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);

        if (primary) {
            button.setBackground(ACCENT);
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder(11, 18, 11, 18));
        }
        else {
            button.setBackground(CARD_BACKGROUND);
            button.setForeground(TEXT_COLOR);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(203, 213, 225)),
                    BorderFactory.createEmptyBorder(10, 17, 10, 17)));
        }
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

        calculateButton.setText(busy ? "Calculating..." : "Calculate Cost");
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

        String error = state.getConversionError();
        errorLabel.setText(error == null || error.isEmpty() ? " " : error);

        costLabel.setText(state.getCostText());

        // Disable HTML interpretation for text containing game names.
        summaryLabel.putClientProperty("html.disable", true);
        summaryLabel.setText(
                state.getNumberOfPulls() + " pulls · " + state.getGameName());

        String warning = state.getWarningMessage();
        boolean hasWarning = warning != null && !warning.isEmpty();

        warningLabel.setText(hasWarning ? warning : "");
        warningLabel.setVisible(hasWarning);

        if (state.isShowingResult()) {
            cardLayout.show(this, "result");
        }
        else {
            cardLayout.show(this, "input");
        }

        revalidate();
        repaint();
    }
}