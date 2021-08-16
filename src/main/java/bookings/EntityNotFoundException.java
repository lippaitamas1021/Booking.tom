package bookings;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import java.net.URI;

public class EntityNotFoundException extends AbstractThrowableProblem {

    public EntityNotFoundException(long id, String entity) {
        super(URI.create("bookings/" + entity + "-not-found"),
                "Entity not found",
                Status.NOT_FOUND,
                String.format("Entity '%s' with ID: '%d' not found", entity, id));
    }
}