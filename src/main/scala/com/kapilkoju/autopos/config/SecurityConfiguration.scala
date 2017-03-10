package com.kapilkoju.autopos.config

import javax.annotation.PostConstruct

import com.kapilkoju.autopos.security._
import com.kapilkoju.autopos.security.jwt._
import io.github.jhipster.security._
import org.springframework.beans.factory.BeanInitializationException
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfiguration(val authenticationManagerBuilder: AuthenticationManagerBuilder,
                            override val userDetailsService: UserDetailsService,
                            val tokenProvider: TokenProvider,
                            val corsFilter: CorsFilter)
  extends WebSecurityConfigurerAdapter {

  @PostConstruct def init() {
    try {
      authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }
    catch {
      case e: Exception => {
        throw new BeanInitializationException("Security configuration failed", e)
      }
    }
  }


  @Bean
  def passwordEncoder: PasswordEncoder = new BCryptPasswordEncoder

  @Bean
  def http401UnauthorizedEntryPoint: Http401UnauthorizedEntryPoint = {
    new Http401UnauthorizedEntryPoint
  }


  @throws[Exception]
  override def configure(web: WebSecurity) {
    web.ignoring
      .antMatchers(HttpMethod.OPTIONS, "/**")
      .antMatchers("/app/**/*.{js,html}")
      .antMatchers("/bower_components/**")
      .antMatchers("/i18n/**")
      .antMatchers("/content/**")
      .antMatchers("/swagger-ui/index.html")
      .antMatchers("/test/**")
  }

  override protected def configure(http: HttpSecurity) {
    http
      .addFilterBefore(corsFilter, classOf[UsernamePasswordAuthenticationFilter])
      .exceptionHandling.authenticationEntryPoint(http401UnauthorizedEntryPoint)
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
      .antMatchers("/api/profile-info").permitAll
      .antMatchers("/api/**").authenticated
      .antMatchers("/management/health").permitAll
      .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
      .antMatchers("/v2/api-docs/**").permitAll
      .antMatchers("/swagger-resources/configuration/ui").permitAll
      .antMatchers("/swagger-ui/index.html").hasAuthority(AuthoritiesConstants.ADMIN)
      .and
      .apply(securityConfigurerAdapter)
  }

  private def securityConfigurerAdapter: JWTConfigurer = new JWTConfigurer(tokenProvider)

  @Bean
  def securityEvaluationContextExtension: SecurityEvaluationContextExtension = new SecurityEvaluationContextExtension
}
