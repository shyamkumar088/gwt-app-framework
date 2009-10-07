package org.gwtaf.eventbus.place;

/**
 * PlaceParsingException
 * 
 * @author David Peterson
 */
public class PlaceParsingException extends Exception {

    public PlaceParsingException() {
    }

    public PlaceParsingException( String message ) {
        super( message );
    }

    public PlaceParsingException( Throwable cause ) {
        super( cause );
    }

    public PlaceParsingException( String message, Throwable cause ) {
        super( message, cause );
    }

}
