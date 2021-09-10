package bookings.rooms;

import bookings.EntityNotFoundException;
import bookings.guests.Guest;
import bookings.guests.GuestRepository;
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
public class RoomService {

    private final RoomRepository roomRepository;

    private final ModelMapper modelMapper;

    private final GuestRepository guestRepository;


    public List<RoomDto> listRooms(Optional<String> roomNumber) {
        Type targetType = new TypeToken<List<RoomDto>>(){}.getType();
        return modelMapper.map(roomRepository.findAll(), targetType); }


    public RoomDto findRoomById(long id) {
        return modelMapper.map(findRoom(id), RoomDto.class); }


    public Room findRoom(long id) {
        return roomRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id, "Room"));}


    public RoomDto saveRoom(CreateRoomCommand command) {
        Room room = new Room(command.getRoomNumber());
        Room result = roomRepository.save(room);
        return modelMapper.map(result, RoomDto.class); }


    @Transactional
    public RoomDto addNewGuestToExistingRoom(long id, AddNewGuestCommand command) {
        Room room = findRoom(id);
        Guest guest = new Guest(command.getName());
        room.addGuests(guest);
        guest.setRoom(room);
        return modelMapper.map(room, RoomDto.class); }


    @Transactional
    public RoomDto addExistingGuestToExistingRoom(long id, AddExistingGuestCommand command) {
        Room room = findRoom(id);
        Guest guest = guestRepository.findById(command.getId()).orElseThrow(()-> new EntityNotFoundException(command.getId(), "Guest"));
        room.addGuests(guest);
        guest.setRoom(room);
        return modelMapper.map(room, RoomDto.class); }


    @Transactional
    public RoomDto updateRoomById(Long id, UpdateRoomCommand command) {
        Room room = findRoom(id);
        room.setRoomNumber(command.getRoomNumber());
        Room result = roomRepository.save(room);
        return modelMapper.map(result, RoomDto.class); }


    public void deleteRoomById(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new EntityNotFoundException(id, "Room");
        }
        roomRepository.deleteById(id); }


    public void deleteAllRooms() {
        roomRepository.deleteAll();
    }}