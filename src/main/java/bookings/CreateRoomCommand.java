package bookings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class CreateRoomCommand {

    private Long id;

    @NotBlank
    @Schema(description = "Number of the room", example = "H11")
    private String roomNumber;

    @NotEmpty
    private List<Guest> guests;
}
