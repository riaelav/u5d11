package valeriapagliarini.u5d11.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TravelDTO(
        @NotEmpty(message = "Destination is required")
        String destination,

        @NotNull(message = "Date is required")
        LocalDate date

) {
}
