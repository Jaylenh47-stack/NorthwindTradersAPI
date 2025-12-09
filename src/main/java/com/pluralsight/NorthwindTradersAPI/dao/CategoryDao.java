package com.pluralsight.NorthwindTradersAPI.dao;

import com.pluralsight.NorthwindTradersAPI.models.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {

    List<Category> getAll() throws SQLException;

    Category getById(int id) throws SQLException;
}
