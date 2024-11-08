
package com.cloudthat.productsappv2.controller;



import com.cloudthat.productsappv2.model.ApiResponse;
import com.cloudthat.productsappv2.model.ProductModel;
import com.cloudthat.productsappv2.model.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductsController {
    @Autowired
    private com.cloudthat.productsappv2.service.ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductRequest productRequest){
        ProductModel productModel = productService.saveProduct(productRequest);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"Product Created Successfully", productModel ), HttpStatus.CREATED );
    }

    @GetMapping("/products")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<ProductModel> productModels =  productService.getProducts();
        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"Products Fetched Successfully", productModels ), HttpStatus.OK );

    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ApiResponse> getProduct(@PathVariable("productId") Long productId){
        ProductModel productModel = productService.getProduct(productId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"Product Fetch Successful", productModel ), HttpStatus.OK );
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductRequest productRequest){
        ProductModel productModel = productService.updateProduct(productId, productRequest);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"Product update Successful", productModel ), HttpStatus.OK );
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") Long productId){
        productService.deleteProduct(productId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"Product delete Successful","" ), HttpStatus.OK );
    }

    @GetMapping("/products/names/{productName}")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable("productName") String productName){
        ProductModel productModel = productService.getProductByName(productName);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"Product Fetch Successful", productModel ), HttpStatus.OK );

    }

    @GetMapping("/products/pages")
    public ResponseEntity<ApiResponse> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "productName") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ){
        Page<ProductModel> products = productService.getProducts(page, size, sort, direction);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"Products Fetch Successful", products ), HttpStatus.OK );
    }


}
