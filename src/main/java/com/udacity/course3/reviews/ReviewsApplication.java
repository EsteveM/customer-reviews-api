package com.udacity.course3.reviews;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Set;

@SpringBootApplication
@EnableMongoRepositories
public class ReviewsApplication {

	public static void main(String[] args) {
		// EMG - See the link below to review the first steps with regard to the Flyway API
		// https://flywaydb.org/getstarted/firststeps/api
		// Create the Flyway instance and point it to the database
		// EMG - See the link below to find a way to avoid the time zone issue when creating the
		// Flyway instance and pointing it to the database
		// https://stackoverflow.com/questions/26515700/mysql-jdbc-driver-5-1-33-time-zone-issue
		Flyway flyway = Flyway.configure().dataSource("jdbc:mysql://localhost/PROJECT3?serverTimezone=UTC", "root", "ESTEBAN55").load();

		// Start the migration
		flyway.migrate();

		// EMG - Create the MongoDB reviews collection if it does not already exist
		String uri = "mongodb://project3:project3@localhost:27017/PROJECT3";
		MongoClient mongoClient = MongoClients.create(uri);
		MongoDatabase database = mongoClient.getDatabase("PROJECT3");
		if (collectionExists("reviews", database)) {
			System.err.println("There is no need to create the reviews collection as it already exists.");
		} else {
			database.createCollection("reviews");
		}
		mongoClient.close();
		// EMG - The Spring application is now run, after MySQL Flyway migration (if necessary), and MongoDB collection creation
		// (if necessary)
		SpringApplication.run(ReviewsApplication.class, args);
	}

	// EMG - The current version of MongoDB does not appear to include a "collection exists" method. This code snippet adapted from
	// the DB.java class in https://github.com/mongodb/mongo-java-driver performs this very same functionality. Please, see the
	// link below for more information
	// https://stackoverflow.com/questions/31909247/mongodb-3-java-check-if-collection-exists
	public static boolean collectionExists(final String collectionName, MongoDatabase database) {
		for (final String name : database.listCollectionNames()) {
			if (name.equalsIgnoreCase(collectionName)) {
				return true;
			}
		}
		return false;
	}

	// EMG - To run the tests, the 'mongoTemplate' bean is required. See the link below for more information on
	// how to configure that.
	// https://www.baeldung.com/spring-data-mongodb-tutorial
	// For this reason, it is configured below:
	public class SimpleMongoConfig {

		@Bean
		public MongoClient mongo() {
			String uri = "mongodb://project3:project3@localhost:27017/PROJECT3";
			MongoClient mongoClient = MongoClients.create(uri);
			return mongoClient;
		}

		@Bean
		public MongoTemplate mongoTemplate() throws Exception {
			return new MongoTemplate(mongo(), "PROJECT3");
		}
	}

}