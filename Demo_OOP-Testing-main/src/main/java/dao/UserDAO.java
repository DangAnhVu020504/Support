package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DatabaseConnection;
import jakarta.enterprise.context.ApplicationScoped;
import model.User;

@ApplicationScoped
public class UserDAO {

    public String searchUser(String username) {
        String query = "SELECT u.username, COUNT(p.id) as post_count FROM users u " +
                "LEFT JOIN posts p ON u.id = p.user_id WHERE u.username = ? GROUP BY u.id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String foundUsername = rs.getString("username");
                int postCount = rs.getInt("post_count");
                return "Người dùng " + foundUsername + " đã tham gia.";
            } else {
                return "Không tìm thấy người dùng.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi truy vấn dữ liệu.";
        }
    }


    public boolean checkUserExists(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, "user"); // Thêm giá trị role
            int rowsInserted = stmt.executeUpdate(); // Kiểm tra số dòng được thêm vào
            return rowsInserted > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        User user = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
