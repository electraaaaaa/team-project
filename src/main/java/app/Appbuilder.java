package app;

import data_access.InMemoryGameDataAccessObject;
import data_access.StarRailPullHistoryDataAccessObject;
import entity.CollectableFactory;
import entity.GamePullHistoriesFactory;
import interface_adapter.calculate_probability.ProbabilityController;
import interface_adapter.calculate_probability.ProbabilityPresenter;
import interface_adapter.calculate_probability.ProbabilityViewModel;
import interface_adapter.pull_history.PullHistoryController;
import interface_adapter.pull_history.PullHistoryPresenter;
import interface_adapter.pull_history.PullHistoryViewModel;
import use_case.calculate_probability.ProbabilityInputBoundary;
import use_case.calculate_probability.ProbabilityInteractor;
import use_case.calculate_probability.ProbabilityOutputBoundary;
import use_case.pull_history.PullHistoryInputBoundary;
import use_case.pull_history.PullHistoryInteractor;
import use_case.pull_history.PullHistoryOutputBoundary;
import view.ProbabilityView;
import view.PullHistoryView;

import javax.swing.*;
import java.awt.*;
// temporary
import entity.Game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Builds the application: each use case adds its view and wires its Controller,
 * Interactor and Presenter here.
 */
public class Appbuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    CollectableFactory collectableFactory = new CollectableFactory();
    GamePullHistoriesFactory gamePullHistoriesFactory = new GamePullHistoriesFactory();

    private final InMemoryGameDataAccessObject gameDataAccessObject = new InMemoryGameDataAccessObject();
    private final StarRailPullHistoryDataAccessObject starRailPullHistoryDataAccessObject =
            new StarRailPullHistoryDataAccessObject(collectableFactory, gamePullHistoriesFactory);

    private ProbabilityViewModel probabilityViewModel;
    private ProbabilityView probabilityView;
    private PullHistoryViewModel pullHistoryViewModel;
    private PullHistoryView  pullHistoryView;
    // temp
    private final List<Game> games = createGames();

    public Appbuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public Appbuilder addProbabilityView() {
        probabilityViewModel = new ProbabilityViewModel();
        probabilityView = new ProbabilityView(probabilityViewModel,
                gameDataAccessObject.getGameNames(), gameDataAccessObject.getBannerTypes());
        cardPanel.add(probabilityView, probabilityView.getViewName());
        return this;
    }

    public Appbuilder addProbabilityUseCase() {
        final ProbabilityOutputBoundary probabilityOutputBoundary = new ProbabilityPresenter(probabilityViewModel);
        final ProbabilityInputBoundary probabilityInteractor =
                new ProbabilityInteractor(gameDataAccessObject, probabilityOutputBoundary);
        probabilityView.setProbabilityController(new ProbabilityController(probabilityInteractor));
        return this;
    }

    public Appbuilder addPullHistoryView() {
        pullHistoryViewModel = new PullHistoryViewModel();
        pullHistoryView = new PullHistoryView(
                games,
                pullHistoryViewModel
        );
        cardPanel.add(
                pullHistoryView,
                PullHistoryViewModel.VIEW_NAME
        );
        return this;
    }

    public Appbuilder addPullHistoryUseCase() {

        // Create the factories used by the DAO.
        CollectableFactory collectableFactory = new CollectableFactory();
        GamePullHistoriesFactory historiesFactory = new GamePullHistoriesFactory();

        // Create the Data Access Object.
        StarRailPullHistoryDataAccessObject pullHistoryDAO =
                new StarRailPullHistoryDataAccessObject(
                        collectableFactory,
                        historiesFactory
                );

        // Create the Presenter.
        PullHistoryOutputBoundary pullHistoryPresenter =
                new PullHistoryPresenter(pullHistoryViewModel);

        // Create the Interactor.
        PullHistoryInputBoundary pullHistoryInteractor =
                new PullHistoryInteractor(
                        pullHistoryDAO,
                        pullHistoryPresenter
                );

        // Create the Controller and connect it to the View.
        PullHistoryController pullHistoryController =
                new PullHistoryController(pullHistoryInteractor);

        pullHistoryView.setPullHistoryController(pullHistoryController);

        return this;
    }


    public JFrame build() {
        final JFrame application = new JFrame("Gacha Game Pull Analysis");
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Navigation sidebar.
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new GridLayout(0, 1, 5, 5));

        JButton probabilityButton = new JButton("Probability");
        JButton pullHistoryButton = new JButton("Pull History");

        navigationPanel.add(probabilityButton);
        navigationPanel.add(pullHistoryButton);

        // Switch between views when buttons are clicked.
        probabilityButton.addActionListener(evt ->
                cardLayout.show(cardPanel, ProbabilityViewModel.VIEW_NAME));

        pullHistoryButton.addActionListener(evt ->
                cardLayout.show(cardPanel, PullHistoryViewModel.VIEW_NAME));

        // Assemble the application.
        mainPanel.add(navigationPanel, BorderLayout.WEST);
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        application.add(mainPanel);

        // Show Probability by default.
        cardLayout.show(cardPanel, ProbabilityViewModel.VIEW_NAME);

        application.pack();
        application.setSize(900, 600);
        application.setLocationRelativeTo(null);

        return application;
    }


    private List<Game> createGames() {

        List<Game> games = new ArrayList<>();

        Game starRail = new Game(
                "Honkai: Star Rail",
                "Stellar Jade",
                new HashMap<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );

        Game genshin = new Game(
                "Genshin Impact",
                "Primogem",
                new HashMap<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );

        Game zenlessZoneZero = new Game(
                "Zenless Zone Zero",
                "Polychrome",
                new HashMap<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );

        games.add(starRail);
        games.add(genshin);
        games.add(zenlessZoneZero);

        return games;
    }

}
