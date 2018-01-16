package com.kapilkoju.autopos.config

import com.kapilkoju.autopos.security.SecurityUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@Configuration
@EnableJpaAuditing
class AuditConfiguration {

    @Bean
    fun auditorAware() = AuditorAware { SecurityUtils.getCurrentUser() }

}