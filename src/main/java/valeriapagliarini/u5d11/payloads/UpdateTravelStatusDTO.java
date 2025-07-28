package valeriapagliarini.u5d11.payloads;

import jakarta.validation.constraints.NotNull;
import valeriapagliarini.u5d11.enums.TravelStatus;

public record UpdateTravelStatusDTO(
        @NotNull(message = "Status is required")
        TravelStatus status
) {
}
