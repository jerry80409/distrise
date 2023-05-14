package rise.distrise.nostr.core.message.req;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import rise.distrise.nostr.core.filter.NeventFilter;

@Builder
@JsonInclude(NON_NULL)
public record ReqContent(String subId, NeventFilter filter) {

}