package bookings.rooms;

import bookings.guests.GuestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonBackReference
    private List<GuestDto> guests;}