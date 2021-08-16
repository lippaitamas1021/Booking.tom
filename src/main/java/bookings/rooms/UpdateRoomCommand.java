package bookings.rooms;

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
public class UpdateRoomCommand {

    @NotBlank(message = "Room number must be completed")
    @Schema(description = "Room number of the guest", example = "H11")
    private String roomNumber;
}
