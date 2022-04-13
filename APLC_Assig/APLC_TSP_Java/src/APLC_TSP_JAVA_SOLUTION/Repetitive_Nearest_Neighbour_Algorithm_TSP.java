/**
 * Defines the package the class belongs to.
 */
package APLC_TSP_JAVA_SOLUTION;

/**
 * Import all of the necessary libraries.
 */
import java.util.ArrayList;

/**
 * The Repetitive_Nearest_Neighbour_Algorithm_TSP.Java Class implements an application that uses
 * a Repetitive Nearest Neighbour algorithm to produce a desired route for the
 * travelling salesman problem.
 *
 * @author Harry Clewlow (C012952A)
 * @version 1.0
 * @since 18-01-2014
 */
public class Repetitive_Nearest_Neighbour_Algorithm_TSP {

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
     * route (lists: all the routes and weights, the best route and weight and
     * the starting location.
     */
    static ArrayList<Integer> city_Nearest_Route_List = new ArrayList<>();
    static ArrayList<Double> city_Nearest_Route_Weight_List = new ArrayList<>();
    static ArrayList<Double> all_Route_Weights_List = new ArrayList<>();
    static ArrayList<Integer> best_Route_List = new ArrayList<>();
    static double best_Weight = Double.MAX_VALUE;
    static int start_Location;

    /**
     * the main method used to generate and display the resulting route that the
     * algorithm produces.
     *
     * @param args
     */
    public static void main(String args[]) {

        /**
         * variable to hold the routes weight.
         */
        double route_Weight;

        /**
         * printing out display information to make the results understandable.
         */
        System.out.print("===================================================================================================================================================================\n");
        System.out.println("\t\t\t\t\t\t\tAll Routes Using Nearest Neighbour Algorithm ");
        System.out.print("===================================================================================================================================================================\n");

        /**
         * a for loop that will generate the a route using the algorithm for
         * each city
         */
        long startTime = System.nanoTime();
        for (int i = 0; i < city_Distance_Matrix[0].length; i++) {

            /**
             * clearing the lists so that the data for the next city can be
             * entered into them.
             */
            city_Nearest_Route_List.clear();
            city_Nearest_Route_Weight_List.clear();

            /**
             * calls the method which generates the route that will be used
             * later.
             */
            generate_Nearesest_Route(i);

            /**
             * printing out the results so that a user can see all the different
             * routes so the user can understand the data being used to generate
             * the best possible route.
             */
            if (i == 2 || i == 4) {
                System.out.print("\t\t" + city_Name_Array[start_Location] + "\t\t\t");
            } else {
                System.out.print("\t\t" + city_Name_Array[start_Location] + "\t\t");
            }
            /**
             * a for loop that will show the route with the first 3 characters
             * of the city's name instead of a number so the user can understand
             * the route easier.
             */
            for (Integer city_Nearest_Route_List1 : city_Nearest_Route_List) {
                System.out.print(city_Name_Array[city_Nearest_Route_List1].substring(0, 3) + "\t");
            }

            /**
             * generates the routes weight and stores it so it can be compared
             * to the other routes later.
             */
            route_Weight = generate_Route_Weight(city_Nearest_Route_Weight_List);
            System.out.printf("\tRoute Weight: %.2f \n", route_Weight);

            /**
             * adds the current routes weight to the list of the other routes
             * weights so it can be compared.
             */
            all_Route_Weights_List.add(route_Weight);

            /**
             * each time a route is added it will check if that route is the
             * best route against the other routes that are currently store in
             * the list.
             */
            generate_Best_Route(all_Route_Weights_List, city_Nearest_Route_List);
        }

        /**
         * calls a method that will implement the second half of the algorithm
         * the alter the best route found to start and end with Stoke On Trent.
         */
        transpose_Route_Stoke(best_Route_List);

          long endTime = System.nanoTime();

        double duration = (endTime - startTime) / 1000000000.0;
        /**
         * printing out the final best route to take in a normal that the user
         * can understand.
         */
        System.out.print("===================================================================================================================================================================\n");
        System.out.println("\t\t\t\t\t\t    Best Route Using Repetitive Nearest Neighbour Algorithm ");
        System.out.println("===================================================================================================================================================================\n");

        System.out.printf("\t\t\t\t   Route Order: " + best_Route_List + "\t\t\tRoute Weight: %.2f \n\n   ", best_Weight);

        /**
         * a for loop that is go through each of the places on the route and get
         * the right name so that instead of numbers it will show the name of
         * the city.
         */
        for (int o = 0; o < best_Route_List.size(); o++) {
            if (o < best_Route_List.size() - 1) {
                System.out.print(city_Name_Array[best_Route_List.get(o)] + " --> ");
            } else {
                System.out.print(city_Name_Array[best_Route_List.get(o)]);
            }
        }

        System.out.println("\n\n===================================================================================================================================================================");

       System.out.printf("Execution Time In Seconds %.7f \n" , duration);
        }

    /**
     * a method that allows the user to set the starting city if they wanted to
     * travel from another city instead of Stoke On Trent.
     *
     * @param starting_City
     */
    private static void generate_Nearesest_Route(int starting_City) {

        /*
         * sets a global variable for the stating city so that other functions can user it without passing it into several different methods.
         */
        start_Location = starting_City;

        /**
         * calls a method that will generate the route using the nearest
         * neighbour algorithm using the city related to the value of the
         * variable starting_City.
         */
        generate_Nearest_Neighbour(city_Distance_Matrix[starting_City]);

    }

    /**
     * a method that implements the nearest neighbour algorithm when the data
     * for a city is entered (the 1d array from the 2d array containing all the
     * distance values).
     *
     * @param city_Data
     */
    private static void generate_Nearest_Neighbour(double[] city_Data) {

        /**
         * creating variables that will be used in the calculations in
         * determining the route.
         */
        double min_Value = Double.MAX_VALUE;
        int next_City = 0;

        /**
         * a for loop that will run through all the different distances store in
         * the 1d array called city_Data.
         */
        for (int i = 0; i < city_Data.length; i++) {

            /**
             * checks if the value that is pulled from the array is not equal to
             * 0 so that it doesn't use the value that is for a cities distance
             * from itself.
             */
            if (city_Data[i] != 0) {

                /**
                 * checks if the city that is not already in the route and is
                 * not the starting city.
                 */
                if (!city_Nearest_Route_List.contains(i) && i != start_Location) {

                    /**
                     * check is distance is lower than the min_Value so get the
                     * next city to visit.
                     */
                    if (city_Data[i] <= min_Value) {

                        /**
                         * sets the min_Value so that is can be compared to the
                         * next value/s
                         */
                        min_Value = city_Data[i];

                        /**
                         * sets the value of the next city to visit.
                         */
                        next_City = i;

                    }
                }

            }
        }

        /**
         * after getting the nearest route from the for loop above the values
         * are stored in the relevant lists.
         */
        city_Nearest_Route_List.add(next_City);
        city_Nearest_Route_Weight_List.add(min_Value);

        /**
         * checks if all the possible routes have been generated if they have
         * then it will add the values to the list and exit the method if they
         * have not then it will use recursion to generate the rest.
         */
        if (city_Nearest_Route_List.size() != 9) {
            generate_Nearest_Neighbour(city_Distance_Matrix[next_City]);
        } else {
            city_Nearest_Route_List.add(start_Location);
            city_Nearest_Route_Weight_List.add(city_Distance_Matrix[city_Nearest_Route_List.get(8)][start_Location]);
        }

    }

    /**
     * a method that will generate the weight (the cost) of each route so that
     * the best route can be chosen.
     *
     * @param city_Route
     * @return
     */
    private static double generate_Route_Weight(ArrayList<Double> city_Route) {

        /**
         * a variable to store the weight while doing the calculations.
         */
        double route_Weight = 0;

        /**
         * a for loop that will add each of the values for a route to get the
         * total weight of the route.
         */
        for (Double city_Route1 : city_Route) {
            route_Weight += city_Route1;
        }

        return route_Weight;
    }

    /**
     * a method that will generate the best route by comparing all the best
     * routes when you start a different city and store the result in the global
     * variables.
     *
     * @param all_Route_Weights
     * @param city_Nearest_Route
     */
    private static void generate_Best_Route(ArrayList<Double> all_Route_Weights, ArrayList<Integer> city_Nearest_Route) {

        /**
         * a for loop that is go trough all the elements of the
         * all_Route_Weights list.
         */
        for (Double all_Route_Weight : all_Route_Weights) {

            /**
             * checks if the value is better than the current best weight if so
             * it will then assign it to the variable and get the route it came
             * from and save that in another variable
             *
             */
            if (all_Route_Weight < best_Weight) {
                best_Weight = all_Route_Weight;
                best_Route_List = new ArrayList<>(city_Nearest_Route);
            }
        }

    }

    /**
     * a method that will convert the best route from another city into a route
     * where you start and end at Stoke On Trent.
     *
     * @param city_Nearest_Route
     */
    private static void transpose_Route_Stoke(ArrayList<Integer> city_Nearest_Route) {

        /**
         * a temp variable to store a routes city in while it is moved.
         */
        int temp_City = 0;

        /**
         * a while loop that will check is the route has been transposed so that
         * 9 (Stoke On Trent) is at the front.
         */
        while (city_Nearest_Route.get(0) != 9) {
            /**
             * puts the first element in the list into temp.
             */
            temp_City = city_Nearest_Route.get(0);

            /**
             * a for loop that is go through all the elements in the list.
             */
            for (int i = 0; i < city_Nearest_Route.size(); i++) {

                /**
                 * check if the there is any elements left to change.
                 */
                if (i == city_Nearest_Route.size() - 1) {
                    /**
                     * sets the value of i in the city_Nearest_Route to the
                     * value of temp_city.
                     */
                    city_Nearest_Route.set(i, temp_City);
                } else {
                    /**
                     * sets the value of i in the city_Nearest_Route to value of
                     * i+1 so that it swaps the position.
                     */
                    city_Nearest_Route.set(i, city_Nearest_Route.get(i + 1));
                }
            }

        }

        /**
         * adds 9 to the end of the list so that you can see the route returning
         * to Stoke On Trent.
         */
        city_Nearest_Route.add(9);

    }

}
