package bookings.guests;

import bookings.rooms.Room;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from guests")
public class GuestControllerRestTestIT {

    @Autowired
    TestRestTemplate template;

    @Test
    @DisplayName(value = "Saving & listing")
    void saveGuestThenListAllTest() {
        template.postForObject("/api/guests", new CreateGuestCommand("Mila Kunis", new Room("H11")), GuestDto.class);
        template.postForObject("/api/guests", new CreateGuestCommand("Margot Robbie", new Room("H7")),GuestDto.class);
        template.postForObject("/api/guests", new CreateGuestCommand("Nemes Anna", new Room("H6")),GuestDto.class);
        List<GuestDto> guests = template.exchange("/api/guests",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<GuestDto>>(){}).getBody();
        assertThat(guests)
                .hasSize(3)
                .extracting(GuestDto::getName)
                .contains("Margot Robbie"); }


    @Test
    @DisplayName(value = "Finding a guest by name")
    void findGuestByNameTest() {
        template.postForObject("/api/guests", new CreateGuestCommand("Kapás Boglárka", new Room("H3")), GuestDto.class);
        template.postForObject("/api/guests", new CreateGuestCommand("Mutina Ági", new Room("H5")), GuestDto.class);
        template.postForObject("/api/guests", new CreateGuestCommand("Jakabos Zsuzsanna", new Room("H6")), GuestDto.class);
        List<GuestDto> guests = template.exchange("/api/guests?name=Kapás Boglárka",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<GuestDto>>(){}).getBody();
        assert guests != null;
        assertEquals("Kapás Boglárka", guests.get(0).getName()); }


    @Test
    @DisplayName(value = "Updating a guest")
    void updateGuestTest() {
        GuestDto guest = template.postForObject("/api/guests", new CreateGuestCommand("Varsányi Lilla", new Room("H14")), GuestDto.class);
        template.put("/api/guests/" + guest.getId(), new UpdateGuestCommand("Stephanie Mertens"));
        List<GuestDto> guests = template.exchange("/api/guests",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<GuestDto>>() {}).getBody();
        assert guests != null;
        assertEquals("Stephanie Mertens", guests.get(0).getName()); }


    @Test
    @DisplayName(value = "Saving a guest with invalid name")
    void saveGuestWithInvalidNameTest() {
        Problem guest = template.postForObject("/api/guests", new CreateGuestCommand("", new Room("H15")), Problem.class);
        assertEquals(Status.BAD_REQUEST, guest.getStatus()); }


    @Test
    @DisplayName(value = "Deleting a guest by ID")
    void deleteByIdTest() {
        GuestDto guestDto = template.postForObject("/api/guests", new CreateGuestCommand("Margot Robbie", new Room("H17")), GuestDto.class);
        template.delete("/api/guests/" + guestDto.getId());
        List<GuestDto> guests = template.exchange("/api/guests",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<GuestDto>>() {}).getBody();
        assert guests != null;
        assertEquals(0, guests.size()); }
}
