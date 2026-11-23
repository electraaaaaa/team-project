package use_case.simulate_pulls;

import java.util.SplittableRandom;

/**
 * The Use Case Interactor for the Simulate Use Case
 */
public class SimulateInteractor implements SimulateInputBoundary{

    private final SimulateOutputBoundary simulatePresenter;
    private final StatisticsHandler statisticsHandler;


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


        // perform all number format checks and PreparefailView if any don't pass

        int numOfSims = -1;
        int pullCountPerSim = -1;
        int hardPity = -1;
        int softPity = -1;
        double baseRate = -1;
        double softPityIncreaseRate = -1;
        boolean guaranteeLimited = simulateInputData.getGuaranteedLimited();
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
        if(softPity<0 || softPity >= hardPity) {simulatePresenter.prepareFailView("The Soft Pity is invalid must be 0 < soft pity =< hard pity");}
        if(softPityIncreaseRate<0){simulatePresenter.prepareFailView("The Soft Pity Increase Rate is invalid, must be Greater than 0");}
        if(baseRate<=0.0 || baseRate>1.0){simulatePresenter.prepareFailView("The base rate is invalid, must be from 0-1");}
        else {
            //otherwise run simulation and prepare success view
            double[] results = runSimulation(numOfSims,pullCountPerSim,hardPity,softPity,baseRate,softPityIncreaseRate, guaranteeLimited);
            double[] quartiles = statisticsHandler.findQuartiles(results);
            double mean = statisticsHandler.mean(results);
            double Q1 = quartiles[0];
            double Q2 = quartiles[1];
            double Q3 = quartiles[2];
            SimulateOutputData simulateOutputData = new SimulateOutputData(results, mean, Q1, Q2, Q3);
            this.simulatePresenter.prepareSuccessView(simulateOutputData);
        }
    }

    private double[] runSimulation(int numSims,
                                   int pullCountPerSim,
                                   int hardPity,
                                   int softPity,
                                   double baseRate,
                                   double softPityIncreaseRate,
                                   boolean guaranteeLimited){
        double[] results = new double[numSims];
        SplittableRandom rng = new SplittableRandom();  //maybe better for doing more simulations? (from AI)

        for (int sim = 0; sim < numSims; sim++) {
            int pity = 0;
            int limitedPulled = 0;
            boolean guaranteedNextLimited = false;

            for (int pull = 0; pull < pullCountPerSim; pull++) {
                pity++;

                double currentRate;
                if (pity >= hardPity) {
                    currentRate = 1.0;
                } else if (softPity > 0 && pity >= softPity) {
                    int softPullsIntoPity = pity - softPity + 1;
                    currentRate = Math.min(1.0, baseRate + softPullsIntoPity * softPityIncreaseRate);
                } else {
                    currentRate = baseRate;
                }

                if (rng.nextDouble() < currentRate) {
                    // Pulled a 5-star; reset pity counter
                    pity = 0;

                    if (guaranteeLimited && guaranteedNextLimited) {
                        limitedPulled++;
                        guaranteedNextLimited = false;
                    } else {
                        boolean wonFiftyFifty = rng.nextDouble() < 0.5;
                        if (wonFiftyFifty) {
                            limitedPulled++;
                            guaranteedNextLimited = false;
                        } else if (guaranteeLimited) {
                            guaranteedNextLimited = true;
                        }
                    }
                }
            }

            results[sim] = limitedPulled;
        }
        return results;
    }

}
