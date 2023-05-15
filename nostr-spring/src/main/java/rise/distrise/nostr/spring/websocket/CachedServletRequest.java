package rise.distrise.nostr.spring.websocket;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.ServerHttpAsyncRequestControl;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StreamUtils;

/**
 * 用來攔截 WebSocket Request
 */
public class CachedServletRequest implements ServerHttpRequest {

  private ServerHttpRequest request;

  private byte[] cachedPayload;

  public CachedServletRequest(ServerHttpRequest request) throws IOException {
    this.request = request;
    this.cachedPayload = StreamUtils.copyToByteArray(request.getBody());
  }

  @Override
  public Principal getPrincipal() {
    return request.getPrincipal();
  }

  @Override
  public InetSocketAddress getLocalAddress() {
    return request.getLocalAddress();
  }

  @Override
  public InetSocketAddress getRemoteAddress() {
    return request.getRemoteAddress();
  }

  @Override
  public ServerHttpAsyncRequestControl getAsyncRequestControl(ServerHttpResponse response) {
    return request.getAsyncRequestControl(response);
  }

  @Override
  public InputStream getBody() throws IOException {
    return new ByteArrayInputStream(this.cachedPayload);
  }

  @Override
  public HttpMethod getMethod() {
    return request.getMethod();
  }

  @Override
  public URI getURI() {
    return request.getURI();
  }

  @Override
  public HttpHeaders getHeaders() {
    return request.getHeaders();
  }

  public Reader getBodyReader() throws IOException {
    return new BufferedReader(new InputStreamReader(getBody()));
  }
}