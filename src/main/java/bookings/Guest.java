package bookings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "guests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String name;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "rooms_id")
    private Room room;

    public Guest(String name) {
        this.name = name;
    }
}
