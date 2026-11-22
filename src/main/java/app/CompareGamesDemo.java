package app;

import entity.PitySystem;
import interface_adapter.compare_games.CompareGamesController;
import interface_adapter.compare_games.CompareGamesPresenter;
import interface_adapter.compare_games.CompareGamesViewModel;
import use_case.compare_games.CompareGamesInteractor;
import view.CompareGamesView;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Temporary demo for testing the Compare Across Games use case. Can be deleted later.
 */
public class CompareGamesDemo {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Create sample pity systems for testing.
            Map<String, PitySystem> pitySystems = new HashMap<>();

            pitySystems.put("Genshin Impact",
                    new PitySystem(0.006f, 74, 0.06f, 90));

            pitySystems.put("Honkai: Star Rail",
                    new PitySystem(0.006f, 74, 0.06f, 90));

            // Create the ViewModel and Presenter.
            CompareGamesViewModel viewModel =
                    new CompareGamesViewModel();

            CompareGamesPresenter presenter =
                    new CompareGamesPresenter(viewModel);

            // Create the Interactor and Controller.
            CompareGamesInteractor interactor =
                    new CompareGamesInteractor(pitySystems, presenter);

            CompareGamesController controller =
                    new CompareGamesController(interactor);

            // Create the comparison View.
            CompareGamesView view =
                    new CompareGamesView(controller, viewModel);

            // Display the GUI.
            JFrame frame = new JFrame("Compare Across Games");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(view);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
