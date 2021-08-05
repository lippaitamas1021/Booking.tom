package bookings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.zalando.problem.Problem;
import java.util.List;
import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GuestControllerRestTestIT {

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    void setup() {
        template.delete("/api/guests");
    }

    @Test
    @DisplayName(value = "Saving & listing")
    void saveGuestThenListAllTest() {
        template.postForObject("/api/guests", new CreateGuestCommand("Mila Kunis", new Room("H11")),GuestDto.class);
        template.postForObject("/api/guests", new CreateGuestCommand("Margot Robbie", new Room("H7")),GuestDto.class);
        template.postForObject("/api/guests", new CreateGuestCommand("Nemes Anna", new Room("H6")),GuestDto.class);
        List<GuestDto> guests = template.exchange("/api/guests",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GuestDto>>(){})
                .getBody();
        assertThat(guests)
                .hasSize(3)
                .extracting(GuestDto::getName)
                .contains("Margot Robbie");
    }
    @Test
    @DisplayName(value = "Finding a guest by name")
    void findGuestByNameTest() {
        template.postForObject("/api/guests", new CreateGuestCommand("Kapás Boglárka", new Room("H3")), GuestDto.class);
        template.postForObject("/api/guests", new CreateGuestCommand("Mutina Ági", new Room("H5")), GuestDto.class);
        template.postForObject("/api/guests", new CreateGuestCommand("Jakabos Zsuzsanna", new Room("H6")), GuestDto.class);
        List<GuestDto> guests = template.exchange("/api/guests?name=Kapás Boglárka",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<GuestDto>>(){})
                .getBody();
        assertEquals("Jakabos Zsuzsanna", Objects.requireNonNull(guests).get(2).getName());
    }

    @Test
    @DisplayName(value = "Updating a guest")
    void updateGuestTest() {
        GuestDto guestDto = template.postForObject("/api/guests", new CreateGuestCommand("Varsányi Lilla", new Room("H14")), GuestDto.class);
        template.put("/api/guests/" + guestDto.getId(), new UpdateGuestCommand("Stephanie Mertens"));
        GuestDto guest = Objects.requireNonNull(template.exchange("/api/guests",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<GuestDto>>(){})
                .getBody()).get(0);
        assertEquals("Stephanie Mertens", guest.getName());
    }

    @Test
    @DisplayName(value = "Saving a guest with invalid name")
    void saveGuestWithInvalidNameTest() {
        Problem guest = template.postForObject("/api/guests", new CreateGuestCommand("", new Room("H15")), Problem.class);
        assertEquals(400, Objects.requireNonNull(guest.getStatus()).getStatusCode());
    }

    @Test
    @DisplayName(value = "Deleting a guest by ID")
    void deleteByIdTest() {
        template.postForObject("/api/guests", new CreateGuestCommand("Margot Robbie", new Room("H17")), GuestDto.class);
        template.delete("/api/guests/1");
        List<GuestDto> guests = template.exchange("/api/guests",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<GuestDto>>() {})
                .getBody();
        assert guests != null;
        assertEquals(0, guests.size());
    }
}
