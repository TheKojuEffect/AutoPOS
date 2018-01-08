package com.kapilkoju.autopos

import com.kapilkoju.autopos.config.DefaultProfileUtil
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.support.SpringBootServletInitializer

class ApplicationWebXml : SpringBootServletInitializer() {

    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
        DefaultProfileUtil.addDefaultProfile(application.application())
        return application.sources(Application::class.java)
    }
}