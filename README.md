# library-management-system

<div id="top"></div>
<div align="center">


<a href="![image](https://github.com/Ranajaafar/library-management-system/assets/110610925/45386b7a-0c39-424b-87bc-e6f2a566d760)
">
<img src="https://github-production-user-asset-6210df.s3.amazonaws.com/110610925/256531960-440c9a4f-7894-4d84-bfd2-4ccec41d48a8.png" alt="Logo" width="80" height="80">
</a>
<h3 align="center">README for <b> the Task </b> </h3>
 </div> 

# About The Project
This project has been developed with the primary objective of fulfilling the task of my assessment program.
Build a Library Management System API using Spring Boot. The system should allow librarians to manage books, patrons, and borrowing records.
I have built few API (rest endpoint) for user that sends an HTTP request and will be returned with an HTTP response

![LibraryManagementSystem-ERD](https://github.com/Ranajaafar/library-management-system/assets/110610925/6cba2d4b-2145-480e-b9a9-13249df92c1e)
![LibraryManagementSystem_PDM](https://github.com/Ranajaafar/library-management-system/assets/110610925/95ad7607-26cf-42e9-98fb-8adb251863a9)

<br/>I used power Designer to design the ER diagram that helped me with the creation of database and the generation of physical data diagram.
<br/>
An Entity Relationship (ER) Diagram is a type of flowchart that illustrates how “entities” such as people, objects or concepts relate to  each other within a system.<br/>
PDM is a database-specific model that represents relational data objects and their relationships.<br/>
Implementing the physical data model is a straightforward process once the ER diagram has been designed. In this process:
* Each entity in the ER diagram is mapped to a table in the database. Each table should have a primary key.
* The relationships between entities in the ER diagram are translated into fields in the corresponding tables. In some cases, a new table may be created to represent relationships, depending on the cardinality and nature of the relationship.

<br/>
In the ER diagram we have 4 entities, each entity has its own relations:<br/> 
<b>Each patron can borrow multiple books, and each book can be borrowed multiple times. However, each book can only be borrowed by one patron at a time.
A librarian, who has access to manage the library</b>
<br/>

![image](https://github.com/Ranajaafar/library-management-system/assets/110610925/36c81dbb-c8aa-4cbe-837b-447768f05d9c)
I have implemented the widely-used three-layer architecture in this Spring Boot application:
1. Controller Layer: This layer is responsible for defining all the REST API endpoints, and it injects services into the controller classes.
2. Service Layer: The service layer is where we manage all the business logic, and it injects repositories into service classes.
3. Repository Layer: This layer is responsible for communication with the database, and all database transactions are managed by Hibernate, which is an implementation of Spring Data JPA."
   <br/><br/><br/><br/>

![image](https://github.com/Ranajaafar/library-management-system/assets/110610925/817b85c1-a836-414b-9f6d-ebc6110e1afb)

Utilize Spring's caching mechanisms to cache frequently accessed data, such as book details or patron information, to improve system performance.
so i used it to cache all patrons and, books and specific entry by id.
I have also integrated Redis as a cache to enhance the application's performance. When we perform a database retrieval operation through the application, Redis caches the results. Subsequently, when the same retrieval operation is repeated, Redis returns the result from its cache, avoiding the need for a second database call. You can observe the usage of Redis cache by examining the project's logs, as demonstrated below.
![image](https://github.com/Ranajaafar/library-management-system/assets/110610925/7273e28f-aead-4e34-b54d-fd2ddcbe501d)

This photo shows that we requested to retrieve all patrons twice. The first time, it fetched them from the database, and the second time, it didn't access the database; instead, it used the cache.
<br/>

![image](https://github.com/Ranajaafar/library-management-system/assets/110610925/c0374365-6804-4cec-b678-9f027204809a)
I have Develop a Spring Boot Application to expose a Simple REST GET API.
I Configured Spring Security for JWT. Expose REST POST API with mapping api/librarian/auth/sign-in using which Librarian will get a valid JSON Web Token. And then allow the librarian access to the api api/librarian/auth/** only if it has a valid token

I have configured Spring Security and JWT for performing 2 operations:

1. Generating JWT - Expose a GET API with mapping /authenticate. On passing correct username and password it will generate a JSON Web Token(JWT)
1. Validating JWT - If user tries to access GET API with mapping api/v1/**. It will allow access only if request has a valid JSON Web Token(JWT)

Firstly, if the librarian does not already have an account, they will need to sign up using a specific username and password. After signing up, they can use these credentials to sign in, which will return a valid token.In my application, authentication is configured to accept only the credentials of registered librarians.
<br/>
First, the librarian registers with a specific username and password. Input validation is applied for this API requests.
![image](https://github.com/Ranajaafar/library-management-system/assets/110610925/d43cdd51-d56b-4dec-83d5-6407625ee7df)
<br/><br/>
Then, the librarian authenticates. If the credentials are valid, a token will be generated. This token can be used to access other APIs.
![image](https://github.com/Ranajaafar/library-management-system/assets/110610925/a2b96bd6-2adf-4a3b-be90-87bc94071a3b)


I Implemented unit tests for the API endpoints. so i mock the service and API.
![image](https://github.com/Ranajaafar/library-management-system/assets/110610925/1570d0e9-ee5c-4d0f-af4e-0f488ceb3cd6)
<br/>Before running the tests, you need to modify the .properties file to update the database connection and Redis configuration.
now you could right click on test/java/maids.cc.librarymanagementsystem and run tests with coverage

![image](https://github.com/Ranajaafar/library-management-system/assets/110610925/4590114c-92ec-489d-bb09-1efcdcfd1e15)
![image](https://github.com/Ranajaafar/library-management-system/assets/110610925/17391355-5764-41c3-be33-5565221641db)


<br/>

![image](https://github.com/Ranajaafar/library-management-system/assets/110610925/63be66a0-c2f6-4a45-b43a-4807aae5c261)
Implement logging using Aspect-Oriented Programming (AOP) to log method calls, exceptions, and performance metrics
<br/>
<h3> <b> Requirements: </b> </h3>


you are in need for those software:<br/>
1 **PostgreSQL** <br/>
2 **JDK 17**  
3 **Intellij IDEA (IDE)**  
4 **Gradle**  
5 **Redis**


Note: Before proceeding, please ensure that no other processes are currently running on port 8080 . If any processes are occupying port 8080, you should either terminate them or consider changing the port from 8080 to an available alternative.
You can manage the port configurations in the 'application.properties' file located in the 'src/main/resources' directory.
and add
```
server.port=8086
```

   <br/>     
You can use Docker to initialize instances of PostgreSQL and Redis. First, you need to have Docker installed. I have prepared a docker-compose file for you to simplify the creation of these instances. Using this file, Docker will create an image and a container, which is a running instance of that image.

![image](https://github.com/Ranajaafar/library-management-system/assets/110610925/8a2ba327-38f6-473b-9136-951257f501d7)

<br/> You can click on the arrow sign in the services section to create instances of both PostgreSQL and Redis. Alternatively, you can click on the arrow sign next to Redis or PostgreSQL specifically to create an instance of that particular image
<br/>
or  Visit the link to download Redis Server on Windows: https://github.com/microsoftarchive/redis/releases/tag/win-3.2.100
<br/>
<br/>

<h3> steps: </h3>
this is an example of how you can instruct your audience on installing and setting up your app

1. download the above requirements please don't forget to setup the system environment variables
2. download the project from github to your desktop by cloning it

   ```sh
     git clone https://github.com/Ranajaafar/library-management-system.git
   ```
3. open the project in intellij idea
4. this Step you must do it to make the application work properly :
   access `src/main/resources/application.properties` and  <b>update the database connection and Redis configuration</b> such as (spring.datasource.username and spring.data.redis.port)<br/>
   You need to create a database in PostgreSQL. You can connect to PostgreSQL using any UI tool(pgAdmin) or via the console. Please <b>create a database named 'librarymanagementsystemdb' </b>. Personally, I prefer using the embedded database UI provided by IntelliJ for creating the database.

![image](https://github.com/Ranajaafar/library-management-system/assets/110610925/b4e83f88-f292-4cfd-88aa-7c1248b3e20b)

<br/>
  5. finally you can run the project.
       When the project is run, the application initializes the database by creating both tthe necessary tables using Hibernate, the underlying database engine.
       Now we have a Tomcat web servers up and running, listening on specific port, ready to receive requests.
  6. in the browser access http://localhost:8080/swagger-ui.html

since we have springdoc openapi dependency in build.gradle and try all the api
{Springdoc OpenAPI is an open-source library that provides support for generating OpenAPI (formerly known as Swagger) documentation for Spring Boot applications. OpenAPI is a specification for defining APIs, and it is often used to document and describe RESTful APIs.}

![image](https://github.com/Ranajaafar/library-management-system/assets/110610925/5aa918ae-7f95-4de1-8769-65e6251d0bb0)

First, sign in with a specific username and password. If you don't have an account, register (sign up) first. Use the JWT token obtained from signing in to access other APIs. In Swagger, click 'Authorize' and enter your JWT token.
![image](https://github.com/Ranajaafar/library-management-system/assets/110610925/63bec41b-d810-4cdb-b528-f6f0f843e3de)

and put the jwtToken in the value field nd  click authorize ( you can now send any request and try the apis)
<p align="right">(<a href="#top">back to top</a>)</p>

