package valeriapagliarini.u5d11.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import valeriapagliarini.u5d11.entities.Booking;

import java.time.LocalDate;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByEmployeeIdAndRequestDate(Long employeeId, LocalDate requestDate);

    Optional<Booking> findByEmployeeIdAndRequestDate(Long employeeId, LocalDate requestDate);
}
