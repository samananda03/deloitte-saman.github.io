
package com.cloudthat.productsappv2.service;

import com.cloudthat.productsappv2.entity.Category;
import com.cloudthat.productsappv2.entity.Product;
import com.cloudthat.productsappv2.model.ProductModel;
import com.cloudthat.productsappv2.model.ProductRequest;
import com.cloudthat.productsappv2.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

public class ProductServiceTests {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

//    @Test
//    public void whenSaveProductShouldReturnUser() throws Exception{
//        ProductRequest product = new ProductRequest("Onion", 23.5, Category.GROCERY);
//
//        Mockito.when(productRepository.save(any(Product.class))).thenReturn(new Product());
//
//        Product product1 = productService.saveProduct(product);
//    }
}
