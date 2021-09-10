package bookings.rooms;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
@AllArgsConstructor
@Tag(name = "Operations on rooms")
public class RoomController {

    private final RoomService roomService;


    @GetMapping
    @Tag(name = "GET")
    @Operation(summary = "Listing rooms", description = "This option is for listing all the rooms")
    public List<RoomDto> listRooms(Optional<String> roomNumber) {return roomService.listRooms(roomNumber);}


    @GetMapping("/{id}")
    @Tag(name = "GET")
    @Operation(summary = "Finding a room by ID", description = "This option is for finding a room by ID")
    @ApiResponse(responseCode = "404", description = "Room not found")
    public RoomDto findRoomById(@PathVariable("id") long id) {return roomService.findRoomById(id); }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Tag(name = "POST")
    @Operation(summary = "Saving a room", description = "This option is for saving a new room")
    @ApiResponse(responseCode = "201", description = "New room has been saved")
    @ApiResponse(responseCode = "400", description = "Validation exception while saving a room")
    public RoomDto saveRoom(@Valid @RequestBody CreateRoomCommand command) {
        return roomService.saveRoom(command);
    }


    @PostMapping("/{id}/guest")
    @Tag(name= "POST")
    @Operation(summary = "Adding a new guest", description = "This option is for adding a new guest to the room")
    public RoomDto addNewGuestToExistingRoom(@PathVariable("id") long id, @Valid @RequestBody AddNewGuestCommand command) {
        return roomService.addNewGuestToExistingRoom(id, command); }


    @PutMapping("/{id}/guest")
    @Tag(name = "PUT")
    @Operation(summary = "Adding an existing guest", description = "This option is for adding an existing guest to an existing room")
    public RoomDto addExistingGuestToExistingRoom(@PathVariable("id") int id, @Valid @RequestBody AddExistingGuestCommand command) {
        return roomService.addExistingGuestToExistingRoom(id, command);}


    @PutMapping("/{id}")
    @Tag(name = "PUT")
    @Operation(summary = "Updating a room", description = "This option is for updating a room by ID")
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
    }}