package bookings;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    private final GuestRepository guestRepository;

    private final ModelMapper modelMapper;

    public List<RoomDto> listRooms() {
        List<Room> rooms = roomRepository.findAll();
        Type targetType = new TypeToken<List<RoomDto>>(){}.getType();
        return modelMapper.map(rooms, targetType);
    }

    public RoomDto findRoomById(long id) {
        Room room = roomRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id, "Room"));
        return modelMapper.map(room, RoomDto.class);
    }

    public RoomDto saveRoom(Long id, CreateRoomCommand command) {
        Guest guest = guestRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id, "Guest"));
        Room room = new Room(command.getId(), command.getRoomNumber(), command.getGuests());
        room.addGuests(guest);
        roomRepository.save(room);
        return modelMapper.map(room, RoomDto.class);
    }

    @Transactional
    public RoomDto updateRoomById(Long id, UpdateRoomCommand command) {
        Room room = roomRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id, "Room"));
        room.setRoomNumber(command.getRoomNumber());
        return modelMapper.map(room, RoomDto.class);
    }

    public void deleteRoomById(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new EntityNotFoundException(id, "Room");
        }
        roomRepository.deleteById(id);
    }

    public void deleteAllRooms() {
        roomRepository.deleteAll();
    }


}
