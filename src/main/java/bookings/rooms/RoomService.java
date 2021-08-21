package bookings.rooms;

import bookings.EntityNotFoundException;
import bookings.guests.CreateGuestCommand;
import bookings.guests.Guest;
import bookings.guests.GuestRepository;
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

    private final ModelMapper modelMapper;


    public Room findRoomById(long id) {
        return roomRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id, "Room"));}


    public RoomDto findRoom(long id) {
        return modelMapper.map(findRoomById(id), RoomDto.class); }


    public RoomDto saveRoom(CreateRoomCommand command) {
        Room room = roomRepository.save(new Room(command.getRoomNumber()));
        return modelMapper.map(room, RoomDto.class); }


    public List<RoomDto> listRooms() {
        Type targetType = new TypeToken<List<RoomDto>>(){}.getType();
        return modelMapper.map(roomRepository.findAll(), targetType); }


    @Transactional
    public RoomDto addGuest(long id, CreateGuestCommand command) {
        Room room = findRoomById(id);
        Guest guest = new Guest(command.getName(), command.getRoom());
        room.addGuests(guest);
        return modelMapper.map(room, RoomDto.class); }


    @Transactional
    public RoomDto updateRoomById(Long id, UpdateRoomCommand command) {
        Room room = findRoomById(id);
        room.setRoomNumber(command.getRoomNumber());
        return modelMapper.map(room, RoomDto.class); }


    public void deleteRoomById(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new EntityNotFoundException(id, "Room");
        }
        roomRepository.deleteById(id); }


    public void deleteAllRooms() {
        roomRepository.deleteAll();
    }
}