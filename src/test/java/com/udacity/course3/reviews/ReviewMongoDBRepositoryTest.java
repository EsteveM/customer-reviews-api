package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.CommentMongoDB;
import com.udacity.course3.reviews.entity.ReviewMongoDB;
import com.udacity.course3.reviews.repository.ReviewMongoDBRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ReviewMongoDBRepositoryTest {

    @Autowired MongoTemplate mongoTemplate;
    @Autowired ReviewMongoDBRepository reviewMongoDBRepository;

    @Test
    public void injectedComponentsAreNotNull(){
        assertThat(mongoTemplate).isNotNull();
        assertThat(reviewMongoDBRepository).isNotNull();
    }

    @Test
    public void MongoDBSpringIntegrationTest() {

        // GIVEN OBJECT TO SAVE

        // Create ReviewMongoDB
        ReviewMongoDB reviewMongoDB = new ReviewMongoDB();
        // Set fields
        reviewMongoDB.setId(1);
        reviewMongoDB.setTitle("My review about the Apple Magic Mouse");
        reviewMongoDB.setText("I am very dissapointed. The mouse is extremely sensitive.");
        reviewMongoDB.setCreatedBy("John Kensington");

        // Create first CommentMongoDB
        CommentMongoDB commentMongoDB1 = new CommentMongoDB();
        commentMongoDB1.setId(1);
        commentMongoDB1.setTitle("My comment to your review about the Apple Magic Mouse");
        commentMongoDB1.setText("I cannot understand why you say you are so disappointed. So far, this mouse has proved to be the best of the market.");
        commentMongoDB1.setCreatedBy("Karen Peterson");

        // Create second CommentMongoDB
        CommentMongoDB commentMongoDB2 = new CommentMongoDB();
        commentMongoDB2.setId(2);
        commentMongoDB2.setTitle("Great review John about the Apple Magic Mouse");
        commentMongoDB2.setText("Indeed the mouse is far too sensitive. It's not up to Apple standards.");
        commentMongoDB2.setCreatedBy("Jeremy Lee");

        // Add both CommentMongoDB to ReviewMongoDB
        List<CommentMongoDB> reviewComments = new ArrayList<>();
        reviewComments.add(commentMongoDB1);
        reviewComments.add(commentMongoDB2);
        reviewMongoDB.setComments(reviewComments);

        // WHEN
        mongoTemplate.save(reviewMongoDB, "reviews");

        // THEN
        // Let's recover the Id of the ReviewMongoDB that we have just saved, persisting it
        Optional<ReviewMongoDB> actual = reviewMongoDBRepository.findById(reviewMongoDB.getId());

        // The ReviewMongoDB that we have recovered should not be null
        assertThat(actual).isNotNull();
        // The ReviewMongoDB that we wanted to save must be the ReviewMongoDB that we have recovered
        // after saving, for the save method to work
        // Firstly, the four first properties are examined
        assertThat(reviewMongoDB.getId()).isEqualTo(actual.get().getId());
        assertThat(reviewMongoDB.getTitle()).isEqualTo(actual.get().getTitle());
        assertThat(reviewMongoDB.getText()).isEqualTo(actual.get().getText());
        assertThat(reviewMongoDB.getCreatedBy()).isEqualTo(actual.get().getCreatedBy());

        // Secondly, the fifth property (the comments) are examined by the same token
        ArrayList<CommentMongoDB> actualReviewComments =  new ArrayList<CommentMongoDB>();
        actual.get().getComments().iterator().
                forEachRemaining(actualComment -> { actualReviewComments.add(actualComment); });

        for (int i = 0; i < actualReviewComments.size(); i++) {
            assertThat(reviewComments.get(i).getId()).isEqualTo(actualReviewComments.get(i).getId());
            assertThat(reviewComments.get(i).getTitle()).isEqualTo(actualReviewComments.get(i).getTitle());
            assertThat(reviewComments.get(i).getText()).isEqualTo(actualReviewComments.get(i).getText());
            assertThat(reviewComments.get(i).getCreatedBy()).isEqualTo(actualReviewComments.get(i).getCreatedBy());
        }
    }
}
