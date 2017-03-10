package com.kapilkoju.autopos

import com.kapilkoju.autopos.config.DefaultProfileUtil
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.support.SpringBootServletInitializer

class ApplicationWebXml extends SpringBootServletInitializer {

  override def configure(application: SpringApplicationBuilder) = {
    DefaultProfileUtil.addDefaultProfile(application.application())
    application.sources(classOf[Application])
  }


}
