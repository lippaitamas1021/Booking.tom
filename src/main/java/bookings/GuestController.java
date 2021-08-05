package bookings;

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
@RequestMapping("/api/guests")
@Tag(name = "Operations on guests")
@AllArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @GetMapping()
    @Tag(name = "GET")
    @Operation(summary = "Listing guests", description = "This option is for listing the guests, optionally listing by name")
    public List<GuestDto> listGuests(@RequestParam Optional<String> name) {
        return guestService.listGuests(name);
    }


    @GetMapping("/{id}")
    @Tag(name = "GET")
    @Operation(summary = "Finding guest by ID", description = "This option is for finding a guest by ID")
    @ApiResponse(responseCode = "404", description = "Guest not found")
    public GuestDto findGuestById(@PathVariable("id") Long id) {
        return guestService.findGuestById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Tag(name = "POST")
    @Operation(summary = "Saving a new guest", description = "This option is for saving a new guest")
    @ApiResponse(responseCode = "201", description = "New guest has been saved")
    public GuestDto saveGuest(@RequestBody @Valid CreateGuestCommand command) {
        return guestService.saveGuest(command);
    }


    @PostMapping("/{id}/rooms")
    @Tag(name = "POST")
    @Operation(summary = "Adding rooms", description = "This option is for adding rooms to the guest")
    public GuestDto addRooms(@PathVariable("id") Long id, @RequestBody @Valid AddRoomCommand command) {
        return guestService.addRoomsById(id, command);
    }


    @PutMapping("/{id}")
    @Tag(name = "PUT")
    @Operation(summary = "Updating guest")
    public GuestDto updateGuestById(@PathVariable("id") long id, @RequestBody @Valid UpdateGuestCommand command) {
        return guestService.updateGuestById(id, command);
    }


    @DeleteMapping("/{id}")
    @Tag(name = "DELETE")
    @Operation(summary = "Deleting one guest", description = "This option is for deleting one guest")
    public void deleteGuest(@PathVariable("id") Long id) {
        guestService.deleteGuestById(id);
    }


    @DeleteMapping
    @Tag(name = "DELETE")
    @Operation(summary = "Deleting all the guests", description = "This option is for deleting all the guests")
    public void deleteAllGuests() {
        guestService.deleteAll();
    }
}