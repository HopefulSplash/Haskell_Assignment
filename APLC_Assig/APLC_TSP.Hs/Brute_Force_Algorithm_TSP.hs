{-  
    The Brute_Force_Algorithm_TSP.Hs implements an application using a
	brute force algorithm to produce a route for the travelling salesman problem.
	
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
city_Index = [0 .. 8] :: [Int];		

{- A series of functions that will get all the different possible permutations of the numbers 0-8 each all the different routes you can take from STOKE-ON-TRENT
   by inserting and joining each element in the list in all possible ways.
-}
permutate_City :: [Int] -> [[Int]]
permutate_City [] =  [[]]
permutate_City (x:xs) = concat [insert x ys | ys <- permutate_City xs]
insert :: Int -> [Int] -> [[Int]]
insert x [] = [[x]]
insert x (y:ys) = (x:y:ys) : consall y (insert x ys)
consall y [] = []
consall y (xs:xss) = (y:xs) : consall y xss

{-
   A function that will take the list of permutations for the different routes you can take from STOKE-ON-TRENT and then return back to it, then it then it will add
   9 to the start of each element so that they can be modified into co-ordinates to be used on the distance_Matrix.
-}
get_Permutations :: [[Int]] -> [(Double, [Int] )]
get_Permutations [[]] =  error "Empty List"
get_Permutations xs =    [(get_Weight ( 9:x++[9]) , (9:x++[9])) | x <- xs ]

{-
   A function that will get the take a list of Ints and zip them together so that they can be used as co-ordinates for use on the distance_Matrix
   to get the value that represents the distance from x to y (one city to another).
-}
get_Weight :: [Int] -> Double
get_Weight [] = error "Empty List"
get_Weight (x:xs) = generate_Weight $ zipWith (\c1 c2 -> distance_Matrix !! c1 !! c2) (x:xs)(xs)
 	
{-
   A function that take a list of double values and add them all up to give you a total, in this case it will add all the different stops
   on a route up to give you the total weight for that specific route.
-}
generate_Weight :: [Double] -> Double
generate_Weight []     = 0
generate_Weight (x:[]) = x
generate_Weight (x:xs) = x + generate_Weight xs

{-
   A function that is used to et the route with the smallest weight (best) it does this by compare one element against the next using an if
   statement that will return the smallest route once it has compared all the values in the object passed into it.
-}
get_Min_Route :: (Ord a) => [a] -> a 
get_Min_Route [] = error "Empty List"
get_Min_Route [x] = x  
get_Min_Route (x:y:xs) = if x < y then get_Min_Route (x:xs) 
							  else get_Min_Route (y:xs)
							  
{- 
   A function that is covert a list of Int type into the names of each city so that it is more user friendly by taking a tuple and adding a 
   list of list of chars (String) that will contain the names this is done by using the Ints as an index for name_Array.
-}
get_Names :: (Double, [Int]) -> (Double, [Int], [[Char]])
get_Names (nil , []) = error "Empty List"
get_Names data_Tuple = (fst data_Tuple , snd data_Tuple , [name_Array !! n | n <- (snd data_Tuple)])

{- A variable to store the result of all the above functions so that it can be called easily in the CMD interface to show the user the route that has been generated.-}
result = get_Names (get_Min_Route (get_Permutations (permutate_City city_Index)))