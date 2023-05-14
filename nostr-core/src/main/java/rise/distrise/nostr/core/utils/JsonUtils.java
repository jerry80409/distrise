package rise.distrise.nostr.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtils {

  public static final ObjectMapper JSON = new ObjectMapper();
}