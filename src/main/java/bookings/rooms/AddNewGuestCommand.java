package bookings.rooms;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNewGuestCommand {

    @NotBlank(message = "Name of the guest must be given")
    @Schema(name = "Name of the guest", example = "Margot Robbie")
    private String name;}