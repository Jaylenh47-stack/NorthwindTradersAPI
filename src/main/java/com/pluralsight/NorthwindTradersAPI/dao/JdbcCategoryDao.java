package com.pluralsight.NorthwindTradersAPI.dao;

import com.pluralsight.NorthwindTradersAPI.models.Category;
import com.pluralsight.NorthwindTradersAPI.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCategoryDao implements CategoryDao{

    DataSource dataSource;

    @Autowired
    public JdbcCategoryDao(DataSource dataSource){
        this.dataSource = dataSource;
    }


    @Override
    public List<Category> getAll() throws SQLException {

        List<Category> categories = new ArrayList<>();

        try(
                Connection connection = this.dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT CategoryId, CategoryName, Description FROM categories");
                ResultSet results = preparedStatement.executeQuery();
        ){

            while (results.next()){
                int id = results.getInt("CategoryId");
                String name = results.getString("CategoryName");
                String description = results.getString("Description");

                Category c = new Category(id, name, description);
                categories.add(c);
            }
        }

        return categories;
    }

    @Override
    public Category getById(int id) throws SQLException {

        try(
                Connection connection = this.dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT CategoryId, CategoryName, Description FROM products WHERE CategoryID = ?");
        ){
            preparedStatement.setInt(1, id);

            try(ResultSet results = preparedStatement.executeQuery()){

                if (results.next()){
                    int categoryId = results.getInt("CategoryID");
                    String name = results.getString("CategoryName");
                    String description = results.getString("Description");

                    return new Category(categoryId,name,description);
                }
            }
        }
        return null;
    }
}
