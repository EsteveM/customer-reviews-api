package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {

    // EMG - Declaration of a new method. Spring Data derives the implementation of the method by its name.
    List<Review> findAllByProduct(Product product);

    @Query("select r.id from Review r where r.product.id=:id")
    List<Integer> findAllIdsByProduct(Integer id);
}