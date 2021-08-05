package bookings;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class GuestRepositoryTestIT {

    @Autowired
    GuestRepository guestRepository;

    @Disabled
    @Test
    @DisplayName(value = "Finding a guest by a name part")
    void findGuestByPrefixTest() {
        Guest guestOne = new Guest("Mila Kunis");
        Guest guestTwo = new Guest("Margot Robbie");
        Guest guestThree = new Guest("Kapás Boglárka");
        guestRepository.save(guestOne);
        guestRepository.save(guestTwo);
        guestRepository.save(guestThree);
        List<Guest> result = guestRepository.findGuestByPrefix("obb");
        assertThat(result).extracting(Guest::getName).containsOnly("Margot Robbie");
    }

    @Test
    @DisplayName(value = "Finding a guest with generated query by name part")
    void findGuestByPrefixGeneratedTest() {
        Guest guestOne = new Guest("Mila Kunis");
        Guest guestTwo = new Guest("Margot Robbie");
        Guest guestThree = new Guest("Kapás Boglárka");
        guestRepository.save(guestOne);
        guestRepository.save(guestTwo);
        guestRepository.save(guestThree);
        List<Guest> result = guestRepository.findGuestByNameStartsWith("Kap");
        assertThat(result).extracting(Guest::getName).containsOnly("Kapás Boglárka");
    }
}
