package io.github.robertograham.fortniteapirestclient.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FortniteApiError {

    private String errorCode;
    private String errorMessage;
    private List<String> messageVars;
    private int numericErrorCode;
    private String originatingService;
    private String intent;
    @JsonProperty("error_description")
    private String errorDescription;
    private String error;

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
                '}';
    }

}
