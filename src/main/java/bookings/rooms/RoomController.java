package bookings.rooms;

import bookings.guests.CreateGuestCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@AllArgsConstructor
@Tag(name = "Operations on rooms")
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    @Tag(name = "GET")
    @Operation(summary = "Listing rooms", description = "This option is for listing all the rooms")
    public List<RoomDto> listRooms() {
        return roomService.listRooms();
    }


    @GetMapping("/{id}")
    @Tag(name = "GET")
    @Operation(summary = "Finding a room by ID", description = "This option is for finding a room by ID")
    @ApiResponse(responseCode = "404", description = "Room not found")
    public RoomDto findRoomById(@PathVariable("id") long id) {
        return roomService.findRoom(id); }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Tag(name = "POST")
    @Operation(summary = "Saving a room", description = "This option is for saving a new room")
    @ApiResponse(responseCode = "201", description = "New room has been saved")
    @ApiResponse(responseCode = "400", description = "Validation exception while saving a room")
    public RoomDto saveRoom(@Valid @RequestBody CreateRoomCommand command) {
        return roomService.saveRoom(command);
    }


    @PostMapping("/{id}/guests")
    @Operation(summary = "Adding guest", description = "This option is for adding a new guest to the room")
    public RoomDto addGuest(@PathVariable("id") long id, @Valid @RequestBody CreateGuestCommand command) {
        return roomService.addGuest(id, command); }


    @PutMapping("/{id}/room")
    @Tag(name = "PUT")
    @Operation(summary = "Updating room", description = "This option is for updating a room by ID")
    @ApiResponse(responseCode = "200", description = "Room has been updated")
    @ApiResponse(responseCode = "400", description = "Room update is not allowed")
    public RoomDto updateRoomById(@PathVariable("id") long id, @Valid @RequestBody UpdateRoomCommand command) {
        return roomService.updateRoomById(id, command); }


    @DeleteMapping("/{id}")
    @Tag(name = "DELETE")
    @Operation(summary = "Deleting a room", description = "This option is for deleting one room by ID")
    @ApiResponse(responseCode = "204", description = "Room has been deleted")
    @ApiResponse(responseCode = "404", description = "Room not found")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoomById(@PathVariable("id") long id) {
        roomService.deleteRoomById(id);
    }


    @DeleteMapping
    @Tag(name = "DELETE")
    @Operation(summary = "Deleting all rooms", description = "This option is for deleting all the rooms")
    @ApiResponse(responseCode = "204", description = "Rooms have been deleted")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllRooms() {
        roomService.deleteAllRooms();
    }
}
