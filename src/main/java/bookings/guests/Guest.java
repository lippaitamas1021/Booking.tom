package bookings.guests;

import bookings.rooms.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "guests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
    private Room room;

    public Guest(String name) {
        this.name = name;
    }

    public Guest(String name, Room room) {
        this.name = name;
        this.room = room; }

    public boolean hasNoRoom() {
        return room==null; }
}
