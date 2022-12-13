Project Title

This application helps you out to calculate your expenses.

Prerequisites

What things you need to install in advance:

      •	Java 8

      •	Maven
      
Maven is used to fetch a few dependencies (json, gson), described in the bottom of pom.xml. If you use Eclipse, Maven might already be pre-installed. 

Run the application locally

1.	Clone this repository 

2.	Run the following:

         •	mvn compile

         •	mvn exec:java -Dexec.mainClass="ua.karatnyk.App"
         
or just simply run app.bat

Run JUnit tests

Run the following:

       •	mvn clean test


