package com.pluralsight.NorthwindTradersAPI.dao;

import com.pluralsight.NorthwindTradersAPI.models.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    List<Product> getAll() throws SQLException;

     Product getById(int id) throws SQLException;

}
