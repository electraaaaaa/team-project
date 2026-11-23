package view;
import Interface_adapter.simulate_pulls.SimulateViewModel;
import Interface_adapter.simulate_pulls.SimulateController;

import Interface_adapter.simulate_pulls.SimulateViewModelListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

public class SimulateView extends JPanel implements ActionListener,  SimulateViewModelListener{


    private final SimulateViewModel simulateViewModel;
    private SimulateController simulateController = null;



    private static final Color BACKGROUND =
            new Color(255, 255, 255);

    private static final Color BORDER_COLOR =
            Color.BLACK;

    // Input fields
    private final JTextField simulationsField;
    private final JTextField pullsField;
    private final JTextField hardPityField;
    private final JTextField softPityField;
    private final JTextField baseRateField;
    private final JTextField softPityIncreaseField;

    private final JComboBox<String> guaranteedLimitedBox;
    private final JComboBox<String> gameBox;

    // Button
    private final JButton runSimulationButton;

    // Histogram
    private final JPanel histogramPanel;

    // Output statistics
    private final JLabel meanLabel;
    private final JLabel medianLabel;
    private final JLabel firstQuartileLabel;
    private final JLabel thirdQuartileLabel;

    // Error message
    private final JLabel errorMessageLabel;

    public SimulateView(SimulateViewModel simulateViewModel) {

        this.simulateViewModel = simulateViewModel;
        simulateViewModel.addListener(this);

        setLayout(new GridBagLayout());
        setBackground(BACKGROUND);

        // Initialize input fields
        simulationsField = new JTextField();
        pullsField = new JTextField();
        hardPityField = new JTextField();
        softPityField = new JTextField();
        baseRateField = new JTextField();
        softPityIncreaseField = new JTextField();

        guaranteedLimitedBox = new JComboBox<>(
                new String[]{"True", "False"}
        );

        // Supported Games
        gameBox = new JComboBox<>(
                new String[]{"Genshin Impact", "Honkai: Star Rail"}
        );

        runSimulationButton = new JButton("Run Simulation");

        // Initialize histogram container
        histogramPanel = new JPanel(new BorderLayout());
        histogramPanel.setBackground(BACKGROUND);
        histogramPanel.setBorder(
                BorderFactory.createLineBorder(BORDER_COLOR)
        );

        // Initialize statistics
        meanLabel = new JLabel("Mean: _____");
        medianLabel = new JLabel("Median: _____");
        firstQuartileLabel = new JLabel("1st Quartile: _____");
        thirdQuartileLabel = new JLabel("3rd Quartile: _____");

        // Initialize error message
        errorMessageLabel = new JLabel(" ");
        errorMessageLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        // Build sections
        JPanel inputPanel = createInputPanel();
        JPanel topPanel = createTopPanel(inputPanel);
        JPanel outputPanel = createOutputPanel();

        // Add top section, Allows for resizing based on the weights
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.68;
        gbc.fill = GridBagConstraints.BOTH;

        add(topPanel, gbc);

        // Add bottom section
        gbc.gridy = 1;
        gbc.weighty = 0.32;

        add(outputPanel, gbc);

        runSimulationButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(runSimulationButton)) {
                            simulateController.execute(
                                    simulationsField.getText(),
                                    pullsField.getText(),
                                    hardPityField.getText(),
                                    softPityField.getText(),
                                    baseRateField.getText(),
                                    softPityIncreaseField.getText(),
                                    "True"==guaranteedLimitedBox.getSelectedItem(),
                                    (String)gameBox.getSelectedItem()
                            );
                        }
                    }
                }
        );

    }

    // create/draw
    private JPanel createInputPanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND);
        panel.setBorder(
                BorderFactory.createLineBorder(BORDER_COLOR)
        );

        String[] labels = {
                "# of Simulations",
                "# of Pulls per Sim",
                "Hard Pity",
                "Soft Pity",
                "Base Rate",
                "Soft Pity Increase",
                "Guaranteed Limited",
                "Game"
        };

        JComponent[] fields = {
                simulationsField,
                pullsField,
                hardPityField,
                softPityField,
                baseRateField,
                softPityIncreaseField,
                guaranteedLimitedBox,
                gameBox
        };

        for (int i = 0; i < labels.length; i++) {

            GridBagConstraints gbc = new GridBagConstraints();

            gbc.gridy = i;
            gbc.insets = new Insets(3, 5, 3, 5);
            gbc.fill = GridBagConstraints.BOTH;

            // Label column
            gbc.gridx = 0;
            gbc.weightx = 0.65;

            JLabel label = new JLabel(
                    labels[i],
                    SwingConstants.CENTER
            );

            label.setBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR)
            );

            label.setPreferredSize(new Dimension(170, 36));

            panel.add(label, gbc);

            // Input column
            gbc.gridx = 1;
            gbc.weightx = 0.35;

            fields[i].setPreferredSize(
                    new Dimension(95, 36)
            );

            fields[i].setBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR)
            );

            panel.add(fields[i], gbc);
        }

        // Run Simulation button
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 10, 5);

        gbc.anchor = GridBagConstraints.CENTER;

        runSimulationButton.setPreferredSize(
                new Dimension(180, 45)
        );

        panel.add(runSimulationButton, gbc);

        return panel;
    }

    private JPanel createTopPanel(JPanel inputPanel) {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND);

        GridBagConstraints gbc = new GridBagConstraints();

        // Input area: approximately 30% width
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.30;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        panel.add(inputPanel, gbc);

        // Histogram: approximately 70% width
        // Add to the right of the Input Area
        gbc.gridx = 1;
        gbc.weightx = 0.70;

        panel.add(histogramPanel, gbc);

        return panel;
    }

    private JPanel createOutputPanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND);

        panel.setBorder(
                BorderFactory.createLineBorder(BORDER_COLOR)
        );

        // Statistics row
        JPanel statisticsPanel = new JPanel(
                new GridLayout(1, 4, 25, 0)
        );

        statisticsPanel.setBackground(BACKGROUND);

        statisticsPanel.add(createStatisticBox(meanLabel));
        statisticsPanel.add(createStatisticBox(medianLabel));
        statisticsPanel.add(createStatisticBox(firstQuartileLabel));
        statisticsPanel.add(createStatisticBox(thirdQuartileLabel));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(35, 35, 20, 35);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel.add(statisticsPanel, gbc);

        // Error message area
        JPanel errorPanel = new JPanel(new BorderLayout());

        errorPanel.setBackground(BACKGROUND);
        errorPanel.setBorder(
                BorderFactory.createLineBorder(BORDER_COLOR)
        );

        errorPanel.add(errorMessageLabel, BorderLayout.CENTER);

        errorPanel.setPreferredSize(new Dimension(310, 50));

        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.insets = new Insets(15, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        panel.add(errorPanel, gbc);

        return panel;
    }

    private JPanel createStatisticBox(JLabel label) {

        JPanel box = new JPanel(new GridBagLayout());

        box.setBackground(BACKGROUND);
        box.setBorder(
                BorderFactory.createLineBorder(BORDER_COLOR)
        );

        box.add(label);

        return box;
    }

    // ==========================================
    // Controller communication
    // ==========================================

    //TODO: Add all input textfield and box listeners

    // ==========================================
    // Input getters
    // ==========================================

    public String getNumberOfSimulations() {
        return simulationsField.getText();
    }

    public String getNumberOfPulls() {
        return pullsField.getText();
    }

    public String getHardPity() {
        return hardPityField.getText();
    }

    public String getSoftPity() {
        return softPityField.getText();
    }

    public String getBaseRate() {
        return baseRateField.getText();
    }

    public String getSoftPityIncrease() {
        return softPityIncreaseField.getText();
    }

    public boolean getGuaranteedLimited() {
        return guaranteedLimitedBox.getSelectedItem()
                .equals("True");
    }

    public String getSelectedGame() {
        return (String) gameBox.getSelectedItem();
    }

    // ==========================================
    // Output methods (called by presenter)
    // ==========================================

    public void displayStatistics(
            double mean,
            double median,
            double firstQuartile,
            double thirdQuartile
    ) {
        meanLabel.setText(String.format("Mean: %.2f", mean));
        medianLabel.setText(String.format("Median: %.2f", median));

        firstQuartileLabel.setText(
                String.format("1st Quartile: %.2f", firstQuartile)
        );

        thirdQuartileLabel.setText(
                String.format("3rd Quartile: %.2f", thirdQuartile)
        );
    }

    public void displayError(String message) {
        errorMessageLabel.setForeground(Color.RED);
        errorMessageLabel.setText(message);
    }

    public void clearError() {
        errorMessageLabel.setForeground(Color.BLACK);
        errorMessageLabel.setText(" ");
    }

    public void displayChart(JFreeChart chart) {

        histogramPanel.removeAll();

        ChartPanel chartPanel = new ChartPanel(chart);

        histogramPanel.add(chartPanel, BorderLayout.CENTER);

        histogramPanel.revalidate();
        histogramPanel.repaint();
    }

    @Override
    public void stateChanged() {
        if(!simulateViewModel.getErrorState().isBlank()){
            displayError(simulateViewModel.getErrorState());
        }else{
            clearError();
            displayStatistics(simulateViewModel.getMean(),simulateViewModel.getMedian(),
                    simulateViewModel.getQ1(),simulateViewModel.getQ3());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO: I have no idea what the action performed could do.
    }
    public void setSimulateController(SimulateController controller) {
        this.simulateController = controller;
    }
}
