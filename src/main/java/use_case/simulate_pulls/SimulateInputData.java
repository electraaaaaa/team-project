package use_case.simulate_pulls;


/**
    input data for simulate use_case
 */
import entity.PitySystem;

public class SimulateInputData {
    private final String numOfSims;
    private final String pullCountPerSim;
    private final String hardPity;
    private final String softPity;
    private final String baseRate;
    private final String softpityincreaserate;
    private final boolean guaranteedLimited;
    private final String pitySystem;

    public SimulateInputData(String numSims,
                             String pullCountPerSim,
                             String hardpity,
                             String softpity,
                             String baserate,
                             String softpityincreaserate,
                             boolean guaranteedLimited,
                             String pitySystem){
        this.numOfSims = numSims;
        this.pullCountPerSim = pullCountPerSim;
        //pity system data
        this.hardPity = hardpity;
        this.softPity = softpity;
        this.baseRate = baserate;
        this.softpityincreaserate = softpityincreaserate;
        this.guaranteedLimited = guaranteedLimited;
        this.pitySystem = pitySystem.toLowerCase(); //may need to check the api for any games matching the name,
    }

    public String getNumOfSims(){
        return this.numOfSims;
    }
    public String getPullCountPerSim(){
        return this.pullCountPerSim;
    }
    public String getHardPity(){return this.hardPity;}
    public String getSoftPity(){
        return this.softPity;
    }
    public String getBaseRate(){
        return this.baseRate;
    }
    public String getSoftPityIncreaseRate(){
        return this.softpityincreaserate;
    }
    public boolean getGuaranteedLimited(){return this.guaranteedLimited; }
    public String getPitySystem(){return this.pitySystem;}

}
