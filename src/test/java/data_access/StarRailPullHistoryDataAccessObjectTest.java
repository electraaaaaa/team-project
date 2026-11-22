package data_access;

import entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class StarRailPullHistoryDataAccessObjectTest {

    public static void main(String[] args) {

        // Create a temporary HSR Game object
        Game starRail = new Game(
                "Honkai: Star Rail",
                "Stellar Jade",
                new HashMap<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );

        CollectableFactory collectableFactory = new CollectableFactory();
        GamePullHistoriesFactory gamePullHistoriesFactory = new GamePullHistoriesFactory();

        // Create the DAO
        StarRailPullHistoryDataAccessObject dao =
                new StarRailPullHistoryDataAccessObject(collectableFactory, gamePullHistoriesFactory);

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== HSR Warp Import Test ===");
        System.out.print("Enter your warp history URL: ");

        String url = scanner.nextLine();

        try {
            System.out.println("\nImporting warp history...");

            GamePullHistories histories = dao.getGamePullHistory(starRail, url);

            for (PullHistory history :
                    histories.getAllHistories().values()) {

                System.out.println("--------------------");
                System.out.println("Banner: " + history.getType());
                System.out.println("Total pulls: "
                        + history.getTotalPulls());
                System.out.println("Current pity: "
                        + history.getCurrentPity());
            }

        } catch (Exception e) {
            System.out.println("Import failed!");
            System.out.println("Error: " + e.getMessage());
        }
    }

}