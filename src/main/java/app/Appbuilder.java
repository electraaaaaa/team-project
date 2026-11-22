package app;

import data_access.*;
import entity.Banner;
import entity.CollectableFactory;
import entity.Game;
import entity.GameFactory;
import entity.GamePullHistoriesFactory;
import interface_adapter.calculate_probability.ProbabilityController;
import interface_adapter.calculate_probability.ProbabilityPresenter;
import interface_adapter.calculate_probability.ProbabilityViewModel;
import interface_adapter.cost_conversion.ConversionController;
import interface_adapter.cost_conversion.ConversionPresenter;
import interface_adapter.cost_conversion.ConversionViewModel;
import interface_adapter.pull_history.PullHistoryController;
import interface_adapter.pull_history.PullHistoryPresenter;
import interface_adapter.pull_history.PullHistoryViewModel;
import use_case.calculate_probability.ProbabilityInputBoundary;
import use_case.calculate_probability.ProbabilityInteractor;
import use_case.calculate_probability.ProbabilityOutputBoundary;
import use_case.cost_conversion.ConversionInputBoundary;
import use_case.cost_conversion.ConversionInteractor;
import use_case.cost_conversion.ConversionOutputBoundary;
import use_case.pull_history.PullHistoryInputBoundary;
import use_case.pull_history.PullHistoryInteractor;
import use_case.pull_history.PullHistoryOutputBoundary;
import view.ConversionView;
import view.ProbabilityView;
import view.PullHistoryView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds the application: each use case adds its view and wires its Controller,
 * Interactor and Presenter here.
 */
public class Appbuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    CollectableFactory collectableFactory = new CollectableFactory();
    GamePullHistoriesFactory gamePullHistoriesFactory = new GamePullHistoriesFactory();
    GameFactory gameFactory = new GameFactory();

    private final InMemoryGameDataAccessObject gameDataAccessObject = new InMemoryGameDataAccessObject();
    private final StarRailPullHistoryDataAccessObject starRailPullHistoryDataAccessObject =
            new StarRailPullHistoryDataAccessObject(collectableFactory, gamePullHistoriesFactory);
    private final GenshinGameDataAccessObject genshinGameDataAccessObject =
            new GenshinGameDataAccessObject(gameFactory);
    private final StarRailGameDataAccessObject starRailGameDataAccessObject =
            new StarRailGameDataAccessObject(gameFactory);
    private final GameDataDataAccessObject gameDataDataAccessObject =
            new GameDataDataAccessObject(starRailGameDataAccessObject, genshinGameDataAccessObject);
    private final ExchangeRateDataAccessObject exchangeRateDataAccessObject = new ExchangeRateDataAccessObject();

    private final List<Game> games;

    private ProbabilityViewModel probabilityViewModel;
    private ProbabilityView probabilityView;

    private ConversionViewModel conversionViewModel;
    private ConversionView conversionView;

    private PullHistoryViewModel pullHistoryViewModel;
    private PullHistoryView pullHistoryView;

    public Appbuilder() {
        this.games = gameDataDataAccessObject.getAllGames();
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

    public Appbuilder addConversionView() {
        Map<String, List<String>> bannersByGame = new LinkedHashMap<>();
        for (Game game : games) {
            gameDataAccessObject.addGame(game);
            List<String> bannerNames = new ArrayList<>();
            for (Banner banner : game.getAllBanners()) {
                bannerNames.add(banner.getName());
            }
            bannersByGame.put(game.getName(), bannerNames);
        }
        List<String> currencies = exchangeRateDataAccessObject.getSupportedCurrencies();
        conversionViewModel = new ConversionViewModel();
        conversionView = new ConversionView(conversionViewModel, bannersByGame, currencies);
        conversionView.setExitAction(() -> cardLayout.show(cardPanel, ProbabilityViewModel.VIEW_NAME));
        cardPanel.add(conversionView, ConversionViewModel.VIEW_NAME);
        return this;
    }

    public Appbuilder addConversionUseCase() {
        final ConversionOutputBoundary conversionOutputBoundary = new ConversionPresenter(conversionViewModel);
        final ConversionInputBoundary conversionInteractor = new ConversionInteractor(
                gameDataAccessObject, exchangeRateDataAccessObject, conversionOutputBoundary);
        conversionView.setConversionController(new ConversionController(conversionInteractor));
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
        PullHistoryOutputBoundary pullHistoryPresenter =
                new PullHistoryPresenter(pullHistoryViewModel);
        PullHistoryInputBoundary pullHistoryInteractor =
                new PullHistoryInteractor(
                        starRailPullHistoryDataAccessObject,
                        pullHistoryPresenter
                );
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
        JButton conversionButton = new JButton("Pull Cost");
        JButton pullHistoryButton = new JButton("Pull History");

        navigationPanel.add(probabilityButton);
        navigationPanel.add(conversionButton);
        navigationPanel.add(pullHistoryButton);

        // Switch between views when buttons are clicked
        probabilityButton.addActionListener(event ->
                cardLayout.show(cardPanel, ProbabilityViewModel.VIEW_NAME));

        conversionButton.addActionListener(event ->
                cardLayout.show(cardPanel, ConversionViewModel.VIEW_NAME));

        pullHistoryButton.addActionListener(event ->
                cardLayout.show(cardPanel, PullHistoryViewModel.VIEW_NAME));

        // Assemble the navigation and the cards
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
}