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
public class CreateRoomCommand {

    @NotBlank(message = "Room number must be completed")
    @Schema(description = "Number of the room", example = "H11")
    private String roomNumber;
}
