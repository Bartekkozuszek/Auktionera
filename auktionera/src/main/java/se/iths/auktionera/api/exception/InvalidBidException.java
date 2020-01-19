package se.iths.auktionera.api.exception;

public class InvalidBidException extends RuntimeException{

        public InvalidBidException(String message) {
            super(message);
        }

}
