# cacheProject
# CPSC 3300
# December 9, 2019
Cesar Bustamante and Alex Dale

Our cache program is written in Java

Our cache program can be compiled by typing "make". Our cache.java file can be RUN as 
follows: java cache < input file.txt >. To clean up the directory, type "make clean" 
(deletes the compiled program).
Our file takes in an input file, sorts out the cache with the number of 	
sets, set size, and line size and then iteratively reads each read/write
with the address and checks/updates the cache. Our cache uses a LRU replacement 
policy for all blocks in the sets. Functions are used to print to the terminal 
and calculate the final statistics.
