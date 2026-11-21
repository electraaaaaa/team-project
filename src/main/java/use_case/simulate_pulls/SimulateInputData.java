package use_case.simulate_pulls;

public class SimulateInputData {
    private int pityNumber;
    private int numOfSims;
    private int pullCountPerSim;

    public SimulateInputData(int pityNum, int numSims, int pullCountPerSim){
        this.pityNumber = pityNum;
        this.numOfSims = numSims;
        this.pullCountPerSim = pullCountPerSim;
    }

    public int getPityNumber(){
        return this.pityNumber;
    }
    public int getNumOfSims(){
        return this.numOfSims;
    }
    public int getPullCountPerSim(){
        return this.pullCountPerSim;
    }
}
