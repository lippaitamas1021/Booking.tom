package bookings.rooms;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddExistingGuestCommand {

    @Schema(name = "ID of the guest", example = "2")
    private long id;}