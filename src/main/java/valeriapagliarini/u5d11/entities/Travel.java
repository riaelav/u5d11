package valeriapagliarini.u5d11.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import valeriapagliarini.u5d11.enums.TravelStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "travels")
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String destination;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private TravelStatus status = TravelStatus.SCHEDULED;

    public Travel(String destination, LocalDate date) {
        this.destination = destination;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Travel{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", date=" + date +
                '}';
    }
}
