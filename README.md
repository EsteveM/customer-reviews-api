# Customer Reviews API Project

This project is intended to build the persistence layer for a REST API that supports the customer reviews and comments of a product page in an ecommerce application. The API is built using Java, Spring Boot and Maven, and MySQL provides the underlying database. The API is also tested using Spring Data JPA Tests.

## Table of Contents

* [Description of the Project](#description-of-the-project)
* [Testing coverage](#testing-coverage)
* [Getting Started](#getting-started)
* [References](#references)
* [Contributing](#contributing)

## Description of the Project

As has already been mentioned, this project develops a REST API that supports the customer reviews and comments of a product page in an ecommerce application. The API supports writing and listing of products, reviews, and comments, but no sorting or filtering. The work that has been done is best described by explaining its main steps:

* MySQL has been setup, a DB created, and connectivity verfied.
* SQL has been written to create the corresponding tables.
* To run the above SQL, DB migration has been written.
* Classes for required JPA Entities (Product, Review, and Comment) have been written, and the relationships between them identified.
* Required Spring Data JPA Repositories (ProductRepository, ReviewRepository, and CommentRepository) have been defined.
* Spring REST controller methods have been implemented within ProductsController, ReviewsController, and CommentsController.
* Tests for the repositories have been written against an in-memory database (H2): ReviewsApplicationTests, ProductRepositoryTest, ReviewRepositoryTest, and CommentRepositoryTest.

## Testing coverage

In this section, the ten tests covered are listed:

* ReviewsApplicationTests:
	* contextLoads: tests context loading.
* ProductRepositoryTest:
	* injectedComponentsAreNotNull: tests that the injected components are not null.
	* testSaveProduct: tests that the save method of the ProductRepository works.
	* testFindAllProducts: tests that the findAll method of the ProductRepository works.
* ReviewRepositoryTest:
	* injectedComponentsAreNotNull: tests that the injected components are not null.
	* testSaveReview: tests that the save method of the ReviewRepository works.
	* testFindAllByProduct: tests that the findAllByProduct method of the ReviewRepository works.
* CommentRepositoryTest:
	* injectedComponentsAreNotNull: tests that the injected components are not null.
	* testSaveComment: tests that the save method of the CommentRepository works.
	* testFindAllByReview: tests that the findAllByReview method of the CommentRepository works.

## Getting Started

The procedure to obtain a functional a copy of the project on your local machine so that you can further develop and/or test it is explained in this section. These are the steps to be followed:

* Firstly, you have to download/clone the project files from this repository onto your local machine. Then, cd into the root folder where the project files are located.
* For your information, this is the result of the execution of the packaging step for the P03-ReviewsAPI application:
![reviewsapipackage](/ScreenShots/reviewsapipackage.png)
* This step creates this *jar* file: *reviews-0.0.1-SNAPSHOT.jar*.
* Now, secondly, you can execute the packaged application. Just run the *jar* file on a terminal shell window by typing `java -jar target/reviews-0.0.1-SNAPSHOT.jar`:
	* The first time you run the *jar* file, the initial database migration creates the application tables:
	![jar1](/ScreenShots/jar1.png)
	* This can also be seen on the MySQL shell:
	![jar3](/ScreenShots/jar3.png)
	* The reviews API server is started on port 8080:
	![jar2](/ScreenShots/jar2.png)
* Thirdly, the Reviews API can be manually tested executing a number of POSTMAN requests:
	* The Reviews API is able to create a new product based on input from the user with a POST request:
	![postman1](/ScreenShots/postman1.png)
	* Let's see the PRODUCTS table on MySQL:
	![mysql1](/ScreenShots/mysql1.png)
	* The Reviews API can receive GET requests from a user, and read back an existing product by Id, or a list of all existing products:
	![postman2](/ScreenShots/postman2.png)
	![postman3](/ScreenShots/postman3.png)
	* The Reviews API is able to create a new review for a product based on input from the user with a POST request:
	![postman4](/ScreenShots/postman4.png)
	* Let's see the REVIEWS table on MySQL:
	![mysql2](/ScreenShots/mysql2.png)
	* The Reviews API can receive GET requests from a user, and read back a list of all existing reviews by product:
	![postman5](/ScreenShots/postman5.png)
	* The Reviews API is able to create a new comment for a review of a product based on input from the user with a POST request:
	![postman6](/ScreenShots/postman6.png)
	* Let's see the COMMENTS tabLe on MySQL:
	![mysql3](/ScreenShots/mysql3.png)
	* The Reviews API can receive GET requests from a user, and read back a list of all existing comments by review:
	![postman7](/ScreenShots/postman7.png)
* In the fourth place, if you want to run the supporting tests yourself, you have to:
	* Make sure the application is running. If you have followed along, this should be the case now.
	* Open a new terminal shell window, cd to the P03-ReviewsAPI folder, and type, for instance, `mvn test`. Please, note how all tests pass:
	![tests1](/ScreenShots/tests1.png)
	![tests2](/ScreenShots/tests2.png)

# References 

Please, consider this resources for further information:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

## Contributing

This repository contains all the work that makes up the project. Individuals and I myself are encouraged to further improve this project. As a result, I will be more than happy to consider any pull requests.