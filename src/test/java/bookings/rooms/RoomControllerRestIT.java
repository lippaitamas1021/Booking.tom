package bookings.rooms;

import bookings.guests.CreateGuestCommand;
import bookings.guests.GuestDto;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from guests", "delete from rooms"})
public class RoomControllerRestIT {

    @Autowired
    TestRestTemplate template;


    @Test
    @DisplayName(value = "Saving a new room")
    void saveRoomTest() {
        RoomDto result = template.postForObject("/api/rooms", new CreateRoomCommand("H1"), RoomDto.class);
        assertEquals("H1", result.getRoomNumber());}


    @Test
    @DisplayName(value = "Listing rooms")
    void listRoomsTest(){
        template.postForObject("/api/rooms", new CreateRoomCommand("H1"), RoomDto.class);
        template.postForObject("/api/rooms", new CreateRoomCommand("H2"), RoomDto.class);
        template.postForObject("/api/rooms", new CreateRoomCommand("H3"), RoomDto.class);
        List<RoomDto> rooms = template.exchange("/api/rooms",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<RoomDto>>() {}).getBody();
        assertThat(rooms).extracting(RoomDto::getRoomNumber).containsExactly("H1", "H2", "H3"); }


    @Test
    @DisplayName(value = "Finding a room by ID")
    void findRoomByIdTest() {
        RoomDto roomDto = template.postForObject("/api/rooms", new CreateRoomCommand("H1"), RoomDto.class);
        RoomDto result = template.exchange("/api/rooms/" + roomDto.getId(),
                HttpMethod.GET, null, RoomDto.class).getBody();
        assert result != null;
        assertEquals("H1", result.getRoomNumber()); }


    @Test
    @DisplayName(value = "Finding a room by name")
    void findRoomByNameTest() {
        template.postForObject("/api/rooms", new CreateRoomCommand("H1"), RoomDto.class);
        List<RoomDto> result = template.exchange("/api/rooms?name=H1",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<RoomDto>>() {}).getBody();
        assert result != null;
        assertEquals(1, result.size()); }


    @Test
    @DisplayName(value = "Adding guest to the romm")
    void addGuestTest() {
        RoomDto room = template.postForObject("/api/rooms", new CreateRoomCommand("H1"), RoomDto.class);
        RoomDto result = template.postForObject("/api/rooms/" + room.getId() + "/guests",
                new CreateGuestCommand("Mila Kunis", new Room("H1")), RoomDto.class, room.getId());
        assertThat(result.getGuests()).extracting(GuestDto::getName).containsExactly("Mila Kunis"); }


    @Test
    @DisplayName(value = "Updating the room number")
    void updateRoomNumberTest() {
        RoomDto room = template.postForObject("/api/rooms", new CreateRoomCommand("H1"), RoomDto.class);
        template.put("/api/rooms/" + room.getId() + "/room", new UpdateRoomCommand("H2"));
        RoomDto result = template.exchange("/api/rooms/" + room.getId(), HttpMethod.GET, null, RoomDto.class).getBody();
        assert result != null;
        assertEquals("H2", result.getRoomNumber()); }


    @Test
    @DisplayName(value = "Saving a room with wrong parameters")
    void saveRoomWithWrongParamsTest() {
        Problem room = template.postForObject("/api/rooms", new CreateRoomCommand(""), Problem.class);
        assertEquals(Status.BAD_REQUEST, room.getStatus()); }


    @Test
    @DisplayName(value = "Deleting a room by ID")
    void deleteRoomByIdTest() {
        RoomDto roomDto = template.postForObject("/api/rooms", new CreateRoomCommand("H1"), RoomDto.class);
        template.delete("/api/rooms/" + roomDto.getId());
        List<RoomDto> result = template.exchange("/api/rooms",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<RoomDto>>(){}).getBody();
        assert result != null;
        assertTrue(result.isEmpty()); }


    @Test
    @DisplayName(value = "Deleting all the rooms")
    void deleteAllRoomsTest() {
        template.postForObject("/api/rooms", new CreateRoomCommand("H1"), RoomDto.class);
        template.postForObject("/api/rooms", new CreateRoomCommand("H2"), RoomDto.class);
        template.postForObject("/api/rooms", new CreateRoomCommand("H3"), RoomDto.class);
        template.delete("/api/rooms");
        List<RoomDto> result = template.exchange("/api/rooms", HttpMethod.GET, null, new ParameterizedTypeReference<List<RoomDto>>(){}).getBody();
        assert result != null;
        assertTrue(result.isEmpty());
    }
}
