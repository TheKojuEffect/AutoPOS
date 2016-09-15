package io.koju.autopos.config

import io.koju.autopos.config.locale.AngularCookieLocaleResolver
import org.springframework.boot.bind.RelaxedPropertyResolver
import org.springframework.context.EnvironmentAware
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.core.env.Environment
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.{InterceptorRegistry, WebMvcConfigurerAdapter}
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor

@Configuration class LocaleConfiguration extends WebMvcConfigurerAdapter with EnvironmentAware {

  private var propertyResolver: RelaxedPropertyResolver = _

  override def setEnvironment(environment: Environment) {
    this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.messages.")
  }

  @Bean(name = Array("localeResolver")) def localeResolver: LocaleResolver = {
    val cookieLocaleResolver = new AngularCookieLocaleResolver
    cookieLocaleResolver.setCookieName("NG_TRANSLATE_LANG_KEY")
    cookieLocaleResolver
  }

  override def addInterceptors(registry: InterceptorRegistry) {
    val localeChangeInterceptor = new LocaleChangeInterceptor
    localeChangeInterceptor.setParamName("language")
    registry.addInterceptor(localeChangeInterceptor)
  }
}