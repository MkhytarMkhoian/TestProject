package com.test.project.features.developer_settings;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.inject.Inject;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import timber.log.Timber;

public class CurlInterceptor implements Interceptor {

  @Inject public CurlInterceptor() {
  }

  @Override public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();

    logAsCurlCommand(request);

    return chain.proceed(request);
  }

  private void logAsCurlCommand(Request request) throws IOException {
    final Charset UTF8 = StandardCharsets.UTF_8;
    boolean compressed = false;

    StringBuilder curlCmd = new StringBuilder("curl");
    curlCmd.append(" -X ").append(request.method());

    Headers headers = request.headers();
    for (int i = 0, count = headers.size(); i < count; i++) {
      String name = headers.name(i);
      String value = headers.value(i);
      if ("Accept-Encoding".equalsIgnoreCase(name) && "gzip".equalsIgnoreCase(value)) {
        compressed = true;
      }
      curlCmd.append(" -H " + "\"").append(name).append(": ").append(value).append("\"");
    }

    RequestBody requestBody = request.body();
    if (requestBody != null) {
      Buffer buffer = new Buffer();
      requestBody.writeTo(buffer);
      Charset charset = UTF8;
      MediaType contentType = requestBody.contentType();
      if (contentType != null) {
        charset = contentType.charset(UTF8);
      }
      // try to keep to a single line and use a subshell to preserve any line breaks
      curlCmd.append(" --data $'").append(buffer.readString(charset).replace("\n", "\\n")).append("'");
    }

    curlCmd.append((compressed) ? " --compressed " : " ").append(request.url());

    Timber.d("╭--- cURL (" + request.url() + ")");
    Timber.d(curlCmd.toString());
    Timber.d("╰--- (copy and paste the above line to a terminal)");
  }
}
