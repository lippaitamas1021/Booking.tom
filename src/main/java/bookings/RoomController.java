package bookings;

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
    public RoomDto findRoomById(@PathVariable long id) {
        return roomService.findRoomById(id);
    }


    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Tag(name = "POST")
    @Operation(summary = "Saving a room", description = "This option is for saving a new room")
    @ApiResponse(responseCode = "201", description = "Room has been saved")
    public RoomDto saveRoom(@PathVariable("id") Long id, @RequestBody @Valid CreateRoomCommand command) {
        return roomService.saveRoom(id, command);
    }


    @PutMapping("/{id}")
    @Tag(name = "PUT")
    @Operation(summary = "Updating room", description = "This option is for updating a room by ID")
    public RoomDto updateRoomById(@PathVariable("id") Long id, @RequestBody @Valid UpdateRoomCommand command) {
        return roomService.updateRoomById(id, command);
    }


    @DeleteMapping("/{id}")
    @Tag(name = "DELETE")
    @Operation(summary = "Deleting a room", description = "This option is for deleting one room by ID")
    @ApiResponse(responseCode = "204", description = "Room has been deleted")
    public void deleteRoomById(@PathVariable("id") Long id) {
        roomService.deleteRoomById(id);
    }


    @DeleteMapping
    @Tag(name = "DELETE")
    @Operation(summary = "Deleting all rooms", description = "This option is for deleting all the rooms")
    @ApiResponse(responseCode = "204", description = "All rooms have been deleted")
    public void deleteAllRooms() {
        roomService.deleteAllRooms();
    }
}
