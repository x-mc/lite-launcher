package net.minestudio.launcher.http;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class WebbUtils {



  static void addRequestProperties(HttpURLConnection connection, Map<String, Object> map) {
    if (map == null || map.isEmpty()) {
      return;
    }
    for (Map.Entry<String, Object> entry : map.entrySet()) {
      addRequestProperty(connection, entry.getKey(), entry.getValue());
    }
  }

  static void addRequestProperty(HttpURLConnection connection, String name, Object value) {
    String valueAsString = value.toString();
    connection.addRequestProperty(name, valueAsString);
  }

  static byte[] getPayloadAsBytesAndSetContentType(
    HttpURLConnection connection, Request request, int jsonIndentFactor) {
    byte[] requestBody = null;
    String bodyStr = null;

    bodyStr = request.payload.toString();
    if (bodyStr != null) {
      requestBody = bodyStr.getBytes(StandardCharsets.UTF_8);
    }

    if (requestBody == null) {
      throw new IllegalStateException();
    }

    connection.setFixedLengthStreamingMode(requestBody.length);

    return requestBody;
  }

  static InputStream wrapStream(String contentEncoding, InputStream inputStream) throws IOException {
  return inputStream;
  }

  static <T> void parseResponseBody(Class<T> clazz, Response<T> response, InputStream responseBodyStream)
    throws IOException {

    response.setBody(new Gson().fromJson(new InputStreamReader(responseBodyStream), clazz));

  }
}
