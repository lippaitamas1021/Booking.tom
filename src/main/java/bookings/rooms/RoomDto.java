package bookings.rooms;

import bookings.guests.GuestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {

    private long id;

    private String roomNumber;

    private List<GuestDto> guests;
}