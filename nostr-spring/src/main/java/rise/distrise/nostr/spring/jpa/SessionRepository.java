package rise.distrise.nostr.spring.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rise.distrise.nostr.spring.jpa.entity.SessionEntity;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, String>, JpaSpecificationExecutor<SessionEntity> {

  Optional<SessionEntity> findBySession(String sessionId);
}