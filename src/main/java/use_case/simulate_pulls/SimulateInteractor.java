package use_case.simulate_pulls;

import java.util.Random;

/**
 * The Use Case Interactor for the Simulate Use Case
 */
public class SimulateInteractor implements SimulateInputBoundary{

    private final SimulateOutputBoundary simulatePresenter;
    private final StatisticsHandler statisticsHandler;
    private double[] results;


    public SimulateInteractor(SimulateOutputBoundary simulatePresenter,
                              StatisticsHandler statisticsHandler){
        this.simulatePresenter = simulatePresenter;
        this.statisticsHandler = statisticsHandler;
    }

//    //overloaded constructor in case Game and Pity System exist
//    public simulateInteractor(){
//    }

    public void execute(SimulateInputData simulateInputData){
        //TODO: if the pity system exists data.pitySystem, update all pity system data with the given pity system objects.

//        TODO:find out if anything doesn't make sense; respond with the appropriate fail message

//        this.simulatePresenter.prepareFailView();
        //otherwise run simulation and prepare success view
        this.results = runSimulation(simulateInputData);

//        this.simulatePresenter.prepareSuccessView();
    }


    private double[] runSimulation(SimulateInputData data){
        Random random = new Random();
        double[] results = new double[data.getNumOfSims()];
//        boolean guarenteeTF =

        for (int sim = 0; sim < data.getNumOfSims(); sim++) {
            int pulls = 0;
            int pityCounter = 0;
            int highestRarityCount = 0;
            int featuredCount = 0;

            // Whether the next highest-rarity character is guaranteed only for guarentees
            boolean guaranteed = false;
            while (featuredCount < data.getPullCountPerSim()) {

                pulls++;
                pityCounter++;

                // Calculate probability of highest rarity
                double currentRate = data.getBaseRate();

                if (pityCounter >= data.getSoftPity()) {
                    currentRate +=
                            (pityCounter - data.getSoftPity() + 1)
                                    * data.getSoftPityIncreaseRate();
                }
                // Hard pity guarantees highest rarity
                if (pityCounter >= data.getHardPity()) {
                    currentRate = 1.0;
                }
                // Check whether this pull is highest rarity
                if (random.nextDouble() < currentRate) {
                    highestRarityCount++;
                    // Reset pity after obtaining highest rarity
                    pityCounter = 0;
                    // Check 50/50
                    if (guaranteed) {
                        // Guaranteed featured character
                        featuredCount++;
                        guaranteed = false;
                    } else {
                        // 50% chance of winning
                        if (random.nextDouble() < 0.5) {
                            featuredCount++;
                        } else {
                            guaranteed = true;
                        }
                    }
                }
            }

            // Record total pulls required
            results[sim] = pulls;
        }
    }


}
