package usausa.github.io.work.model;

import java.util.Date;

public class TraceEntry {

    private final Date date;

    private final EventType type;

    private final String message;

    public Date getDate() {
        return date;
    }

    public EventType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public TraceEntry(final Date date, final EventType type, final String message) {
        this.date = date;
        this.type = type;
        this.message = message;
    }
}
