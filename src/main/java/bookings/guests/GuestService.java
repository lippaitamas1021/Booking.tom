package bookings.guests;

import bookings.EntityNotFoundException;
import bookings.rooms.CreateRoomCommand;
import bookings.rooms.Room;
import bookings.rooms.RoomRepository;
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
        Type targetListType = new TypeToken<List<GuestDto>>(){}.getType();
        return modelMapper.map(guestRepository.findAll(), targetListType); }


    public GuestDto findGuestById(long id) {
        return modelMapper.map(findGuest(id), GuestDto.class); }


    public Guest findGuest(long id) {
        return guestRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id, "Guest"));}


    public GuestDto saveGuest(CreateGuestCommand command) {
        Guest guest = new Guest(command.getName());
        guestRepository.save(guest);
        return modelMapper.map(guest, GuestDto.class); }


    @Transactional
    public GuestDto addRoomById(Long id, CreateRoomCommand command) {
        Guest guest = findGuest(id);
        Room room = new Room(command.getRoomNumber());
        roomRepository.save(room);
        guest.setRoom(room);
        return modelMapper.map(guest, GuestDto.class); }


    @Transactional
    public GuestDto updateGuestById(long id, UpdateGuestCommand command) {
        Guest guest = findGuest(id);
        guest.setName(command.getName());
        Guest result = guestRepository.save(guest);
        return modelMapper.map(result, GuestDto.class); }


    public void deleteGuestById(Long id) {
        if(!guestRepository.existsById(id)) {
            throw new EntityNotFoundException(id, "Guest");
        }
        guestRepository.deleteById(id); }


    public void deleteAll() {
        guestRepository.deleteAll(); }}
