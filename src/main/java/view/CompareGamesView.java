
package view;

import interface_adapter.compare_games.CompareGamesController;
import interface_adapter.compare_games.CompareGamesViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Swing view for comparing pity probabilities across games.
 */
public class CompareGamesView extends JPanel
        implements PropertyChangeListener {

    private final CompareGamesController controller;
    private final CompareGamesViewModel viewModel;

    private final String[] games = {
            "Genshin Impact",
            "Honkai: Star Rail"
    };

    private final JComboBox<String> game1Box =
            new JComboBox<>(games);

    private final JComboBox<String> game2Box =
            new JComboBox<>(games);

    private final JTextField pullsField = new JTextField("50");
    private final JTextField pity1Field = new JTextField("0");
    private final JTextField pity2Field = new JTextField("0");

    private final JLabel result1 = new JLabel("Game 1: --");
    private final JLabel result2 = new JLabel("Game 2: --");
    private final JLabel errorLabel = new JLabel(" ");

    public CompareGamesView(CompareGamesController controller,
                            CompareGamesViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;

        viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(10, 10));

        // Select different games by default.
        game2Box.setSelectedIndex(1);

        // Input fields.
        JPanel inputPanel = new JPanel(
                new GridLayout(5, 2, 10, 10));

        inputPanel.add(new JLabel("Game 1:"));
        inputPanel.add(game1Box);

        inputPanel.add(new JLabel("Game 2:"));
        inputPanel.add(game2Box);

        inputPanel.add(new JLabel("Number of future pulls:"));
        inputPanel.add(pullsField);

        inputPanel.add(new JLabel("Game 1 current pity:"));
        inputPanel.add(pity1Field);

        inputPanel.add(new JLabel("Game 2 current pity:"));
        inputPanel.add(pity2Field);

        //Compare button
        JButton compareButton = new JButton("Compare");
        compareButton.addActionListener(event -> compareGames());

        //Results panel
        JPanel resultsPanel = new JPanel(
                new GridLayout(2, 1, 10, 10));

        resultsPanel.add(result1);
        resultsPanel.add(result2);

        JPanel bottomPanel = new JPanel(
                new BorderLayout(10, 10));

        bottomPanel.add(compareButton, BorderLayout.NORTH);
        bottomPanel.add(resultsPanel, BorderLayout.CENTER);
        bottomPanel.add(errorLabel, BorderLayout.SOUTH);

        add(inputPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
    }

    /**
     * Called when the player clicks Compare.
     */
    private void compareGames() {
        try {
            String game1 = (String) game1Box.getSelectedItem();
            String game2 = (String) game2Box.getSelectedItem();

            int pulls = Integer.parseInt(
                    pullsField.getText().trim());

            int pity1 = Integer.parseInt(
                    pity1Field.getText().trim());

            int pity2 = Integer.parseInt(
                    pity2Field.getText().trim());

            controller.execute(game1, game2, pulls, pity1, pity2);

            errorLabel.setText(" ");

        } catch (NumberFormatException exception) {
            errorLabel.setText("Please enter valid whole numbers.");

        } catch (IllegalArgumentException exception) {
            errorLabel.setText(exception.getMessage());
        }
    }

    /**
     * Updates the GUI when the ViewModel changes.
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if ("results".equals(event.getPropertyName())) {

            result1.setText(viewModel.getGame1() + ": "
                    + viewModel.getProbability1());

            result2.setText(viewModel.getGame2() + ": "
                    + viewModel.getProbability2());
        }
    }
}
