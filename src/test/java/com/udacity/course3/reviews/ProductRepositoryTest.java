package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.repository.ProductRepository;

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
public class ProductRepositoryTest {

    @Autowired private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private EntityManager entityManager;
    @Autowired private TestEntityManager testEntityManager;

    @Autowired private ProductRepository productRepository;

    @Test
    public void injectedComponentsAreNotNull(){
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(testEntityManager).isNotNull();
        assertThat(productRepository).isNotNull();
    }

    @Test
    // This method tests both save and findById
    public void testSaveProduct(){
        // Create Product
        Product product = new Product();
        // Set fields
        product.setName("Samsung Galaxy Mobile Phone");
        product.setDescription("Small mobile phone powered by Android Kit-kat");

        // Save the new product, persisting it
        // productRepository.save(product);
        entityManager.persist(product);
        // Let's recover the Id of the product that we have just saved, persisting it
        Optional<Product> actual = productRepository.findById(product.getId());

        // The product that we have recovered should not be null
        assertThat(actual).isNotNull();
        // The product that we wanted to save must be the product that we have recovered
        // after saving, for the save method to work
        assertThat(product.getId()).isEqualTo(actual.get().getId());
        assertThat(product.getName()).isEqualTo(actual.get().getName());
        assertThat(product.getDescription()).isEqualTo(actual.get().getDescription());
    }

    @Test
    public void testFindAllProducts(){
        // Create a first Product
        Product product1 = new Product();
        // Set fields
        product1.setName("Samsung Galaxy Mobile Phone");
        product1.setDescription("Small mobile phone powered by Android Kit-kat");
        // Save product1
        entityManager.persist(product1);
        // Create a second Product
        Product product2 = new Product();
        // Set fields
        product2.setName("Apple Magic Mouse");
        product2.setDescription("This mouse is wireless, and has a long-lasting battery");
        // Save product2
        entityManager.persist(product2);

        // Save the Id of the two new products
        ArrayList<Integer> savedIds =  new ArrayList<Integer>();
        savedIds.add(product1.getId());
        savedIds.add(product2.getId());

        // Let's find All the products that we have just saved
        ArrayList<Integer> actualIds =  new ArrayList<Integer>();
        productRepository.findAll().iterator().
                forEachRemaining(actual -> { actualIds.add(actual.getId()); });

        // The products that we have found must be the products that we saved
        // for the findAll method to work
        for (int i = 0; i < actualIds.size(); i++) {
            assertThat(savedIds.get(i)).isEqualTo(actualIds.get(i));
        }
    }

}
