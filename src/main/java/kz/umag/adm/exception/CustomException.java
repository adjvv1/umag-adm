package kz.umag.adm.exception;

import kz.umag.adm.type.ErrorType;

import java.text.MessageFormat;

public class CustomException extends AppException {

    public CustomException(String inMessage) {
        this(ErrorType.UNDEFINED, inMessage);
    }

    public CustomException(ErrorType errorType, String inMessage) {
        super(formatMessage(errorType, inMessage));
    }

    private static String formatMessage(ErrorType errorType, String inMessage) {
        if (errorType == ErrorType.UNDEFINED) {
            return inMessage;
        }
        return MessageFormat.format("{0}: {1}", errorType.getMessage(), inMessage);
    }


}
