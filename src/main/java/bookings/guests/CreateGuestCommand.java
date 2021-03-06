package bookings.guests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGuestCommand {

    @NotBlank(message = "Name must be completed")
    @Schema(description = "Name of the guest", example = "Mila Kunis")
    private String name;}
