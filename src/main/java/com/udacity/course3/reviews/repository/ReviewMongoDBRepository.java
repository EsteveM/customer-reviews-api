package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.ReviewMongoDB;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewMongoDBRepository extends MongoRepository<ReviewMongoDB, Integer> {
}
