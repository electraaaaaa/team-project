package use_case.simulate_pulls;

import java.util.Locale;

/**
    input data for simulate use_case
 */
public class SimulateInputData {
    private final int numOfSims;
    private final int pullCountPerSim;
    private final int hardPity;
    private final int softPity;
    private final double baseRate;
    private final double softpityincreaserate;
//    private final boolean guarenteedLimited;
    private final String pitySystem;

    public SimulateInputData(int numSims,
                             int pullCountPerSim,
                             int hardpity,
                             int softpity,
                             float baserate,
                             float softpityincreaserate,
                             String pitySystem){
        this.numOfSims = numSims;
        this.pullCountPerSim = pullCountPerSim;
        //pity system data
        this.hardPity = hardpity;
        this.softPity = softpity;
        this.baseRate = baserate;
        this.softpityincreaserate = softpityincreaserate;
        this.pitySystem = pitySystem.toLowerCase(); //may need to check the api for any games matching the name,
    }

    public int getNumOfSims(){
        return this.numOfSims;
    }
    public int getPullCountPerSim(){
        return this.pullCountPerSim;
    }
    public int getHardPity(){
        return this.hardPity;
    }
    public int getSoftPity(){
        return this.softPity;
    }
    public double getBaseRate(){
        return this.baseRate;
    }
    public double getSoftPityIncreaseRate(){
        return this.softpityincreaserate;
    }
    public String getPitySystem(){
        return this.pitySystem;
    }

}
