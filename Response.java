import type.ErrorType;

class Response {

    private Boolean status = false;
    private String message = null;
    private ErrorType errorType = null;
    private String data = null;

    public Response(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public Response(Boolean status, String message, ErrorType type) {
        this.status = status;
        this.message = message;
        this.errorType = type;
    }

    public Response(Boolean status, String message, String data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Boolean isSuccessful() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public String getData() {
        return data;
    }
}
