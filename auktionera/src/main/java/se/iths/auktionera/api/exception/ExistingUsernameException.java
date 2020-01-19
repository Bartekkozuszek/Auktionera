package se.iths.auktionera.api.exception;

import java.sql.SQLException;

public class ExistingUsernameException extends SQLException {
    public ExistingUsernameException(String message) {super(message);}
}
