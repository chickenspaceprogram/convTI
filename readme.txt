--------
convTI
--------
A command line utility for converting .txt and .csv files into the format used by certain Texas Instruments calculators.
--------
Version 0.1.x (alpha)
--------
Dependencies:

Java Development Kit (JDK)
 - version 22 was used to create this application, earlier or later versions will likely work as well
Apache Maven 3.6.2
 - older or newer versions probably would also work but you may or may not have to mess with pom.xml a bit.
 - I am pretty new to Maven, if I've done something dumb here please raise an issue on GitHub!
--------
How to compile:

Navigate to the directory of this project in terminal, and enter `mvn package` to compile this project to a .jar file.
You can then run the application by either directly running the .jar file or by using the included shell script.
--------
This application is still incomplete, as is its documentation.
Please raise an issue on GitHub if you encounter any issues or bugs! 
Also, feel free to suggest features/additions.

If you want an *actually good* version of this, check out SourceCoder 3!
This project is more for fun and teaching myself Java than for practical use, if I'm honest.
--------
Creator:

Athena Boose (chickenspaceprogram)
--------
Acknowledgements:
merthsoft, "Link Protocol Guide v1.4"
    - https://merthsoft.com/linkguide/
    - made this project possible, as I'm not willing to spend the long time it would probably take to reverse-engineer TI's file format
KermMartian, "SourceCoder 3"
    - https://sc.cemetech.net
    - inspired this project