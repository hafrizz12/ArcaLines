package org.hafidzmrizky.smkbi.dao;

import org.hafidzmrizky.smkbi.model.Schedule;
import org.hafidzmrizky.smkbi.model.dto.ScheduleDTO;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public interface Dao<T, I> {
    Optional<T> get(int id);
    Collection<T> getAll();
    Optional<I> save(T t);
    void update(T t);
    void delete(T t);

    Collection<T> search(String keyword);

    Collection<ScheduleDTO> searchSchedule(long departureId, long arrivalId, Date departureDate);
}
