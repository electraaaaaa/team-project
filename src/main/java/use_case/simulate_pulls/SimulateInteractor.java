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
        int numOfSims = -1;
        int pullCountPerSim = -1;
        int hardPity = -1;
        int softPity = -1;
        double baseRate = -1;
        double softPityIncreaseRate = -1;
        try {
            numOfSims = Integer.parseInt(simulateInputData.getNumOfSims());
        }catch(NumberFormatException e){}//do nothing
        try{
            pullCountPerSim = Integer.parseInt(simulateInputData.getPullCountPerSim());
        }catch(NumberFormatException e){}
        try{
            hardPity = Integer.parseInt(simulateInputData.getHardPity());
        }catch(NumberFormatException e){}
        try{
            softPity = Integer.parseInt(simulateInputData.getSoftPity());
        }catch(NumberFormatException e){}
        try{
            baseRate = Double.parseDouble(simulateInputData.getBaseRate());
        }catch(NumberFormatException e){}
        try{
            softPityIncreaseRate = Double.parseDouble(simulateInputData.getSoftPityIncreaseRate());
        }catch(NumberFormatException e){}

        if(numOfSims<0){simulatePresenter.prepareFailView("The number of simulations is invalid");}
        if(pullCountPerSim<0){simulatePresenter.prepareFailView("The Pull Count Per Simulation is invalid");}
        if(hardPity<=0){simulatePresenter.prepareFailView("The Hard Pity is invalid");}
        if(softPity<0 || softPity > hardPity) {simulatePresenter.prepareFailView("The Soft Pity is invalid must be 0 < soft pity < hard pity");}
        if(softPityIncreaseRate<0){simulatePresenter.prepareFailView("The Soft Pity Increase Rate is invalid, must be Greater than 0");}
        if(baseRate<=0.0 || baseRate>1.0){simulatePresenter.prepareFailView("The base rate is invalid, must be from 0-1");}
        else {
            //otherwise run simulation and prepare success view
            this.results = runSimulation(numOfSims,pullCountPerSim,hardPity,softPity,baseRate,softPityIncreaseRate);
            double[] quartiles = statisticsHandler.findQuartiles(results);
            double mean = statisticsHandler.mean(results);
            double Q1 = quartiles[0];
            double Q2 = quartiles[1];
            double Q3 = quartiles[2];

            //        this.simulatePresenter.prepareSuccessView();
        }
    }


    private double[] runSimulation(int numSims,
                                   int pullCountPerSim,
                                   int hardPity,
                                   int softPity,
                                   double baseRate,
                                   double softPityIncreaseRate){
        Random random = new Random();
        double[] results = new double[numSims];
//        boolean guarenteeTF =  TODO: choose if you want a guarenteed after 1 standard or not

        for (int sim = 0; sim < numSims; sim++) {
            int pulls = 0;
            int pityCounter = 0;
            int highestRarityCount = 0;
            int featuredCount = 0;

            // Whether the next highest-rarity character is guaranteed only for guarentees
            boolean guaranteed = false;
            while (featuredCount < pullCountPerSim) {

                pulls++;
                pityCounter++;

                // calculate probability of highest rarity
                double currentRate = baseRate;

                if (pityCounter >= softPity) {
                    currentRate += (pityCounter - softPity + 1) * softPityIncreaseRate;
                }
                // Hard pity guarantees highest rarity
                if (pityCounter >= hardPity) {
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
