# tm_assessment

Assuming you have the environment installed in your machine
1. Java 14
2. NPM latest+
3. Angular 10+
4. MySQL


There are multiple folder into this repository, name as (be) Java back-end program whereas (fe) is the Angular front-end program whereby the cvs_files and load_data(this folder will create when running the Java program). There are few setup need to be done in order to launch the system. Follow the step below:

1. Place the entire solution into your computer desktop, there are 2 area need to configure in application.yml file with this # comment
1.1. configure the import-path: according of your desktop directory by default should be working  note* if the load_data folder not created it mean the path not configure correctly.
1.2. configure the datasource to match with your MySQL connection string #

2. To launch the back-end Java program under the be$ path execute command below or run in IDE
   $ mvn clean install
   $ mvn spring-boot:run

3. After the back-end is up and running you can start to simulate the scenario copy the .csv files into load_data folder, the back-end will trigger and you can check the data are loaded into database.

4. To insert a task from API level, use your preference REST API tool e.g. Postman to send the payload below and endpoint is http://localhost:8080/api/assignment/save
    {
        "task": "TASK_88",
        "skill": "SKILL_1"
    }

5. Lastly launch the front-end Angular program(client) under the fe$ execute command below:
   $ ng s -o

-- That's all -- 


