
package com.cloudthat.productsappv2.repository;

import com.cloudthat.productsappv2.entity.Category;
import com.cloudthat.productsappv2.entity.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@DataJpaTest
@Transactional
public class ProductRepositoryTests {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindByIdReturnProduct() throws Exception{
        Product product = new Product("MacBook", 23.5, Category.ELECTRONICS);
        product.setId(1L);

        testEntityManager.merge(product);

        Product newProduct =productRepository.findById(1L).get();
        assertEquals("",product,newProduct);
    }
}
