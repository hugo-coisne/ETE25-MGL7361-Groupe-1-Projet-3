package order.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.DBConnection;

public class OrderDAO {
   public void getOrders() {
      try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "SELECT * FROM Orders")) {

            ResultSet rs = statement.executeQuery();

            System.out.println("id account_id order_date total_price");
            while (rs.next()) {
               int id = rs.getInt("id");
               int account_id = rs.getInt("account_id");
               Date order_date = rs.getDate("order_date");
               Double total_price = rs.getDouble("total_price");
               System.out.println(id + " " + account_id + " " + order_date + " " + total_price);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de manière appropriée, par exemple, en lançant une exception personnalisée
        }
   }
}
