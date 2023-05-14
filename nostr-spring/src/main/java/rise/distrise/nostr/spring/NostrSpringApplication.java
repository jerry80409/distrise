package rise.distrise.nostr.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@SpringBootApplication
public class NostrSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(NostrSpringApplication.class, args);
	}

}