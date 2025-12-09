package com.pluralsight.NorthwindTradersAPI.controllers;

import com.pluralsight.NorthwindTradersAPI.models.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductsController {
    private List<Product> products;

    public ProductsController(){
        this.products = new ArrayList<>();
        products.add(new Product(1, "Apple", 1, 1.99f));
        products.add(new Product(2, "Blueberries", 1, 2.99f));
        products.add(new Product(3, "Banana", 1, 0.99f));
        products.add(new Product(4, "Beef", 2, 9.99f));
    }

    @RequestMapping(path = "/products", method = RequestMethod.GET)
    public List<Product> getProducts() {
        return products;
    }

    @RequestMapping(path = "/products/{id}", method =RequestMethod.GET)
    public Product getProduct(@PathVariable int id){
        for (Product p : this.products){
            if (p.getProductId() == id){
                return p;
            }
        }
        return null;
    }

}
