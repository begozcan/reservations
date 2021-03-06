package com.frankmoley.landon.business.service;

import com.frankmoley.landon.business.domain.Shopping;
import com.frankmoley.landon.data.entity.Product;
import com.frankmoley.landon.data.repository.ProductRepository;
import com.frankmoley.landon.data.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProductService {
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public ProductService(ProductRepository productRepository, CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Shopping> getAllProducts() {
        Iterable<Product> productIterable = this.productRepository.findAll();
        Map<Long, Shopping> productMap = new HashMap<>();
        productIterable.forEach(product->{
            Shopping shoppingObj = new Shopping();
            shoppingObj.setProductId(product.getProductID());
            shoppingObj.setTitle(product.getTitle());
            shoppingObj.setSubtitle(product.getSubtitle());
            shoppingObj.setCategory(product.getCategory());
            shoppingObj.setPrice(product.getPrice());
            shoppingObj.setDiscountPrice(product.getDiscountPrice());
            shoppingObj.setProductQuantity(product.getProductQuantity());
            productMap.put(product.getProductID(), shoppingObj);
        });

        List<Shopping> shoppingList = new ArrayList<>();
        for(Long productId: productMap.keySet()) {
            shoppingList.add(productMap.get(productId));
        }
        return shoppingList;
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(long productId) {
        productRepository.delete(productId);
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    public Product getProduct(long productID){
        return  productRepository.findOne(productID);
    }

}
