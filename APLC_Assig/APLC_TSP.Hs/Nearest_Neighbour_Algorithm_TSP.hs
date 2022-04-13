{-  
    The Nearest_Neighbour_Algorithm_TSP.Hs implements an application using a
	nearest neighbour algorithm to produce a route for the travelling salesman problem.
	
	@author Harry Clewlow (C012952A)
	@version 1.0
	@since 18-03-2014
-}	

{- The list of lists used for storing the information about each city as a double (distance from one city to another)-}
distance_Matrix = [[0, 129, 119, 43.6, 98.6, 98.6, 86.3, 52.2, 85.3, 44.5],
        [129, 0, 88.3, 149, 152, 57.4, 55.4, 141, 93.3, 86.3],
        [119, 88.3, 0, 97.4, 71.6, 72.6, 42.5, 71.6, 35.5, 92.1],
        [43.6, 149, 97.4, 0, 54, 119, 107, 28, 64.2, 60.7],
        [98.6, 152, 71.6, 54, 0, 138, 85.2, 39.9, 48.6, 90.7],
        [98.6, 57.4, 72.6, 119, 138, 0, 34.9, 111, 77.1, 56.3],
        [86.3, 55.4, 42.5, 107, 85.2, 34.9, 0, 80.9, 37.9, 44.7],
        [52.2, 141, 71.6, 28, 39.9, 111, 80.9, 0, 38.8, 52.4],
        [85.3, 93.3, 35.5, 64.2, 48.6, 77.1, 37.9, 38.8, 0, 47.4],
        [44.5, 86.3, 92.1, 60.7, 90.7, 56.3, 44.7, 52.4, 47.4, 0]] :: [[Double]];

{- The names of each city in the same order as they are in the distance_Matrix so they can be used to output a route so a user can understand it -}
name_Array = ["BIRMINGHAM", "LANCASTER", "LEEDS", "LEICESTER", "LICOLN", "LIVERPPOOL", "MANCHESTER", "NOTTINGHAM", "SHEFFIELD", "STOKE-ON-TRENT"];

{- 
   A list of chars that represent the cities a travelling salesman can visit not including STOKE-ON-TRENT i have hard coded this because the 
   assignment states that you are starting from and returning to STOKE-ON-TRENT
-}			
city_Index = [0 .. 9] :: [Int];		

{-
   A function that will take the list of permutations for the different routes you can take from STOKE-ON-TRENT and then return back to it, then it then it will add
   9 to the start of each element so that they can be modified into co-ordinates to be used on the distance_Matrix.
-}
get_Permutations :: [[Int]] -> [(Double, [Int] )]
get_Permutations [[]] = [( 0 , [])]
get_Permutations xs =    [(get_Weight ( ( x)) , ( x)) | x <- xs ]

{-
   A function that will get the take a list of Ints and zip them together so that they can be used as co-ordinates for use on the distance_Matrix
   to get the value that represents the distance from x to y (one city to another).
-}
get_Weight :: [Int] -> Double
get_Weight [] = error "Empty List"
get_Weight (x:xs) = generate_Weight $ zipWith (\c1 c2 -> distance_Matrix !! c1 !! c2) (x:xs)(xs)

{- A function that will add  a Int representing a city in front of all the different elements in a list so that they can be used as co-ordinates as reference
   for the distance_Matrix
-}
add_City :: Int -> [Int] -> [[Int]]
add_City i [] = []
add_City i (x:xs) =  [i,x] : add_City i xs

{-
   A function that take a list of double values and add them all up to give you a total, in this case it will add all the different stops
   on a route up to give you the total weight for that specific route.
-}
generate_Weight :: [Double] -> Double
generate_Weight []     = 0
generate_Weight (x:[]) = x
generate_Weight (x:xs) = x + generate_Weight xs

{- A function that will remove all the co-ordinates that return 0 eg (9,9 so the same city to itself).-}
getlist :: [(Double, [Int])] -> [(Double, [Int])]
getlist [(_, [])] = [(0,[])]
getlist xs = filter condition xs
     where condition (n,_) = n > 0

{- A function that will get the next city to visit by getting the last element in the list of Int eg p[9,0] it will get 0 back so the next city
   would be BIRMINGHAM.
-}
get_Next_City :: (Double, [Int]) -> Int
get_Next_City (_, []) = error "List is empty"
get_Next_City place = last (snd place)

{- A function that will remove an element from a list eg if the route have just visited number 0 it will remove 0 from the list-}
remove_Item :: Int -> [Int] -> [Int]
remove_Item _ [] = []
remove_Item i xs = filter condition xs
     where condition (n) = n /= i

{-	 
   A function that is used to et the route with the smallest weight (best) it does this by compare one element against the next using an if
   statement that will return the smallest route once it has compared all the values in the object passed into it.
-}
get_Min_Route :: (Ord a) => [a] -> a 
get_Min_Route [] = error "List is empty"
get_Min_Route [x] = x  
get_Min_Route (x:y:xs) = if x < y then get_Min_Route(x:xs) 
		else get_Min_Route(y:xs)

{- 
   A function that is covert a list of Int type into the names of each city so that it is more user friendly by taking a tuple and adding a 
   list of list of chars (String) that will contain the names this is done by using the Ints as an index for name_Array.
-}		
get_Names :: (Double, [Int]) -> (Double, [Int],[[Char]])
get_Names ( nil , []) = error "List is empty"
get_Names xs = (fst xs , snd xs, [name_Array !! p| p<-(snd xs)] )

{- A function that will generate the best route when a user enters the starting location and the list of other cities you can  visit-}
generate_Best_Route :: Int -> [Int] -> [Int]
generate_Best_Route _ [] = []
generate_Best_Route strt citylist =  get_Next_City(get_Min_Route(getlist (get_Permutations (add_City strt citylist))))  :  generate_Best_Route  (get_Next_City(get_Min_Route(getlist (get_Permutations (add_City strt citylist)))))  (remove_Item (get_Next_City(get_Min_Route(getlist (get_Permutations (add_City strt citylist))))) citylist)

{- A function that will remove the a value that is passed into the method eg the start city from the list of cities you can visit. -}
remove_Duplicate :: Int -> [Int] -> [Int]
remove_Duplicate _ [] = []
remove_Duplicate str citylist =  generate_Best_Route str (remove_Item str citylist)

{- A function that gets the total weight of the final route so that it an be displayed to the user. -}
get_Nearest_Neighbour :: [Int] -> (Double, [Int])
get_Nearest_Neighbour [] = (0 , [])
get_Nearest_Neighbour route = (get_Weight route , route) 
	
{- A variable to store the result of all the above functions so that it can be called easily in the CMD interface to show the user the route that has been generated.-}
result = get_Names (get_Nearest_Neighbour (9:(generate_Best_Route 9 (remove_Duplicate 9 city_Index))++[9]))