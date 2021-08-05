package bookings;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    private final ModelMapper modelMapper;

    private final RoomRepository roomRepository;

    public List<GuestDto> listGuests(Optional<String> name) {
        List<Guest> guests = guestRepository.findAll();
        Type targetListType = new TypeToken<List<GuestDto>>(){}.getType();
        return modelMapper.map(guests, targetListType);
    }

    public GuestDto saveGuest(CreateGuestCommand command) {
        Guest guest = new Guest(command.getName());
        Guest result = guestRepository.save(guest);
        return modelMapper.map(result, GuestDto.class);
    }

    @Transactional
    public GuestDto addRoomsById(Long id, AddRoomCommand command) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, "Guest"));
        Room room = new Room(command.getRoomNumber());
        guest.setRoom(room);
        roomRepository.save(room);
        return modelMapper.map(guest, GuestDto.class);
    }

    public void deleteGuestById(Long id) {
        if(!guestRepository.existsById(id)) {
            throw new EntityNotFoundException(id, "Guest");
        }
        guestRepository.deleteById(id);
    }

    public GuestDto findGuestById(Long id) {
        Guest guest = guestRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Guest"));
        return modelMapper.map(guest, GuestDto.class);
    }

    @Transactional
    public GuestDto updateGuestById(long id, UpdateGuestCommand command) {
        Guest guest = guestRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id, "Guest"));
        guest.setName(command.getName());
        return modelMapper.map(guest, GuestDto.class);
    }

    public void deleteAll() {
        guestRepository.deleteAll();
    }
}
