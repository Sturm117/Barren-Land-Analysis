## Project: Barren Land Analysis

Java application which accepts a set of rectangles, as text from STDIN in the form of four integers representing the rectangles coordinates, and analyzes the farm field in order to determine the areas of fertile land.  The farm field is 400m by 600m with coordinates from (0,0) to (399,599).  The areas of fertile land are output in sorted order, smallest to largest, as square meters.

#### Running

The app was built and tested using java version 1.8.0_191 and may require jre 1.8.0_191 to execute.  Retrieve the 'BarrenLandAnalysis.jar' file.  Using a terminal, go to the location where the jar file was saved and execute the following command: 
	java -jar BarrenLandAnalysis.jar

The application expects input from STDIN in order to specify a set of rectangle coordinates for any barren land present on the farm field.  The input should be a single set of rectangles containing all barren land inside of curly brackets.  Each rectangle should be four integers sepearted by spaces and in between double quotes.  The first two integers of each rectangle represent coordinates for the bottom left corner and the last two represent coordinates for the top right corner.

The application will then display the fertile land areas in square meters, sorted from smallest to largest area.

#### Testing

The application is designed to accept input from STDIN, however it will also check the first argument passed in on the command line for automated testing purposes.  This feature allows a script to be written which will pass in the set of rectangles representing the barren land and execute normally.

There is a batch file for use on Windows operating systems, which has a set of tests that can be run and send the result of each test to an individual file.  There is also a set of golden files for each test which were all manually verified for accuracy.  In order for the batch file to work correctly, the file should be in the same location as the Barren Land Analysis application and requires a directory named 'testing' one level down.  The testing directory should contain the golden files for the tests.

