package com.pluralsight.NorthwindTradersAPI.dao;

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
public class JdbcProductDao implements ProductDao {

    DataSource dataSource;

    @Autowired
    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> getAll() throws SQLException {

        List<Product> products = new ArrayList<>();

        try(
                Connection connection = this.dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT ProductId, ProductName, UnitPrice FROM products");
                ResultSet results = preparedStatement.executeQuery();
                ){

            while (results.next()){
                int productId = results.getInt("ProductId");
                String productName = results.getString("ProductName");
                int categoryID = results.getInt("CategoryId");
                double unitPrice = results.getDouble("UnitPrice");

                Product p = new Product(productId, productName, categoryID, unitPrice);
                products.add(p);
            }
        }

        return products;
    }


    public Product getById2(int id) throws SQLException {

        List<Product> products = new ArrayList<>();

        try(
                Connection connection = this.dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT ProductId, ProductName, UnitPrice FROM products WHERE CategoryID = ?");
        ){
                preparedStatement.setInt(1, id);

                try(ResultSet results = preparedStatement.executeQuery()){

                    while (results.next()){
                        int productId = results.getInt("ProductId");
                        String productName = results.getString("ProductName");
                        int categoryID = results.getInt("CategoryId");
                        double unitPrice = results.getDouble("UnitPrice");

                        Product p = new Product(productId, productName, categoryID, unitPrice);
                        products.add(p);
                    }
            }
        }
        return products.getFirst();
    }

    @Override
    public Product getById(int id) throws SQLException {

        try(
                Connection connection = this.dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT ProductId, ProductName, UnitPrice FROM products WHERE CategoryID = ?");
        ){
            preparedStatement.setInt(1, id);

            try(ResultSet results = preparedStatement.executeQuery()){

                if (results.next()){
                    int productId = results.getInt("ProductId");
                    String productName = results.getString("ProductName");
                    int categoryID = results.getInt("CategoryId");
                    double unitPrice = results.getDouble("UnitPrice");

                    Product p = new Product(productId, productName, categoryID, unitPrice);
                    return p;
                }
                else{
                    return null;
                }
            }
        }

    }
}
