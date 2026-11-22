package app;

import data_access.InMemoryGameDataAccessObject;
import interface_adapter.calculate_probability.ProbabilityController;
import interface_adapter.calculate_probability.ProbabilityPresenter;
import interface_adapter.calculate_probability.ProbabilityViewModel;
import use_case.calculate_probability.ProbabilityInputBoundary;
import use_case.calculate_probability.ProbabilityInteractor;
import use_case.calculate_probability.ProbabilityOutputBoundary;
import view.ProbabilityView;

import javax.swing.*;
import java.awt.*;

/**
 * Builds the application: each use case adds its view and wires its Controller,
 * Interactor and Presenter here.
 */
public class Appbuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final InMemoryGameDataAccessObject gameDataAccessObject = new InMemoryGameDataAccessObject();

    private ProbabilityViewModel probabilityViewModel;
    private ProbabilityView probabilityView;

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

    public JFrame build() {
        final JFrame application = new JFrame("Gacha Game Pull Analysis");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);
        cardLayout.show(cardPanel, ProbabilityViewModel.VIEW_NAME);
        return application;
    }
}
