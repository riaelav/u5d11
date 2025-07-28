package valeriapagliarini.u5d11.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import valeriapagliarini.u5d11.entities.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUsername(String username);

    Optional<Employee> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
