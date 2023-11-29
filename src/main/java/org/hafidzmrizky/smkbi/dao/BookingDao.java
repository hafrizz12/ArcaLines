package org.hafidzmrizky.smkbi.dao;

import org.hafidzmrizky.smkbi.model.Booking;
import org.hafidzmrizky.smkbi.model.dto.ScheduleDTO;

import java.sql.*;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;

public class BookingDao implements Dao<Booking, Integer> {

    private final Optional<Connection> connection;

    public BookingDao() {
        connection = JdbcConnection.getConnection();
    }


    @Override
    public Optional<Booking> get(int id) {
        return connection.flatMap(conn -> {
            Optional<Booking> booking = Optional.empty();
            String sql = "SELECT * FROM booking WHERE id = ?";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int booking_id = rs.getInt("id");
                    int schedule_id = rs.getInt("schedule_id");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");

                    Booking bookingResult = new Booking();
                    bookingResult.setId(booking_id);
                    bookingResult.setScheduleId(schedule_id);
                    bookingResult.setName(name);
                    bookingResult.setPrice(price);

                    booking = Optional.of(bookingResult);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return booking;
        });
    }

    @Override
    public Collection<Booking> getAll() {
        Collection<Booking> res = new LinkedList<>();
        String sql = "SELECT * FROM booking";
        connection.ifPresent(conn -> {
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int schedule_id = rs.getInt("schedule_id");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");

                    Booking booking = new Booking();
                    booking.setId(id);
                    booking.setScheduleId(schedule_id);
                    booking.setName(name);
                    booking.setPrice(price);

                    res.add(booking);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return res;
    }

    @Override
    public Optional<Integer> save(Booking booking) {
        String sql = "INSERT INTO booking (schedule_id, name, price) VALUES (?,?,?)";
        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();
            try {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, booking.getId());
                ps.setString(2, booking.getName());
                ps.setDouble(3, booking.getPrice());
                int numberOfInsertedRows = ps.executeUpdate();
                if (numberOfInsertedRows > 0) {
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
    public void update(Booking booking) {

    }

    @Override
    public void delete(Booking booking) {

    }

    @Override
    public Collection<Booking> search(String keyword) {
        return null;
    }

    @Override
    public Collection<ScheduleDTO> searchSchedule(long departureId, long arrivalId, Date departureDate) {
        return null;
    }
}
