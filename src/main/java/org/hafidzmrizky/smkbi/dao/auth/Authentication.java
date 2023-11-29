package org.hafidzmrizky.smkbi.dao.auth;

import org.hafidzmrizky.smkbi.dao.JdbcConnection;
import org.hafidzmrizky.smkbi.model.User;

import java.sql.Connection;
import java.util.Optional;

public class Authentication {

    private final Optional<Connection> connection;

    public Authentication() {
        connection = JdbcConnection.getConnection();
    }

    public Optional<User> login(String username, String password) {
        return connection.flatMap(conn -> {
            Optional<User> userx= Optional.empty();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            try {
                var ps = conn.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);
                var rs = ps.executeQuery();
                if (rs.next()) {
                    // get roles admin or not
                    User user = new User();
                    user.setUser_id(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                    userx = Optional.of(user);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return userx;
        });
    }

    public Optional<User> register(String username, String password, String fullname, String phoneNumber) {
        return connection.flatMap(conn -> {
            Optional<User> userx= Optional.empty();

            String sql = "SELECT * FROM users WHERE username = ?";
            try {
                var ps = conn.prepareStatement(sql);
                ps.setString(1, username);
                var rs = ps.executeQuery();
                if (rs.next()) {
                    // get roles admin or not
                    return userx;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            sql = "INSERT INTO users (username, password, role, name, phone_number) VALUES (?,?,?,?, ?)";
            try {
                var ps = conn.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, "user");
                ps.setString(4, fullname);
                ps.setString(5, phoneNumber);
                ps.executeUpdate();
                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    // get roles admin or not
                    User user = new User();
                    user.setUser_id(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setFullName(rs.getString("name"));
                    user.setRole(rs.getString("role"));
                    userx = Optional.of(user);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return userx;
        });
    }


};
