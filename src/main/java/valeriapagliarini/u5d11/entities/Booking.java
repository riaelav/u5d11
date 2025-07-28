package valeriapagliarini.u5d11.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bookings")

public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "request_date")
    private LocalDate requestDate;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "travel_id", nullable = false)
    private Travel travel;

    private String notes;

    public Booking(LocalDate requestDate, Employee employee, Travel travel, String notes) {
        this.requestDate = requestDate;
        this.employee = employee;
        this.travel = travel;
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "requestDate=" + requestDate +
                ", employee=" + employee +
                ", travel=" + travel +
                ", notes='" + notes + '\'' +
                '}';
    }
}
