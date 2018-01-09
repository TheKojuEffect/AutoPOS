package com.kapilkoju.autopos.config

import org.apache.commons.lang3.CharEncoding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Description
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

@Configuration
class ThymeleafConfiguration {

    @Bean
    @Description("Thymeleaf template resolver serving HTML 5 emails")
    fun emailTemplateResolver(): ClassLoaderTemplateResolver {
        val emailTemplateResolver = ClassLoaderTemplateResolver()
        emailTemplateResolver.prefix = "mails/"
        emailTemplateResolver.suffix = ".html"
        emailTemplateResolver.templateMode = "HTML5"
        emailTemplateResolver.characterEncoding = CharEncoding.UTF_8
        emailTemplateResolver.order = 1
        return emailTemplateResolver
    }
}