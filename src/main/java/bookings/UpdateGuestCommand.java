package bookings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UpdateGuestCommand {

    @NotBlank
    @Schema(description = "Name of the guest", example = "Mila Kunis")
    private String name;
}
