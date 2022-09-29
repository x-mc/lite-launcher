package net.minestudio.launcher.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class Webb {
  static final Map<String, Object> globalHeaders = new LinkedHashMap<>();

  Map<String, Object> defaultHeaders;

  public static Webb create() {
    return new Webb();
  }

  public Request post(String pathOrUri) {
    return new Request(this, Request.Method.POST, pathOrUri);
  }


  public  <T> Response<T> execute(Request request, Class<T> clazz) {
    Response<T> response = new Response<T>(request);

    InputStream is;
    HttpURLConnection connection;

    try {
      String uri = request.uri;

      URL apiUrl = new URL(uri);
      connection = (HttpURLConnection) apiUrl.openConnection();
      connection.setRequestMethod(request.method.name());
      WebbUtils.addRequestProperties(connection, mergeHeaders(request.headers));

      byte[] requestBody = WebbUtils.getPayloadAsBytesAndSetContentType(
        connection, request, -1);
      connection.setDoOutput(true);
      writeBody(connection, requestBody);
      response.statusCode = connection.getResponseCode();
      is = response.isSuccess() ? connection.getInputStream() : connection.getErrorStream();
      is = WebbUtils.wrapStream(connection.getContentEncoding(), is);

      if (response.isSuccess()) {
        WebbUtils.parseResponseBody(clazz, response, is);
      }
      return response;
    } catch (Exception ignored) {
    }
    return null;
  }

  private void writeBody(HttpURLConnection connection, byte[] body) throws IOException {
    OutputStream os = null;
    try {
      os = connection.getOutputStream();
      os.write(body);
      os.flush();
    } finally {
      if (os != null) {
        try {
          os.close();
        } catch (Exception ignored) {
        }
      }
    }
  }

  Map<String, Object> mergeHeaders(Map<String, Object> requestHeaders) {
    Map<String, Object> headers = null;
    if (!globalHeaders.isEmpty()) {
      headers = new LinkedHashMap<String, Object>();
      headers.putAll(globalHeaders);
    }
    if (defaultHeaders != null) {
      if (headers == null) {
        headers = new LinkedHashMap<String, Object>();
      }
      headers.putAll(defaultHeaders);
    }
    if (requestHeaders != null) {
      if (headers == null) {
        headers = requestHeaders;
      } else {
        headers.putAll(requestHeaders);
      }
    }
    return headers;
  }
}
