package com.kapilkoju.autopos.config

import io.github.jhipster.config.locale.AngularCookieLocaleResolver
import org.springframework.context.EnvironmentAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor

@Configuration class LocaleConfiguration extends WebMvcConfigurerAdapter with EnvironmentAware {
  def setEnvironment(environment: Environment) {
    // unused
  }

  @Bean(name = Array("localeResolver")) def localeResolver: LocaleResolver = {
    val cookieLocaleResolver: AngularCookieLocaleResolver = new AngularCookieLocaleResolver
    cookieLocaleResolver.setCookieName("NG_TRANSLATE_LANG_KEY")
    cookieLocaleResolver
  }

  override def addInterceptors(registry: InterceptorRegistry) {
    val localeChangeInterceptor = new LocaleChangeInterceptor
    localeChangeInterceptor.setParamName("language")
    registry.addInterceptor(localeChangeInterceptor)
  }
}