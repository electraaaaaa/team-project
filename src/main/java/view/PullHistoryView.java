
package view;

import entity.Game;
import interface_adapter.pull_history.PullHistoryController;
import interface_adapter.pull_history.PullHistoryState;
import interface_adapter.pull_history.GamePullHistoryState;
import interface_adapter.pull_history.PullHistoryViewModel;
import interface_adapter.pull_history.PullHistoryViewModelListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The View for the Pull History Use Case.
 */
public class PullHistoryView extends JPanel
        implements ActionListener, PullHistoryViewModelListener {

    private final PullHistoryViewModel pullHistoryViewModel;

    private PullHistoryController pullHistoryController = null;

    private final JTabbedPane tabbedPane = new JTabbedPane();

    private final Map<String, JTextField> urlFields = new HashMap<>();
    private final Map<String, JTextArea> historyAreas = new HashMap<>();

    private final Map<String, Game> games = new HashMap<>();

    private final Map<String, JButton> importButtons = new HashMap<>();

    public PullHistoryView(
            List<Game> games,
            PullHistoryViewModel pullHistoryViewModel) {

        this.pullHistoryViewModel = pullHistoryViewModel;
        pullHistoryViewModel.addListener(this);

        for (Game game : games) {
            createGameTab(game);
        }

        this.setLayout(new BorderLayout());
        this.add(tabbedPane, BorderLayout.CENTER);
    }

    public void setPullHistoryController(PullHistoryController controller) {
        this.pullHistoryController = controller;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {

        for (String gameName : importButtons.keySet()) {

            if (evt.getSource().equals(importButtons.get(gameName))) {

                // Retrieve the URL entered for this game.
                String warpHistoryUrl =
                        urlFields.get(gameName).getText().trim();

                // Retrieve the corresponding Game entity.
                Game game = games.get(gameName);

                // Execute the use case through the Controller.
                pullHistoryController.execute(game, warpHistoryUrl);

                break;
            }
        }
    }



    @Override
    public void stateChanged() {

        // Retrieve the states of all games.
        for (GamePullHistoryState gameState : pullHistoryViewModel.getStates()) {

            String gameName = gameState.getGameName();

            // Find the corresponding history display.
            JTextArea historyArea = historyAreas.get(gameName);

            if (historyArea == null) {
                continue;
            }

            StringBuilder result = new StringBuilder();

            // Display an error if this game's import failed.
            if (!gameState.getErrorMessage().isEmpty()) {
                result.append("Import Failed: ")
                        .append(gameState.getErrorMessage())
                        .append("\n\n");
            }

            // Display the game's pull histories.
            result.append("Pull History for ")
                    .append(gameName)
                    .append("\n\n");

            for (PullHistoryState pullHistory :
                    gameState.getPullHistories()) {

                result.append("Type: ")
                        .append(pullHistory.getType())
                        .append("\n");

                result.append("Total Pulls: ")
                        .append(pullHistory.getTotalPulls())
                        .append("\n");

                result.append("Current Pity: ")
                        .append(pullHistory.getCurrentPity())
                        .append("\n\n");
            }

            // Update the corresponding tab.
            historyArea.setText(result.toString());
        }
    }



    private void createGameTab(Game game) {

        String gameName = game.getName();
        games.put(gameName, game);
        // Create the components.
        final JLabel urlLabel = new JLabel("Warp History URL:");
        final JTextField urlField = new JTextField(25);
        final JButton importButton = new JButton("Import");

        final JTextArea historyArea = new JTextArea(15, 40);
        historyArea.setEditable(false);

        // Store the components for later access.
        urlFields.put(gameName, urlField);
        historyAreas.put(gameName, historyArea);
        importButtons.put(gameName, importButton);

        // Create the URL input panel.
        final JPanel inputPanel = new JPanel();
        inputPanel.add(urlLabel);
        inputPanel.add(urlField);
        inputPanel.add(importButton);

        // Add scrolling to the history display.
        final JScrollPane scrollPane = new JScrollPane(historyArea);

        // Create the game's tab.
        final JPanel gamePanel = new JPanel(new BorderLayout());

        gamePanel.add(inputPanel, BorderLayout.NORTH);
        gamePanel.add(scrollPane, BorderLayout.CENTER);

        // Register this View as the button listener.
        importButton.addActionListener(this);

        // Add the tab.
        tabbedPane.addTab(gameName, gamePanel);
    }

}
