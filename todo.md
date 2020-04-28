#Things That Need To Be Done

##TransPool Trips:
 - Calculate the calculatable fields in TransPoolTrip using the paths in the map
    - PPK (Using fuel consumption)
    - Fuel consumption (Using max speed of path, length and some constant of rate of consumption)
    - Time of arrival (including minutes)
    - Total price (PPK * path length)
    - *Show all riders (inc. IDs) who have been assigned to the trip.
    - **List of stops of the TransPool trip. Each member of that list should contain the stop name, 
      and the rider getting on/off (Maybe make it a pair of stop and a list of riders? will be constructed
      from the tripRequest?)
 
##Engine:
 - Check if the file uploaded is valid application wise (paths, stops rides etc.)
    - Size of map needs to be between 6 and 100 (both width and length)
    - Every stop has a unique name and coordinates.
    - There cannot be 2 stops with the same coordinates.
    - Every stop is in the map's boundaries.
    - Every path is constructed of defined stops.
    - *Every TransPool ride goes through defined paths and stops.
 
 ##Matching
   - Create the matching menu.
   - Search for a match.
 
##Exceptions
   - Move exceptions out of system and re-order the package structure.
   - Throw all exceptions to the UI.