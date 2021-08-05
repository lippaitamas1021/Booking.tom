package bookings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class CreateGuestCommand {

    @NotBlank
    @Schema(description = "Name of the guest", example = "Mila Kunis")
    private String name;

    @NotNull
    @Schema(description = "Room number of the guest", example = "H11")
    private Room room;

    public CreateGuestCommand(String name) {
        this.name = name;
    }
}
