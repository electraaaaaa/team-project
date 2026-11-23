package interface_adapter.simulate_pulls;

import use_case.simulate_pulls.SimulateInputBoundary;
import use_case.simulate_pulls.SimulateInputData;

public class SimulateController {
    private final SimulateInputBoundary simulateUseCaseInteractor;

    public SimulateController(SimulateInputBoundary simulateUseCaseInteractor){
        this.simulateUseCaseInteractor = simulateUseCaseInteractor;
    }

    /**
     * executes the signup Use Case
     * @param numSims
     * @param pullCountPerSim
     * @param hardpity
     * @param softpity
     * @param baserate
     * @param softpityincreaserate
     * @param guarenteedLimited
     * @param pitySystem
     */
    public void execute(String numSims,
                        String pullCountPerSim,
                        String hardpity,
                        String softpity,
                        String baserate,
                        String softpityincreaserate,
                        boolean guarenteedLimited,
                        String pitySystem){
        final SimulateInputData simulateInputData = new SimulateInputData(numSims, pullCountPerSim, hardpity, softpity,
                baserate, softpityincreaserate, guarenteedLimited, pitySystem);
        simulateUseCaseInteractor.execute(simulateInputData);

    }
}
