package bookings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    @Query("select guest from Guest guest left join fetch guest.room where guest.name like :prefix%")
    List<Guest> findGuestByPrefix(@Param("prefix") String prefix);

    List<Guest> findGuestByNameStartsWith(String prefix);
}
