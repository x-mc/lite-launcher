package net.minestudio.launcher.http;

public class Response<T> {
    final Request request;

    int statusCode;
    T body;

    Response(Request request) {
        this.request = request;
    }

    void setBody(Object body) {
        this.body = (T) body;
    }

    public T getBody() {
        return body;
    }

    public boolean isSuccess() {
        return (statusCode / 100) == 2;
    }
}
