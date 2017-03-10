package com.kapilkoju.autopos.config

import org.apache.commons.lang3.CharEncoding
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation._
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

@Configuration class ThymeleafConfiguration {
  @SuppressWarnings(Array("unused")) final private val log: Logger = LoggerFactory.getLogger(classOf[ThymeleafConfiguration])

  @Bean
  @Description("Thymeleaf template resolver serving HTML 5 emails") def emailTemplateResolver: ClassLoaderTemplateResolver = {
    val emailTemplateResolver: ClassLoaderTemplateResolver = new ClassLoaderTemplateResolver
    emailTemplateResolver.setPrefix("mails/")
    emailTemplateResolver.setSuffix(".html")
    emailTemplateResolver.setTemplateMode("HTML5")
    emailTemplateResolver.setCharacterEncoding(CharEncoding.UTF_8)
    emailTemplateResolver.setOrder(1)
    return emailTemplateResolver
  }
}