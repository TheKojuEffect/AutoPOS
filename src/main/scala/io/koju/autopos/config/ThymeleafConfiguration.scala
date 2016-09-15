package io.koju.autopos.config

import org.apache.commons.lang.CharEncoding
import org.springframework.context.annotation.{Bean, Configuration, Description}
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

@Configuration
class ThymeleafConfiguration {

  @Bean
  @Description("Thymeleaf template resolver serving HTML 5 emails")
  def emailTemplateResolver: ClassLoaderTemplateResolver = {
    val emailTemplateResolver: ClassLoaderTemplateResolver = new ClassLoaderTemplateResolver
    emailTemplateResolver.setPrefix("mails/")
    emailTemplateResolver.setSuffix(".html")
    emailTemplateResolver.setTemplateMode("HTML5")
    emailTemplateResolver.setCharacterEncoding(CharEncoding.UTF_8)
    emailTemplateResolver.setOrder(1)
    emailTemplateResolver
  }

}