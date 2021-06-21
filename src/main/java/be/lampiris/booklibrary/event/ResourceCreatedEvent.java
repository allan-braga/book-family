package be.lampiris.booklibrary.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

public class ResourceCreatedEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    @Getter
    private final HttpServletResponse response;

    @Getter
    private final Long id;

    public ResourceCreatedEvent(Object source, final HttpServletResponse response, final Long id) {
        super(source);
        this.response = response;
        this.id = id;
    }
}
