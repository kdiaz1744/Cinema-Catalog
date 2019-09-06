# Instructions on this project

This project was developed in the IntelliJ IDEA 2019.1 environment. To run it there, you must go to the top part and click on
"Add Configurations", after that just add a new configuration and choose the Maven configuration. Specify the directory of the project folder
and the command line "package jetty::run". Now just run the project and open [http://localhost:8080](http://localhost:8080) in browser.

As I understand it, to run it in Eclipse, you must download and install the Maven pluggings necessary, add in the configurations the
the base directory of the project, and enter the "Goal" as "clean install", after which you click "Apply" and "Run". 

The line below was what appeared in the site when asked how to run this type of project:

Run using `mvn jetty:run` and open [http://localhost:8080](http://localhost:8080) in browser.

This project was originally a "Starting Point" project downloaded from the Vaading site (the site that holds the framework). 
By downloading it from there, it possessed the necessary files and dependencies for starting the  web app.

Vaadin is a framework used specifically to facilitate the making of a webpage. If you want to download a Starting Point project, go to [vaadin.com/start](https://vaadin.com/start) - 
you can also go to [getting started tutorial](https://vaadin.com/docs/v10/flow/introduction/tutorial-get-started.html) for a tutorial on it.


