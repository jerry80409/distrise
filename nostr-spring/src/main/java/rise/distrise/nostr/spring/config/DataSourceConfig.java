package rise.distrise.nostr.spring.config;

import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import net.ttddyy.dsproxy.listener.ChainListener;
import net.ttddyy.dsproxy.listener.DataSourceQueryCountListener;
import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {

  /**
   * Just follow from
   */
  @Bean
  @Primary
  public DataSource dataSource(DataSourceProperties properties) {
    final ChainListener listener = new ChainListener();
    listener.addListener(new DataSourceQueryCountListener());
    return ProxyDataSourceBuilder.create(properties.initializeDataSourceBuilder().build()).name("SQL-Trace")
      .listener(listener).asJson().countQuery().logQueryBySlf4j(SLF4JLogLevel.DEBUG, "io.roach.sql_trace")
      .logSlowQueryBySlf4j(50, TimeUnit.MILLISECONDS).multiline().build();
  }
}