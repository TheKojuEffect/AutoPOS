package com.kapilkoju.autopos.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Properties specific to JHipster.
 *
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
class ApplicationProperties