package org.hafidzmrizky.smkbi.dao;

import org.hafidzmrizky.smkbi.model.Plane;
import org.hafidzmrizky.smkbi.model.dto.ScheduleDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;

public class PlaneDao implements Dao<Plane, Integer> {

    private final Optional<Connection> connection;

    public PlaneDao() {
        connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Plane> get(int id) {
        return connection.flatMap(conn -> {
            Optional<Plane> plane = Optional.empty();
            String sql = "SELECT * FROM plane WHERE plane_id = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int plane_id = rs.getInt("plane_id");
                    String plane_name = rs.getString("plane_name");
                    int capacity = rs.getInt("capacity");
                    int location_id = rs.getInt("location_id");

                    Plane planeResult = new Plane();
                    planeResult.setId(plane_id);
                    planeResult.setName(plane_name);
                    planeResult.setCapacity(capacity);
                    planeResult.setLocationId(location_id);

                    plane = Optional.of(planeResult);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return plane;
        });
    }

    @Override
    public Collection<Plane> getAll() {
        Collection<Plane> result = new LinkedList<>();
        String sql = "SELECT * FROM plane";
        connection.ifPresent(conn -> {
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int plane_id = rs.getInt("plane_id");
                    String plane_name = rs.getString("plane_name");
                    int capacity = rs.getInt("capacity");
                    int location_id = rs.getInt("location_id");

                    Plane plane = new Plane();
                    plane.setId(plane_id);
                    plane.setName(plane_name);
                    plane.setCapacity(capacity);
                    plane.setLocationId(location_id);
                    result.add(plane);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return result;
    }

    @Override
    public Optional<Integer> save(Plane plane) {
        String sql = "INSERT INTO plane (plane_name, capacity, location_id) VALUES (?, ?, ?)";
        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, plane.getName());
                ps.setInt(2, plane.getCapacity());
                ps.setInt(3, plane.getLocationId());
                int affectedRow = ps.executeUpdate();
                if (affectedRow > 0) {
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        generatedId = Optional.of(rs.getInt(1));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return generatedId;
        });
    }

    @Override
    public void update(Plane plane) {
        String sql = "UPDATE plane SET plane_name = ?, capacity = ?, location_id = ? WHERE plane_id = ?";
        connection.ifPresent(conn -> {
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, plane.getName());
                ps.setInt(2, plane.getCapacity());
                ps.setInt(3, plane.getLocationId());
                ps.setInt(4, plane.getId());
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void delete(Plane plane) {
        String sql = "DELETE FROM plane WHERE plane_id = ?";
        connection.ifPresent(conn -> {
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, plane.getId());
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Collection<Plane> search(String keyword) {
        return null;
    }

    @Override
    public Collection<ScheduleDTO> searchSchedule(long departureId, long arrivalId, Date departureDate) {
        return null;
    }
}
