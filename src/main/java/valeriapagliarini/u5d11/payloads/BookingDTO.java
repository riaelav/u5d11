package valeriapagliarini.u5d11.payloads;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookingDTO(
        @NotNull(message = "Request date is required")
        LocalDate requestDate,

        @NotNull(message = "Employee ID is required")
        Long employeeId,

        @NotNull(message = "Travel ID is required")
        Long travelId,

        String notes
) {
}
