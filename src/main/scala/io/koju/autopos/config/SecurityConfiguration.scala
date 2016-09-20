package io.koju.autopos.config

import io.koju.autopos.security.jwt.{JWTConfigurer, TokenProvider}
import io.koju.autopos.security.{AuthoritiesConstants, Http401UnauthorizedEntryPoint}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.{HttpSecurity, WebSecurity}
import org.springframework.security.config.annotation.web.configuration.{EnableWebSecurity, WebSecurityConfigurerAdapter}
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfiguration(private val authenticationEntryPoint: Http401UnauthorizedEntryPoint,
                            private val userDetailsServiceProvider: UserDetailsService,
                            private val tokenProvider: TokenProvider)
  extends WebSecurityConfigurerAdapter {

  @Bean
  def passwordEncoder: PasswordEncoder = new BCryptPasswordEncoder

  @Autowired
  def configureGlobal(auth: AuthenticationManagerBuilder) {
    auth
      .userDetailsService(userDetailsServiceProvider)
      .passwordEncoder(passwordEncoder)
  }

  override def configure(web: WebSecurity) {
    web.ignoring
      .antMatchers(HttpMethod.OPTIONS, "/**")
      .antMatchers("/app/**/*.{js,html}")
      .antMatchers("/bower_components/**")
      .antMatchers("/i18n/**")
      .antMatchers("/content/**")
      .antMatchers("/test/**")
  }

  override protected def configure(http: HttpSecurity) {
    http
      .exceptionHandling
      .authenticationEntryPoint(authenticationEntryPoint)
    .and
      .csrf.disable
      .headers.frameOptions.disable
    .and
      .sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    .and
      .authorizeRequests
      .antMatchers("/api/register").permitAll
      .antMatchers("/api/activate").permitAll
      .antMatchers("/api/authenticate").permitAll
      .antMatchers("/api/account/reset_password/init").permitAll
      .antMatchers("/api/account/reset_password/finish").permitAll
      .antMatchers("/api/logs/**").hasAuthority(AuthoritiesConstants.ADMIN)
      .antMatchers("/api/audits/**").hasAuthority(AuthoritiesConstants.ADMIN)
      .antMatchers("/api/**").authenticated
      .antMatchers("/metrics/**").hasAuthority(AuthoritiesConstants.ADMIN)
      .antMatchers("/health/**").hasAuthority(AuthoritiesConstants.ADMIN)
      .antMatchers("/trace/**").hasAuthority(AuthoritiesConstants.ADMIN)
      .antMatchers("/dump/**").hasAuthority(AuthoritiesConstants.ADMIN)
      .antMatchers("/shutdown/**").hasAuthority(AuthoritiesConstants.ADMIN)
      .antMatchers("/beans/**").hasAuthority(AuthoritiesConstants.ADMIN)
      .antMatchers("/configprops/**").hasAuthority(AuthoritiesConstants.ADMIN)
      .antMatchers("/info/**").hasAuthority(AuthoritiesConstants.ADMIN)
      .antMatchers("/autoconfig/**").hasAuthority(AuthoritiesConstants.ADMIN)
      .antMatchers("/env/**").hasAuthority(AuthoritiesConstants.ADMIN)
      .antMatchers("/mappings/**").hasAuthority(AuthoritiesConstants.ADMIN)
      .antMatchers("/liquibase/**").hasAuthority(AuthoritiesConstants.ADMIN)
      .antMatchers("/configuration/security").permitAll
      .antMatchers("/configuration/ui").permitAll
      .antMatchers("/protected/**").authenticated
    .and
      .apply(securityConfigurerAdapter)
  }

  private def securityConfigurerAdapter: JWTConfigurer = new JWTConfigurer(tokenProvider)

  @Bean
  def securityEvaluationContextExtension: SecurityEvaluationContextExtension = new SecurityEvaluationContextExtension
}