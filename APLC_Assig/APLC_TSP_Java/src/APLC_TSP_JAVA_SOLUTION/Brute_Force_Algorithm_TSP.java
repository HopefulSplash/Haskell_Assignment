/**
 * Defines the package the class belongs to.
 */
package APLC_TSP_JAVA_SOLUTION;

/**
 * Import all of the necessary libraries.
 */
import java.util.Arrays;

/**
 * The Brute_Force_Algorithm_TSP.Java Class implements an application that uses
 * a brute force algorithm to produce a desired route for the travelling
 * salesman problem.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Brute_Force_Algorithm_TSP {

    /**
     * 2d array contain all the information for each city (distances).
     */
    static double[][] city_Distance_Matrix = new double[][]{{0, 129, 119, 43.6, 98.6, 98.6, 86.3, 52.2, 85.3, 44.5},
    {129, 0, 88.3, 149, 152, 57.4, 55.4, 141, 93.3, 86.3},
    {119, 88.3, 0, 97.4, 71.6, 72.6, 42.5, 71.6, 35.5, 92.1},
    {43.6, 149, 97.4, 0, 54, 119, 107, 28, 64.2, 60.7},
    {98.6, 152, 71.6, 54, 0, 138, 85.2, 39.9, 48.6, 90.7},
    {98.6, 57.4, 72.6, 119, 138, 0, 34.9, 111, 77.1, 56.3},
    {86.3, 55.4, 42.5, 107, 85.2, 34.9, 0, 80.9, 37.9, 44.7},
    {52.2, 141, 71.6, 28, 39.9, 111, 80.9, 0, 38.8, 52.4},
    {85.3, 93.3, 35.5, 64.2, 48.6, 77.1, 37.9, 38.8, 0, 47.4},
    {44.5, 86.3, 92.1, 60.7, 90.7, 56.3, 44.7, 52.4, 47.4, 0}
    };

    /**
     * an array containing all the cities names in order so it can be used to
     * display the route.
     */
    static String[] city_Name_Array = {"BIRMINGHAM", "LANCASTER", "LEEDS", "LEICESTER", "LICOLN", "LIVERPPOOL", "MANCHESTER", "NOTTINGHAM", "SHEFFIELD", "STOKE-ON-TRENT"};

    /**
     * creating objects to hold the different elements used to calculate the
     * route (the best route and weight).
     */
    static int[] best_Route;
    static double best_Weight = Double.MAX_VALUE;

    /**
     * the main method used to generate and display the resulting route that the
     * algorithm produces.
     *
     * @param args
     */
    public static void main(String args[]) {

        /**
         * creates an array of the cities to visit in number form 9 is not
         * included due to it being Stoke On Trent.
         */
        int[] city_Numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8};

        /**
         * calls the method which generates the route that will be used later.
         */
        long startTime = System.currentTimeMillis();
        generate_Brute_Force_Route(city_Numbers, 0);

        long endTime = System.currentTimeMillis();

        double duration = (endTime - startTime) / 1000.00 ;

        /**
         * printing out display information to make the results understandable.
         */
        System.out.print("===================================================================================================================================================================\n");
        System.out.println("\t\t\t\t\t\t\t Best Route Using Brute Force Algorithm ");
        System.out.print("===================================================================================================================================================================\n\n");

        System.out.print("\t\t" + city_Name_Array[9] + "\t\t");

        /**
         * a for loop that will show the route with the first 3 characters of
         * the city's name instead of a number so the user can understand the
         * route easier.
         */
        for (Integer city_Nearest_Route_List1 : best_Route) {
            System.out.print(city_Name_Array[city_Nearest_Route_List1].substring(0, 3) + "\t");
        }
        System.out.print(city_Name_Array[9].substring(0, 3) + "\t");
        System.out.printf("\tRoute Weight: %.2f \n\n", best_Weight);

        System.out.print("===================================================================================================================================================================\n\n");
        System.out.printf("\t\t\t\t   Route Order: " + Arrays.toString(best_Route) + "\t\t\tRoute Weight: %.2f \n\n   ", best_Weight);

        System.out.print(city_Name_Array[9] + " --> ");

        /**
         * a for loop that is go through each of the places on the route and get
         * the right name so that instead of numbers it will show the name of
         * the city.
         */
        for (int o = 0; o < best_Route.length; o++) {
            System.out.print(city_Name_Array[best_Route[o]] + " --> ");
        }

        System.out.print(city_Name_Array[9]);
        System.out.print("\n\n===================================================================================================================================================================\n");

        System.out.println("Execution Time In Seconds " + duration);

    }

    /**
     * a method that implements a brute force algorithm when the data for a city
     * is entered (the 1d array from the 2d array containing all the distance
     * values) and a start location (0 so that is knows where to start).
     *
     * @param city_Permutation_Route
     * @param starting_Index
     */
    public static void generate_Brute_Force_Route(int[] city_Permutation_Route, int starting_Index) {

        /**
         * creating variables that will be used in the calculations in
         * determining the route.
         */
        double route_Distance;

        /**
         * checks if the permutation of the array has completed a cycle so it
         * can check the weight of the route.
         */
        if (city_Permutation_Route.length == starting_Index) {
            /**
             * generates the weight for the route.
             */
            route_Distance = generate_Route_Weight(city_Permutation_Route);

            /**
             * checks if the weight is better than the best weight.
             */
            if (route_Distance < best_Weight) {
                /**
                 * stores the weight and route so it can be printed out to the
                 * user later.
                 */
                best_Weight = route_Distance;
                best_Route = city_Permutation_Route;
            }
        } else {
            /**
             * a for loop does several cycles of permutation until the
             * starting_Index is equal to i:
             */
            for (int i = starting_Index; i < city_Permutation_Route.length; i++) {
                /**
                 * setting temp variables to save the data to while getting the
                 * permutations.
                 */
                int[] city_Route = city_Permutation_Route.clone();
                int temp_Route = city_Route[i];

                /**
                 * swapping the position of the elements.
                 */
                city_Route[i] = city_Route[starting_Index];
                city_Route[starting_Index] = temp_Route;

                /**
                 * goes through the method again to recursively get the
                 * permutations.
                 */
                generate_Brute_Force_Route(city_Route, starting_Index + 1);
            }
        }
    }

    /**
     * a method that will generate the weight (the cost) for the best route that
     * has been chosen.
     *
     * @param city_Route
     * @return
     */
    private static double generate_Route_Weight(int[] city_Permutation_Route) {

        /**
         * a variable to store the weight while doing the calculations.
         */
        double route_Distance;

        /**
         * adds the distance from stoke to the first stop to the total weight.
         */
        route_Distance = city_Distance_Matrix[9][city_Permutation_Route[0]];

        /**
         * a for loop that will add each of the values for each stop to get the
         * total weight of the route.
         */
        for (int i = 0; i < city_Permutation_Route.length - 1; i++) {
            route_Distance = route_Distance + city_Distance_Matrix[city_Permutation_Route[i]][city_Permutation_Route[i + 1]];

        }

        /**
         * adds the distance to get from the last stop back to Stoke On Trent to
         * the weight.
         */
        route_Distance = route_Distance + city_Distance_Matrix[city_Permutation_Route[8]][9];

        return route_Distance;

    }

}
