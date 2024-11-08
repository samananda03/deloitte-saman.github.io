
package com.cloudthat.productsappv2.service;



import com.cloudthat.productsappv2.entity.Product;
import com.cloudthat.productsappv2.model.ProductModel;
import com.cloudthat.productsappv2.model.ProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Page<ProductModel> getProducts(int page, int size, String sort, String direction);


    ProductModel saveProduct(ProductRequest productRequest);

    List<ProductModel> getProducts();

    ProductModel getProduct(Long productId);

    Product updateProduct(Long productId, Product product);

    ProductModel updateProduct(Long productId, ProductRequest productRequest);

    void deleteProduct(Long productId);

    ProductModel getProductByName(String productName);

}
