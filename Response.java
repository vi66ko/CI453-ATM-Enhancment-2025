class Response {
    final Status status = null;
    final String message = null;

    public void Response(Status status, int satusCode, String message) {
        this.status = status;
        this.satusCode = satusCode
        this.message = message;
    }
}

enum Status {
    200,
    404,
    400
}
