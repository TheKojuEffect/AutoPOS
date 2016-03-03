package io.koju.autopos.config;

import io.koju.autopos.security.SecurityUtils;
import io.koju.autopos.user.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditingConfiguration {

    @Bean
    public AuditorAware<User> auditorAware() {
        return SecurityUtils::getCurrentUser;
    }
}
