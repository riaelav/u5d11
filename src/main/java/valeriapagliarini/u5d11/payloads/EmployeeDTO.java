package valeriapagliarini.u5d11.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record EmployeeDTO(
        @NotEmpty(message = "Username is required")
        String username,

        @NotEmpty(message = "First name is required")
        @Size(max = 50, message = "First name can't be longer than 50 characters")
        String firstName,

        @NotEmpty(message = "Last name is required")
        @Size(max = 50, message = "Last name can't be longer than 50 characters")
        String lastName,

        @NotEmpty(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @NotEmpty(message = "Password is required")
        String password

) {
}
