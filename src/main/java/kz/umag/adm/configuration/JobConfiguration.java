package kz.umag.adm.configuration;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;


/**
 * The configuration is for configuring launch and running process of scheduled tasks <i>(jobs)</i>
 */
@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "30m", defaultLockAtLeastFor = "1m")
public class JobConfiguration {

    /**
     * Configures {@link LockProvider} instance for jobs blocking <i>(in order to avoid parallel running of several the
     * same jobs in different instances of the application)</i>
     *
     * @param dataSource the data source
     *
     * @return configured lock provider
     */
    @Bean
    public LockProvider lockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(
                JdbcTemplateLockProvider.Configuration
                        .builder()
                        .withJdbcTemplate(new JdbcTemplate(dataSource))
                        .withTableName("umag_bill.shedlock")
                        .withColumnNames(new JdbcTemplateLockProvider.ColumnNames("job_name",
                                                                                  "lock_until",
                                                                                  "locked_at",
                                                                                  "locked_by"))
                        .usingDbTime()
                        .build()
        );
    }
}
