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

            simulatePresenter.prepareFailView("");
//        TODO:find out if anything doesn't make sense; respond with the appropriate fail message
        if(simulateInputData.getNumOfSims()<0){simulatePresenter.prepareFailView("The number of simulations is invalid");}
        if(simulateInputData.getPullCountPerSim()<0){simulatePresenter.prepareFailView("The Pull Count Per Simulation is invalid");}
        if(simulateInputData.getHardPity()<=0){simulatePresenter.prepareFailView("The Hard Pity is invalid");}
        if(simulateInputData.getSoftPity()<=0 || simulateInputData.getSoftPity()>simulateInputData.getHardPity())
        {simulatePresenter.prepareFailView("The Soft Pity is invalid");}
        if(simulateInputData.getSoftPityIncreaseRate()<=0){simulatePresenter.prepareFailView("The Soft Pity Increase Rate is invalid");}
        if(simulateInputData.getBaseRate()<=0|| simulateInputData.getBaseRate()>100){simulatePresenter.prepareFailView("The base rate is invalid");}



//        this.simulatePresenter.prepareFailView();
        //otherwise run simulation and prepare success view
        this.results = runSimulation(simulateInputData);
        double[] quartiles = statisticsHandler.findQuartiles(results);
        double mean = statisticsHandler.mean(results);
        double Q1 = quartiles[0];
        double Q2 = quartiles[1];
        double Q3 = quartiles[2];

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

                // calculate probability of highest rarity
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
        return results;
    }


}
