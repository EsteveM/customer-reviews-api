package com.udacity.course3.reviews;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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

		SpringApplication.run(ReviewsApplication.class, args);
	}

}