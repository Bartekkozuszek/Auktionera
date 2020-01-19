package se.iths.auktionera.api;

import org.springframework.context.ApplicationEvent;

public class IncomingQueryEvent extends ApplicationEvent {
    public IncomingQueryEvent(Object source) {
        super(source);
    }


}
