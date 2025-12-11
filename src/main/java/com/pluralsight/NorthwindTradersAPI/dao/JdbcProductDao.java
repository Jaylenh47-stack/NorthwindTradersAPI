package com.pluralsight.NorthwindTradersAPI.dao;

import com.pluralsight.NorthwindTradersAPI.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao {

   private DataSource dataSource;

    @Autowired
    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }





    @Override
    public List<Product> getAll() throws SQLException {

        List<Product> products = new ArrayList<>();

        try(
                Connection connection = this.dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT ProductId, ProductName, UnitPrice, CategoryID FROM products");
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

    @Override
    public Product getById(int id) throws SQLException {

        try(
                Connection connection = this.dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT ProductId, ProductName, UnitPrice, CategoryID FROM products WHERE ProductID = ?");
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
            }
        }
        return null;
    }

    @Override
    public Product insert(Product product) throws SQLException {
        String query = """
                INSERT INTO products (
                	ProductName,
                    CategoryID,
                    UnitPrice)
                VALUES
                (?, ?, ? );
                """;

        try(Connection connection = this.dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){

            statement.setString(1, product.getName());
            statement.setInt(2, product.getCategoryId());
            statement.setDouble(3, product.getPrice());

            int rows = statement.executeUpdate();

            try(ResultSet keys = statement.getGeneratedKeys()){
                if(keys.next()){
                    int productId =  keys.getInt(1);
                    product.setProductId(productId);
                    return product;
                }

            }
        }

        return null;

    }

    @Override
    public void update(int id, Product product) throws SQLException {
        String query = """
                UPDATE products
                SET
                ProductName = ?,
                CategoryId = ?,
                UnitPrice = ?
                WHERE ProductId = ?;""";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            statement.setString(1, product.getName());
            statement.setInt(2, product.getCategoryId());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, id);

            int rows = statement.executeUpdate();


        }

    }

    @Override
    public void delete(int id) throws SQLException {
        String query = """
                DELETE FROM products
                WHERE productId = ?""";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            statement.setInt(1, id);

            int rows = statement.executeUpdate();

        }

    }


}
