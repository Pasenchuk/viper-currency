package com.sbt.currency.exceptions;

/**
 * Created by Pasenchuk Victor on 28/04/2017
 */

public class RequestError extends Throwable {

    private Kind kind;

    public RequestError(Throwable cause, Kind kind) {
        super(cause.getMessage(), cause);
        this.kind = kind;
    }

    public Kind getKind() {
        return kind;
    }

    public enum Kind {
        PROTOCOL_ERROR,
        NETWORK_ERROR,
        IO_ERROR,
        UNKNOWN_ERROR
    }

}
