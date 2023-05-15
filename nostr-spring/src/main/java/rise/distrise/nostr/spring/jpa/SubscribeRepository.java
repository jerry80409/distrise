package rise.distrise.nostr.spring.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rise.distrise.nostr.spring.jpa.entity.SubscribeEntity;

@Repository
public interface SubscribeRepository extends JpaRepository<SubscribeEntity, Long>, JpaSpecificationExecutor<SubscribeEntity> {

}