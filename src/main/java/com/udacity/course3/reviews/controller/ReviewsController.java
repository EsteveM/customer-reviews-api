package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.ReviewMongoDB;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewMongoDBRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

    // EMG - Wire JPA repositories here
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ReviewRepository reviewRepository;
    // EMG - Wire MongoDB repository here
    @Autowired
    ReviewMongoDBRepository reviewMongoDBRepository;

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Integer productId, @Valid @RequestBody Review review) {
        Optional<Product> productRead = productRepository.findById(productId);
        if (productRead.isPresent()) {
            // For a review of the get() method of Optional, see the link below:
            // https://www.baeldung.com/java-optional
            review.setProduct(productRead.get());
            // Firstly, save the review in MySQL
            reviewRepository.save(review);
            // Secondly, save the review in MongoDB
            ReviewMongoDB reviewMongoDB = new ReviewMongoDB();
            reviewMongoDB.setId(review.getId());
            reviewMongoDB.setTitle(review.getTitle());
            reviewMongoDB.setText(review.getText());
            reviewMongoDB.setCreatedBy(review.getCreatedBy());
            reviewMongoDB.setComments(new ArrayList<>());

            return new ResponseEntity<>(reviewMongoDBRepository.save(reviewMongoDB), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        Optional<Product> productRead = productRepository.findById(productId);
        if (productRead.isPresent()) {
            // return new ResponseEntity<>(reviewRepository.findAllByProduct(productRead.get()), HttpStatus.OK);
            // EMG - When loading reviews for a product, the ids are read from MySQL, and the review document from
            // MongoDB
            ArrayList<Optional<ReviewMongoDB>> reviews = new ArrayList<>();
            reviewRepository.findAllIdsByProduct(productRead.get().getId())
                    .iterator().
                    forEachRemaining(id -> { reviews.add(reviewMongoDBRepository.findById(id)); });
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}