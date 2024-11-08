
package com.cloudthat.productsappv2.service;


import com.cloudthat.productsappv2.entity.Product;
import com.cloudthat.productsappv2.exceptions.ResourceNotFoundException;
import com.cloudthat.productsappv2.model.ProductModel;
import com.cloudthat.productsappv2.model.ProductRequest;
import com.cloudthat.productsappv2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductModel saveProduct(ProductRequest productRequest) {
        ProductModel newProductModel = new ProductModel();
        newProductModel.setProductName(productRequest.getProductName());
        newProductModel.setPrice(productRequest.getPrice());
        newProductModel.setCategory(productRequest.getCategory());

        Product product = productRepository.save(productModelToProduct(newProductModel));
        return productToProductModel(product);
    }

    @Override
    public List<ProductModel> getProducts() {
        List<ProductModel> productModels = new ArrayList<ProductModel>();
        List<Product> products = productRepository.findAll();
        for(Product p : products){
            productModels.add(productToProductModel(p));
        }
        return productModels;
    }

    @Override
    public ProductModel getProduct(Long productId) {
        return productRepository.findById(productId)
                .map(this::productToProductModel)
                .orElseThrow(()-> new ResourceNotFoundException("Product","id",productId));
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        return null;
    }

    @Override
    public ProductModel updateProduct(Long productId, ProductRequest productRequest) {
        Product productDB = productRepository.findById(productId).get();

        if(Objects.nonNull(productRequest.getProductName()) && !("".equalsIgnoreCase(productRequest.getProductName()))){
            productDB.setProductName(productRequest.getProductName());
        }
        return productToProductModel(productRepository.save(productDB));
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public ProductModel getProductByName(String productName) {
        return productToProductModel(productRepository.findByProductName(productName));
    }

    @Override
    public Page<ProductModel> getProducts(int page, int size, String sort, String direction) {
        Sort sort1 = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page,size, sort1);
        Page<Product> products = productRepository.findAll(pageable);

        return products.map(this::productToProductModel);
    }

    private Product productModelToProduct(ProductModel productModel){
        Product product = new Product();
        product.setId(productModel.getId());
        product.setProductName(productModel.getProductName());
        product.setCategory(productModel.getCategory());
        product.setPrice(productModel.getPrice());
        return  product;
    }

    private ProductModel productToProductModel(Product product){
        ProductModel productModel = new ProductModel();
        productModel.setId(product.getId());
        productModel.setProductName(product.getProductName());
        productModel.setPrice(product.getPrice());
        productModel.setCategory(product.getCategory());

        return productModel;
    }
}
