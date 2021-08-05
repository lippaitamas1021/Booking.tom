package bookings;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import java.util.List;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from guests")
@Sql(statements = "delete from rooms")
public class RoomControllerRestIT {

    @Autowired
    private TestRestTemplate template;

    @Test
    @DisplayName(value = "Saving & Listing")
    void saveRoomThenListAllTest() {
//        GuestDto guestDto = template.postForObject("/api/guests",
//                new CreateGuestCommand("Margot Robbie"), GuestDto.class);
        template.postForObject("/api/rooms", new CreateRoomCommand(1L,"H1", List.of(
                new Guest("Mila Kunis"))), RoomDto.class);
        template.postForObject("/api/rooms", new CreateRoomCommand(2L,"H2", List.of(
                new Guest("Margot Robbie"))), RoomDto.class);
        List<RoomDto> rooms = template.exchange("/api/rooms",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RoomDto>>() {})
                .getBody();
        assert rooms != null;
        assertEquals(2, rooms.size());
    }


    @Test
    @DisplayName(value = "Finding a room by ID")
    void findRoomByIdTest() {
        RoomDto roomDto = template.postForObject("/api/rooms", new CreateRoomCommand(1L, "H1", List.of(
                new Guest("Mila Kunis"))), RoomDto.class);
        RoomDto result = template.exchange("/api/rooms/" + roomDto.getId(),
                HttpMethod.GET,
                null,
                RoomDto.class)
                .getBody();
        assert result != null;
        assertEquals("H1", result.getRoomNumber());
    }


    @Test
    @DisplayName(value = "Finding a room by name")
    void findRoomByNameTest() {
        RoomDto roomDto = template.postForObject("/api/rooms", new CreateRoomCommand(1L, "H1", List.of(
                new Guest("Mila Kunis"))), RoomDto.class);
        List<RoomDto> result = template.exchange("/api/rooms?name=H1",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RoomDto>>() {})
                .getBody();
        assert result != null;
        assertEquals("H1", result.get(0).getRoomNumber());
    }


    @Test
    @DisplayName(value = "Updating the room number")
    void updateRoomNumberTest() {
        RoomDto roomDto = template.postForObject("/api/rooms", new CreateRoomCommand(1L, "H1", List.of(
                new Guest("Mila Kunis"))), RoomDto.class);
        template.put("/api/rooms/" + roomDto.getId(), new UpdateRoomCommand("H2"));
        RoomDto result = template.exchange("/api/rooms/" + roomDto.getId(),
                HttpMethod.GET,
                null,
                RoomDto.class)
                .getBody();
        assert result != null;
        assertEquals("H2", result.getRoomNumber());
    }


    @Test
    @DisplayName(value = "Saving a room with wrong parameters")
    void saveRoomWithWrongParamsTest() {
        Problem room = template.postForObject("/api/rooms", new CreateRoomCommand(1L, "", List.of(
                new Guest("Mila Kunis"))), Problem.class);
        assertEquals(400, Objects.requireNonNull(room.getStatus()).getStatusCode());
    }


    @Test
    @DisplayName(value = "Deleting a room by ID")
    void deleteRoomByIdTest() {
        RoomDto roomDto = template.postForObject("/api/rooms", new CreateRoomCommand(1L, "H1", List.of(
                new Guest("Mila Kunis"))), RoomDto.class);
        template.delete("/api/rooms/" + roomDto.getId());
        List<RoomDto> result = template.exchange("/api/rooms",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RoomDto>>(){})
                .getBody();
        assertTrue(Objects.requireNonNull(result).isEmpty());
    }
}
