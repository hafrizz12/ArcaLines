package org.hafidzmrizky.smkbi.dao;

import org.hafidzmrizky.smkbi.model.Flight;
import org.hafidzmrizky.smkbi.model.Ticket;
import org.hafidzmrizky.smkbi.model.dto.ScheduleDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.sql.Date;
import java.util.LinkedList;
import java.util.Optional;

public class TicketDao implements Dao<Ticket, Long> {

    private final Optional<Connection> conn;

    public TicketDao() {
        conn = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Ticket> get(int id) {
        return conn.flatMap(connection -> {
            Optional<Ticket> ticketRes = Optional.empty();
            String sql = "SELECT * FROM ticket WHERE id = ?";
            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int idTicket = rs.getInt("id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    int flight_id = rs.getInt("flight_id");
                    String passport_id = rs.getString("passport_id");
                    String createdBy = rs.getString("created_by");
                    String updatedBy = rs.getString("updated_by");
                    Date dateCreated = rs.getDate("date_created");
                    Date lastModified = rs.getDate("last_modified");

                    Ticket ticket = new Ticket();
                    ticket.setId(idTicket);
                    ticket.setEmail(email);
                    ticket.setName(name);
                    ticket.setFlight_id(flight_id);
                    ticket.setPassportId(passport_id);
                    ticket.setCreatedBy(createdBy);
                    ticket.setUpdatedBy(updatedBy);
                    ticket.setDateCreated(dateCreated);
                    ticket.setLastModified(lastModified);

                    ticketRes = Optional.of(ticket);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ticketRes;
        });
    }

    @Override
    public Collection<Ticket> getAll() {
        Collection<Ticket> tickets = new LinkedList<>();
        String sql = "SELECT * FROM ticket";
        conn.ifPresent(connection -> {
            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int idTicket = rs.getInt("id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    int flight_id = rs.getInt("flight_id");
                    String passport_id = rs.getString("passport_id");
                    String createdBy = rs.getString("created_by");
                    String updatedBy = rs.getString("updated_by");
                    Date dateCreated = rs.getDate("date_created");
                    Date lastModified = rs.getDate("last_modified");

                    Ticket ticket = new Ticket();
                    ticket.setId(idTicket);
                    ticket.setEmail(email);
                    ticket.setName(name);
                    ticket.setFlight_id(flight_id);
                    ticket.setPassportId(passport_id);
                    ticket.setCreatedBy(createdBy);
                    ticket.setUpdatedBy(updatedBy);
                    ticket.setDateCreated(dateCreated);
                    ticket.setLastModified(lastModified);
                    tickets.add(ticket);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return tickets;
    }

    @Override
    public Optional<Long> save(Ticket ticket) {
       String sql = "INSERT INTO ticket (name, email, flight_id, passport_id, created_by, updated_by, date_created, last_modified) VALUES (?,?,?,?,?,?,?,?)";
        return conn.flatMap(connection -> {
            Optional<Long> generatedId = Optional.empty();
            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, ticket.getName());
                ps.setString(2, ticket.getEmail());
                ps.setInt(3, ticket.getFlight_id());
                ps.setString(4, ticket.getPassportId());
                ps.setString(5, ticket.getCreatedBy());
                ps.setString(6, ticket.getUpdatedBy());
                ps.setDate(7, new Date(ticket.getDateCreated().getTime()));
                ps.setDate(8, new Date(ticket.getLastModified().getTime()));
                int numberOfInsertedRows = ps.executeUpdate();
                if (numberOfInsertedRows > 0) {
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        generatedId = Optional.of(rs.getLong(1));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return generatedId;
        });
    }

    @Override
    public void update(Ticket ticket) {
        String sql = "UPDATE ticket SET name = ?, email = ?, flight_id = ?, passport_id = ?, created_by = ?, updated_by = ?, date_created = ?, last_modified = ? WHERE id = ?";
        conn.ifPresent(connection -> {
            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, ticket.getName());
                ps.setString(2, ticket.getEmail());
                ps.setInt(3, ticket.getFlight_id());
                ps.setString(4, ticket.getPassportId());
                ps.setString(5, ticket.getCreatedBy());
                ps.setString(6, ticket.getUpdatedBy());
                ps.setDate(7, new Date(ticket.getDateCreated().getTime()));
                ps.setDate(8, new Date(ticket.getLastModified().getTime()));
                ps.setInt(9, ticket.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void delete(Ticket ticket) {
        String sql = "DELETE FROM ticket WHERE id = ?";
        conn.ifPresent(connection -> {
            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, ticket.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Collection<Ticket> search(String keyword) {
        return null;
    }

    @Override
    public Collection<ScheduleDTO> searchSchedule(long departureId, long arrivalId, java.util.Date departureDate) {
        return null;
    }
}
