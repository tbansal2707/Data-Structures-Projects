/*
 * Name: Tushar Bansal
 * EID: tb34826
 */

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Collections;
//import java.util.LinkedList;

/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program3 extends AbstractProgram3 {

    /**
     * Determines the solution of the optimal response time for the given input TownPlan. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return Updated TownPlan town with the "responseTime" field set to the optimal response time
     */
    @Override
    public TownPlan findOptimalResponseTime(TownPlan town) {
        int n = town.getHouseCount();
        int k = town.getStationCount();
        ArrayList<Integer> housePositions = town.getHousePositions();

        // Sort house positions in ascending order
        //Collections.sort(housePositions);

        int[][] r = new int[n][k];
        


        // Base case: One house, response time is 0 for all possible police station placements with minimum one
        for (int j = 0; j < k; j++) {
            r[0][j] = 0;
        }

        // Base case: One police station, response time is difference between first and last house positions
        for (int i = 1; i < n; i++) {
            r[i][0] = (housePositions.get(i) - housePositions.get(0))/2;
        }

        

        // Fill the dp table using bottom-up dynamic programming
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < k; j++) {
                r[i][j] = Integer.MAX_VALUE;
                int responseTime = 0;
                int minResponseTime = Integer.MAX_VALUE;
                
                for (int x = 0; x < i; x++) {

                    // Scenario 1: Place the j-1th police station at or before house x
                    int responseTime1 = r[x][j-1];
                    // Scenario 2: Place the jth police station halfway between x+1 and i
                    int responseTime2 = (housePositions.get(i) - housePositions.get(x+1)) / 2;

                    if (responseTime1 >= responseTime2) {

                        responseTime = responseTime1;

                    }
                    else {

                        responseTime = responseTime2;
                    }

                    if (responseTime < minResponseTime) {

                        minResponseTime = responseTime;
                    }
                }

                r[i][j] = minResponseTime;
            }
        }

        // Extract the optimal response time
        int optimalResponseTime = r[n-1][k-1];

        // Update the TownPlan object with the optimal response time
        town.setResponseTime(optimalResponseTime);

        return town;
    }
        //return town;
    //}

    /**
     * Determines the solution of the set of police station positions that optimize response time for the given input TownPlan. Study the
     * project description to understand the variables which represent the input to your solution.
     *x
     * @return Updated TownPlan town with the "policeStationPositions" field set to the optimal police station positions
     */
    @Override
    public TownPlan findOptimalPoliceStationPositions(TownPlan town) {

        int n = town.getHouseCount();
        int k = town.getStationCount();
        ArrayList<Integer> housePositions = town.getHousePositions();
        int[][] c = new int[n][k];
        int[][] r = new int[n][k];
        int[][] l = new int[n][k];

        // Base case: One house, response time is 0 for all possible police station placements with minimum one
        for (int j = 0; j < k; j++) {
            r[0][j] = 0;
            c[0][j] = housePositions.get(0);
            l[0][j] = 0;
        }

        // Base case: One police station, response time is difference between first and last house positions
        for (int i = 1; i < n; i++) {
            r[i][0] = (housePositions.get(i) - housePositions.get(0))/2;
            c[i][0] = (housePositions.get(i) + housePositions.get(0))/2;
            l[i][0] = 0;
        }


        

        // Fill the dp table using bottom-up dynamic programming
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < k; j++) {
                r[i][j] = Integer.MAX_VALUE;
                int prevLocation = 0;
                int responseTime = 0;
                int minResponseTime = Integer.MAX_VALUE;
                int policeStationPlacement = 0;
                //int policeStationPlacementForMin = 0;
                
                for (int x = 0; x < i; x++) {

                    // Scenario 1: Place the j-1th police station at or before house x
                    int responseTime1 = r[x][j-1];
                    // Scenario 2: Place the jth police station halfway between x+1 and i
                    int responseTime2 = (housePositions.get(i) - housePositions.get(x+1)) / 2;


                    if (responseTime1 >= responseTime2) {

                        responseTime = responseTime1;
                        //policeStationPlacement = housePositions.get(i);

                    }
                    else {

                        responseTime = responseTime2;
                        //policeStationPlacement = (housePositions.get(i) + housePositions.get(x+1)) / 2;
                    }

                    if (responseTime < minResponseTime) {
                        minResponseTime = responseTime;
                        policeStationPlacement = (housePositions.get(i) + housePositions.get(x+1)) / 2;
                        prevLocation = x;
                    }

                    //System.out.println(minResponseTime);
                }

                r[i][j] = minResponseTime;
                c[i][j] = policeStationPlacement;
                l[i][j] = prevLocation;

                //System.out.println(c[i][j]);

            }
        }

        ArrayList<Integer> optimalPoliceStationPositions = new ArrayList<>();
        int currentHouse = n-1;// Start from the last house
        int currentStation = k-1; // Start from the last police station
        int flag = 0;

        while (currentStation >= 0) {
            optimalPoliceStationPositions.add(c[currentHouse][currentStation]);
            currentHouse = l[currentHouse][currentStation]; // Move to the previous house which was not covered

            if (currentHouse == 0) {

                if (flag == 1) {
                    break;
                }

                if (flag == 0) {
                    flag = 1;
                }
                
            }
            currentStation = currentStation - 1; // Move to the previous police station
        }


        Collections.reverse(optimalPoliceStationPositions);

        // Update the TownPlan object with the optimal police station positions
        town.setPoliceStationPositions(optimalPoliceStationPositions);

        return town;


        //return town;
    //}
}
}