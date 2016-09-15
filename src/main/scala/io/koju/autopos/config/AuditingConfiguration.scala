package io.koju.autopos.config

import io.koju.autopos.security.SecurityUtils
import io.koju.autopos.user.domain.User
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@Configuration
@EnableJpaAuditing
class AuditingConfiguration {

  @Bean
  def auditorAware: AuditorAware[User] = new AuditorAware[User] {
    override def getCurrentAuditor = SecurityUtils.getCurrentUser
  }
}