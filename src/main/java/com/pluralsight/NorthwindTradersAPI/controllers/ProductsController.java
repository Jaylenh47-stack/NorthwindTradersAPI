package com.pluralsight.NorthwindTradersAPI.controllers;

import com.pluralsight.NorthwindTradersAPI.dao.ProductDao;
import com.pluralsight.NorthwindTradersAPI.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductsController {

    private ProductDao productDao;

    @Autowired
    public ProductsController(ProductDao productDao){
        this.productDao = productDao;
    }

    @RequestMapping(path ="/products", method = RequestMethod.GET)
    public List<Product> getProducts() throws SQLException {
           return productDao.getAll();

    }

    @RequestMapping(path = "/products/{id}", method =RequestMethod.GET)
    public Product getProduct(@PathVariable int id) throws SQLException {
            return productDao.getById(id);
    }

    @RequestMapping(path = "/products", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) throws SQLException {

        return productDao.insert(product);
    }

    @RequestMapping(path ="/product/{id}", method = RequestMethod.PUT)
    public void updateProduct(@PathVariable int id, @RequestBody Product product) throws SQLException {
           productDao.update(id, product);
    }

    @RequestMapping(path ="/product/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable int id) throws SQLException {
           productDao.delete(id);
    }

}
