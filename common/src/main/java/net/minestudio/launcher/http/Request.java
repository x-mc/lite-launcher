package net.minestudio.launcher.http;


import java.util.LinkedHashMap;
import java.util.Map;

public class Request {
    public enum Method {
        POST
    }
    private final Webb webb;
    final Method method;
    final String uri;
    Map<String, Object> headers = new LinkedHashMap<String, Object>();
    Object payload;

    Request(Webb webb, Method method, String uri) {
        this.webb = webb;
        this.method = method;
        this.uri = uri;
    }

    public Request header(String name, Object value) {
        headers.put(name, value);
        return this;
    }

    public Request body(Object body) {
        this.payload = body;
        return this;
    }


    public <T> T toClass(Class<?> t) {
      return (T) webb.execute(this, t);
    }
}