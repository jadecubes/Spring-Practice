# Spring-Practice
## Practice Goals
1. Create a Spring Boot 2 web application
2. JDK version >= 1.8
3. Apply Maven to manage the project
4. Appply one of the embedded database
5. Apply one of the ORM framwork
6. Unittest code coverage >= 60%


## User Scinario
1. Develop an API to allow user to add a trasaction a time. Data consistency must be checked.

### Requirements
•	transaction_id: not null, unique

•	point_amount: a possitive number that is >=1 and <=10

•	business_time: not null, not after current time


```
{
    "transaction_id": "ab93e00b5a96bc1a25b5c4a83305316e",
    "point_amount": 10,
    "business_time": "2020-09-22 08:45:07"
}
```
2. Add trsactional API logics by adding values bellow.
### Requirements
•	process_cost: is the multiplication of the point_amount and a random number in between 300 and 500.

•	created_by: data processer's name

•	created_time: current UTC time


## Software Design
in src/main/java/com/example/demo,

```
controller
    +--- DataExchange.java: parse data stuffed in rest api calls
    +--- GlobalExceptionController.java: handles any exceptions caused in this project
    +--- TransactionRestController.java: receives data from rest api calls and calls corresponding data processing apis

dao
  +--- TransactionRepository.java: ORM interface for the repository

entity
  +--- Transaction.java: defines the type saved in repository
 
service
   +--- TransactionService.java: used by business layer to save data 
   +--- TransactionServiceImpl.java: implementation of TransactionService.java
   +--- procession
            +--- Context.java: a processor uses context to dispatch and do tasks in job queues
            +--- GeneralEmpProcessor.java: a type of processor and all GeneralEmpProcessors take tasks from one job queue
            +--- Processor.java: defines a processor's job, state and type
            +--- ProcessorService.java: defines processor's service
            +--- ProcessorServiceImpl.java: implements ProcessorService interface
            +--- SeniorEmpProcessor.java: a type of processor and all SeniorEmpProcessors take tasks from one job queue
            +--- Task.java: saves data from controllers and it's handed over to processors.
            +--- TaskDispatcher.java: dispatches data in all-tasks queue to SeniorEmpProcessor's and GeneralEmProcessor's job queues.
```
## Software Installation
1. IntelliJ IDEA Community Edition 2022.2.3
2. JDK 17
3. Maven 3.8.6
4. Set MAVEN_HOME env variable to be the Maven folder path
5. Postman

## Running Project
1. Pull code in this main branch to your local env
2. Use IntelliJ IDEA to specify the root folder of the pulled code.
```
File > New > Project from Existing Sources...
```
select the pulled code root folder 

3. In Import Project window, select Maven > Create

4. Now the source code and unit tests are imported

5. To run the web application, 
```
select DemoApplication.java in src/main/java/com/example/demo > right click mouse menu > Run 'DemoApplication'
```
6. To run all unit tests,
```
select src/test/java > right click mouse menu > Run 'All Tests' With Coverage'
```
7. After running the web application, use Postman to send post requests to test the web API.

![image](https://github.com/jadecubes/Spring-Practice/blob/main/postman.png)

8. Check H2 in [http://http://localhost:8080/h2-console/](http://localhost:8080/h2-console/)

![image](https://github.com/jadecubes/Spring-Practice/blob/main/h2.png)

## License
[MIT](https://choosealicense.com/licenses/mit/)
