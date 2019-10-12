package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
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
public class ReviewRepositoryTest {

    @Autowired private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private EntityManager entityManager;
    @Autowired private TestEntityManager testEntityManager;

    @Autowired private ProductRepository productRepository;
    @Autowired private ReviewRepository reviewRepository;

    @Test
    public void injectedComponentsAreNotNull(){
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(testEntityManager).isNotNull();
        assertThat(productRepository).isNotNull();
        assertThat(reviewRepository).isNotNull();
    }

    @Test
    // This method tests both save and findById
    public void testSaveReview(){
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
        review.setText("It is excellent. The best buy I have ever made. I have used it for two weeks and it is much better than the previous mice I used.");
        review.setCreatedBy("Jack Ringdale");
        review.setProduct(product);
        // Save the new review, persisting it
        // reviewRepository.save(review);
        entityManager.persist(review);

        // Let's recover the Id of the review that we have just saved, persisting it
        Optional<Review> actual = reviewRepository.findById(review.getId());

        // The review that we have recovered should not be null
        assertThat(actual).isNotNull();
        // The review that we wanted to save must be the review that we have recovered
        // after saving, for the save method to work
        assertThat(review.getId()).isEqualTo(actual.get().getId());
        assertThat(review.getTitle()).isEqualTo(actual.get().getTitle());
        assertThat(review.getText()).isEqualTo(actual.get().getText());
        assertThat(review.getCreatedBy()).isEqualTo(actual.get().getCreatedBy());
        assertThat(review.getProduct()).isEqualTo(actual.get().getProduct());
    }

    @Test
    public void testfindAllByProduct() {
        // Create Product
        Product product = new Product();
        // Set fields
        product.setName("Apple Magic Mouse");
        product.setDescription("This mouse is wireless, and has a long-lasting battery");
        // Save the new product, persisting it
        // productRepository.save(product);
        entityManager.persist(product);


        // Create a first Review associated to the just persisted product
        Review review1 = new Review();
        // Set fields
        review1.setTitle("My review about the Apple Magic Mouse");
        review1.setText("It is excellent. The best buy I have ever made. I have used it for two weeks and it is much better than the previous mice I used.");
        review1.setCreatedBy("Jack Ringdale");
        review1.setProduct(product);
        // Save review1
        entityManager.persist(review1);

        // Create a second Review associated to the just persisted product
        Review review2 = new Review();
        // Set fields
        review2.setTitle("My review about the Apple Magic Mouse");
        review2.setText("I am very dissapointed. The mouse is extremely sensitive, and I have to resort to an HP mouse for the most part.");
        review2.setCreatedBy("John Kensington");
        review2.setProduct(product);
        // Save review2
        entityManager.persist(review2);

        // Store the two new reviews in an ArrayList
        ArrayList<Review> savedReviews =  new ArrayList<Review>();
        savedReviews.add(review1);
        savedReviews.add(review2);

        // Let's find all the reviews (associated to the product) that we have just saved
        ArrayList<Review> actualReviews =  new ArrayList<Review>();
        reviewRepository.findAllByProduct(product).iterator().
                forEachRemaining(actual -> { actualReviews.add(actual); });

        // The reviews that we have found must be the reviews that we saved
        // for the findAllByProduct method to work
        for (int i = 0; i < actualReviews.size(); i++) {
            assertThat(savedReviews.get(i).getId()).isEqualTo(actualReviews.get(i).getId());
            assertThat(savedReviews.get(i).getTitle()).isEqualTo(actualReviews.get(i).getTitle());
            assertThat(savedReviews.get(i).getText()).isEqualTo(actualReviews.get(i).getText());
            assertThat(savedReviews.get(i).getCreatedBy()).isEqualTo(actualReviews.get(i).getCreatedBy());
            assertThat(savedReviews.get(i).getProduct()).isEqualTo(actualReviews.get(i).getProduct());
        }

    }

}
