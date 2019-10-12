package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;

import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private EntityManager entityManager;
    @Autowired private TestEntityManager testEntityManager;

    @Autowired private ProductRepository productRepository;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private CommentRepository commentRepository;

    @Test
    public void injectedComponentsAreNotNull(){
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(testEntityManager).isNotNull();
        assertThat(productRepository).isNotNull();
        assertThat(reviewRepository).isNotNull();
        assertThat(commentRepository).isNotNull();
    }

    @Test
    // This method tests both save and findById
    public void testSaveComment(){
        // Create Product
        Product product = new Product();
        // Set fields
        product.setName("Apple Magic Mouse");
        product.setDescription("This mouse is wireless, and has a long-lasting battery");
        // Save the new product, persisting it
        // productRepository.save(product);
        entityManager.persist(product);

        // Create Review associated to the just persisted product
        Review review = new Review();
        // Set fields
        review.setTitle("My review about the Apple Magic Mouse");
        review.setText("I am very disappointed. The mouse is extremely sensitive.");
        review.setCreatedBy("John Kensington");
        review.setProduct(product);
        // Save the new review, persisting it
        // reviewRepository.save(review);
        entityManager.persist(review);

        // Create Comment associated to the just persisted review
        Comment comment = new Comment();
        // Set fields
        comment.setTitle("My comment to your review about the Apple Magic Mouse");
        comment.setText("I cannot understand why you say you are so disappointed. So far, this mouse has proved to be the best of the market.");
        comment.setCreatedBy("Karen Peterson");
        comment.setReview(review);
        // Save the new comment, persisting it
        // reviewRepository.save(comment);
        entityManager.persist(comment);


        // Let's recover the Id of the comment that we have just saved, persisting it
        Optional<Comment> actual = commentRepository.findById(comment.getId());

        // The comment that we have recovered should not be null
        assertThat(actual).isNotNull();
        // The comment that we wanted to save must be the review that we have recovered
        // after saving, for the save method to work
        assertThat(comment.getId()).isEqualTo(actual.get().getId());
        assertThat(comment.getTitle()).isEqualTo(actual.get().getTitle());
        assertThat(comment.getText()).isEqualTo(actual.get().getText());
        assertThat(comment.getCreatedBy()).isEqualTo(actual.get().getCreatedBy());
        assertThat(comment.getReview()).isEqualTo(actual.get().getReview());
    }

    @Test
    public void testfindAllByReview() {
        // Create Product
        Product product = new Product();
        // Set fields
        product.setName("Apple Magic Mouse");
        product.setDescription("This mouse is wireless, and has a long-lasting battery");
        // Save the new product, persisting it
        // productRepository.save(product);
        entityManager.persist(product);


        // Create Review associated to the just persisted product
        Review review = new Review();
        // Set fields
        review.setTitle("My review about the Apple Magic Mouse");
        review.setText("I am very disappointed. The mouse is extremely sensitive.");
        review.setCreatedBy("John Kensington");
        review.setProduct(product);
        // Save the new review, persisting it
        // reviewRepository.save(review);
        entityManager.persist(review);

        // Create a first Comment associated to the just persisted review
        Comment comment1 = new Comment();
        // Set fields
        comment1.setTitle("My comment to your review about the Apple Magic Mouse");
        comment1.setText("I cannot understand why you say you are so disappointed. So far, this mouse has proved to be the best of the market.");
        comment1.setCreatedBy("John Kensington");
        comment1.setReview(review);
        // Save review2
        entityManager.persist(comment1);

        // Create a second Comment associated to the just persisted review
        Comment comment2 = new Comment();
        // Set fields
        comment2.setTitle("Great review John about the Apple Magic Mouse");
        comment2.setText("Indeed the mouse is far too sensitive. It's not up to Apple standards.");
        comment2.setCreatedBy("Jeremy Lee");
        comment2.setReview(review);
        // Save review2
        entityManager.persist(comment2);

        // Store the two new comments in an ArrayList
        ArrayList<Comment> savedComments =  new ArrayList<Comment>();
        savedComments.add(comment1);
        savedComments.add(comment2);

        // Let's find all the comments (associated to the review) that we have just saved
        ArrayList<Comment> actualComments =  new ArrayList<Comment>();
        commentRepository.findAllByReview(review).iterator().
                forEachRemaining(actual -> { actualComments.add(actual); });

        // The comments that we have found must be the comments that we saved
        // for the findAllByReview method to work
        for (int i = 0; i < actualComments.size(); i++) {
            assertThat(savedComments.get(i).getId()).isEqualTo(actualComments.get(i).getId());
            assertThat(savedComments.get(i).getTitle()).isEqualTo(actualComments.get(i).getTitle());
            assertThat(savedComments.get(i).getText()).isEqualTo(actualComments.get(i).getText());
            assertThat(savedComments.get(i).getCreatedBy()).isEqualTo(actualComments.get(i).getCreatedBy());
            assertThat(savedComments.get(i).getReview()).isEqualTo(actualComments.get(i).getReview());
        }

    }

}