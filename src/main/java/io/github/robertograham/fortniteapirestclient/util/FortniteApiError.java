package io.github.robertograham.fortniteapirestclient.util;

import com.google.api.client.util.Key;

import java.util.List;

public class FortniteApiError {

    @Key
    private String errorCode;
    @Key
    private String errorMessage;
    @Key
    private List<String> messageVars;
    @Key
    private int numericErrorCode;
    @Key
    private String originatingService;
    @Key
    private String intent;
    @Key("error_description")
    private String errorDescription;
    @Key
    private String error;
    @Key
    private String trackingId;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<String> getMessageVars() {
        return messageVars;
    }

    public void setMessageVars(List<String> messageVars) {
        this.messageVars = messageVars;
    }

    public int getNumericErrorCode() {
        return numericErrorCode;
    }

    public void setNumericErrorCode(int numericErrorCode) {
        this.numericErrorCode = numericErrorCode;
    }

    public String getOriginatingService() {
        return originatingService;
    }

    public void setOriginatingService(String originatingService) {
        this.originatingService = originatingService;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    @Override
    public String toString() {
        return "FortniteApiError{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", messageVars=" + messageVars +
                ", numericErrorCode=" + numericErrorCode +
                ", originatingService='" + originatingService + '\'' +
                ", intent='" + intent + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                ", error='" + error + '\'' +
                ", trackingId='" + trackingId + '\'' +
                '}';
    }
}
