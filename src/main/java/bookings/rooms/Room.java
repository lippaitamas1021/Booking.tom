package bookings.rooms;

import bookings.guests.Guest;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String roomNumber;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private List<Guest> guests = new ArrayList<>();

    public Room(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void addGuests(Guest guest) {
        guests.add(guest);
        guest.setRoom(this); }
}