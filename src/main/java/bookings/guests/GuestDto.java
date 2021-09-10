package bookings.guests;

import bookings.rooms.RoomDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestDto {

    private long id;

    private String name;

    private RoomDto room;}