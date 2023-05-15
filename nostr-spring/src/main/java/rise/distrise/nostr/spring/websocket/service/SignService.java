package rise.distrise.nostr.spring.websocket.service;

import java.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rise.distrise.nostr.core.message.event.EventMessage;
import rise.distrise.nostr.core.utils.Signer;

@Slf4j
@Service
public class SignService {

  public boolean verify(EventMessage message) throws SignatureException {
    final String pubkey = message.getContent().getPubkey();
    final String sig = message.getContent().getSig();
    return Signer.verify(message.getContent().getContent(), pubkey, sig);
  }
}