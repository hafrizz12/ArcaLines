package org.hafidzmrizky.smkbi.dao;

import org.hafidzmrizky.smkbi.model.Flight;
import org.hafidzmrizky.smkbi.model.dto.ScheduleDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.*;

public class FlightDao implements Dao<Flight, Integer> {

    private final Optional<Connection> connection;

    public FlightDao() {
        connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Flight> get(int id) {
        return connection.flatMap(conn -> {
            Optional<Flight> flight = Optional.empty();
            String sql = "SELECT * FROM flight WHERE id = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int flight_id = rs.getInt("flight_id");
                    int plane_id = rs.getInt("plane_id");
                    int departure_id = rs.getInt("departure_id");
                    int destination_id = rs.getInt("destination_id");
                    Date departure_time = rs.getDate("departure_time");
                    Date arrival_time = rs.getDate("arrival_time");
                    int price = rs.getInt("price");

                    Flight flightResult = new Flight();
                    flightResult.setFlight_id(flight_id);
                    flightResult.setPlane_id(plane_id);
                    flightResult.setDeparture_id(departure_id);
                    flightResult.setDestination_id(destination_id);
                    flightResult.setDeparture_time(departure_time);
                    flightResult.setArrival_time(arrival_time);
                    flightResult.setPrice(price);

                    flight = Optional.of(flightResult);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return flight;
        });
    }

    @Override
    public Collection<Flight> getAll() {
        Collection<Flight> result = new LinkedList<>();
        String sql = "SELECT * FROM flight";
        connection.ifPresent(conn -> {
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int flight_id = rs.getInt("flight_id");
                    int plane_id = rs.getInt("plane_id");
                    int departure_id = rs.getInt("departure_id");
                    int destination_id = rs.getInt("destination_id");
                    Date departure_time = rs.getDate("departure_time");
                    Date arrival_time = rs.getDate("arrival_time");
                    int price = rs.getInt("price");

                    Flight flight = new Flight();
                    flight.setFlight_id(flight_id);
                    flight.setPlane_id(plane_id);
                    flight.setDeparture_id(departure_id);
                    flight.setDestination_id(destination_id);
                    flight.setDeparture_time(departure_time);
                    flight.setArrival_time(arrival_time);
                    flight.setPrice(price);
                    result.add(flight);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return result;
    }

    @Override
    public Optional<Integer> save(Flight flight) {
        String message = "The flight to be added should not be null";
        Flight nonNullFlight = Objects.requireNonNull(flight, message);
        String sql = "INSERT INTO flight (plane_id, departure_id, destination_id, departure_time, arrival_time, price, date_created, last_modified, created_by, updated_by) VALUES (?,?,?,?,?,?,?,?,?,?)";
        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, flight.getPlane_id());
                ps.setInt(2, flight.getDeparture_id());
                ps.setInt(3, flight.getDestination_id());
                ps.setDate(4, new Date(flight.getDeparture_time().getTime()));
                ps.setDate(5, new Date(flight.getArrival_time().getTime()));
                ps.setInt(6, flight.getPrice());
                ps.setDate(7, new Date(flight.getDateCreated().getTime()));
                ps.setDate(8, new Date(flight.getLastModified().getTime()));
                ps.setString(9, flight.getCreatedBy());
                ps.setString(10, flight.getUpdatedBy());
                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    ResultSet generatedKeys = ps.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        generatedId = Optional.of(id);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return generatedId;
        });
    }

    @Override
    public void update(Flight flight) {
        String message = "The flight to be updated should not be null";
        Flight nonNullFlight = Objects.requireNonNull(flight, message);
        String sql = "UPDATE flight SET plane_id = ?, departure_id = ?, destination_id = ?, departure_time = ?, arrival_time = ?, price = ?, date_created = ?, last_modified = ?, created_by = ?, updated_by = ? WHERE flight_id = ?";
        connection.ifPresent(conn -> {
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, flight.getPlane_id());
                ps.setInt(2, flight.getDeparture_id());
                ps.setInt(3, flight.getDestination_id());
                ps.setDate(4, new Date(flight.getDeparture_time().getTime()));
                ps.setDate(5, new Date(flight.getArrival_time().getTime()));
                ps.setInt(6, flight.getPrice());
                ps.setDate(7, new Date(flight.getDateCreated().getTime()));
                ps.setDate(8, new Date(flight.getLastModified().getTime()));
                ps.setString(9, flight.getCreatedBy());
                ps.setString(10, flight.getUpdatedBy());
                ps.setInt(11, flight.getFlight_id());
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void delete(Flight flight) {
        String message = "The flight to be deleted should not be null";
        Flight nonNullFlight = Objects.requireNonNull(flight, message);
        String sql = "DELETE FROM flight WHERE flight_id = ?";
        connection.ifPresent(conn -> {
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, flight.getFlight_id());
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Collection<Flight> search(String keyword) {
        return null;
    }

    @Override
    public Collection<ScheduleDTO> searchSchedule(long departureId, long arrivalId, java.util.Date departureDate) {
        return null;
    }
}
