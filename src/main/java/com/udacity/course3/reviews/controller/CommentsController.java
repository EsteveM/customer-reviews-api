package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.CommentMongoDB;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.ReviewMongoDB;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewMongoDBRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    // EMG - Wire needed JPA repositories here
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    CommentRepository commentRepository;
    // EMG - Wire MongoDB repository here
    @Autowired
    ReviewMongoDBRepository reviewMongoDBRepository;

    /**
     * Creates a comment for a review.
     *
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, save comment.
     *
     * @param reviewId The id of the review.
     */
    // EMG - We add @Valid annotation to @RequestBody to invoke automatic validation.
    // See https://dzone.com/articles/spring-31-valid-requestbody
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") Integer reviewId, @Valid @RequestBody Comment comment) {
        Optional<Review> reviewRead = reviewRepository.findById(reviewId);
        if (reviewRead.isPresent()) {
            // For a review of the get() method of Optional, see the link below:
            // https://www.baeldung.com/java-optional
            comment.setReview(reviewRead.get());
            // Firstly, save the comment for the review in MySQL
            commentRepository.save(comment);
            // Secondly, save the comment for the review in MongoDB
            Optional<ReviewMongoDB> reviewMongoDB = reviewMongoDBRepository.findById(reviewRead.get().getId());
            CommentMongoDB commentMongoDB = new CommentMongoDB();
            commentMongoDB.setId(comment.getId());
            commentMongoDB.setTitle(comment.getTitle());
            commentMongoDB.setText(comment.getText());
            commentMongoDB.setCreatedBy(comment.getCreatedBy());
            List<CommentMongoDB> reviewComments = reviewMongoDB.get().getComments();
            reviewComments.add(commentMongoDB);
            reviewMongoDB.get().setComments(reviewComments);

            return new ResponseEntity<>(reviewMongoDBRepository.save(reviewMongoDB.get()), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        Optional<Review> reviewRead = reviewRepository.findById(reviewId);
        if (reviewRead.isPresent()) {
            return new ResponseEntity<>(commentRepository.findAllByReview(reviewRead.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}