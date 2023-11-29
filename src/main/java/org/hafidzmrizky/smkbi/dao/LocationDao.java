package org.hafidzmrizky.smkbi.dao;

import org.hafidzmrizky.smkbi.dao.Dao;
import org.hafidzmrizky.smkbi.model.Location;
import org.hafidzmrizky.smkbi.model.Schedule;
import org.hafidzmrizky.smkbi.model.dto.ScheduleDTO;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;

public class LocationDao implements Dao<Location, Integer> {

    private final Optional<Connection> connection;

    public LocationDao() {
        connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Location> get(int id) {
        return connection.flatMap(conn -> {
            Optional<Location> location = Optional.empty();
            String sql = "SELECT * FROM location WHERE id = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int location_id = rs.getInt("id");
                    String location_name = rs.getString("name");

                    Location locationResult = new Location();
                    locationResult.setId(location_id);
                    locationResult.setName(location_name);

                    location = Optional.of(locationResult);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return location;
        });
    }

    @Override
    public Collection<Location> getAll() {
        Collection<Location> result = new LinkedList<>();
        String sql = "SELECT * FROM location";
        connection.ifPresent(connection -> {
            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");

                    Location location = new Location();
                    location.setId(id);
                    location.setName(name);
                    result.add(location);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return result;
    }

    @Override
    public Optional<Integer> save(Location location) {
        return Optional.empty();
    }

    @Override
    public void update(Location location) {

    }

    @Override
    public void delete(Location location) {

    }

    @Override
    public Collection<Location> search(String keyword) {
        return null;
    }

    @Override
    public Collection<ScheduleDTO> searchSchedule(long departureId, long arrivalId, Date departureDate) {
        return null;
    }
}
