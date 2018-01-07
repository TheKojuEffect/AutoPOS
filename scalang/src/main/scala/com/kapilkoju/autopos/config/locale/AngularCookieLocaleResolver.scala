package com.kapilkoju.autopos.config.locale

import java.util.{Locale, TimeZone}
import javax.servlet.http.{Cookie, HttpServletRequest, HttpServletResponse}

import org.springframework.context.i18n.{LocaleContext, TimeZoneAwareLocaleContext}
import org.springframework.util.StringUtils
import org.springframework.web.servlet.i18n.CookieLocaleResolver
import org.springframework.web.util.WebUtils

/**
  * Angular cookie saved the locale with a double quote (%22en%22).
  * So the default CookieLocaleResolver#StringUtils.parseLocaleString(localePart)
  * is not able to parse the locale.
  *
  * This class will check if a double quote has been added, if so it will remove it.
  */
class AngularCookieLocaleResolver extends CookieLocaleResolver {
  override def resolveLocale(request: HttpServletRequest): Locale = {
    parseLocaleCookieIfNecessary(request)
    request.getAttribute(CookieLocaleResolver.LOCALE_REQUEST_ATTRIBUTE_NAME).asInstanceOf[Locale]
  }

  override def resolveLocaleContext(request: HttpServletRequest): LocaleContext = {
    parseLocaleCookieIfNecessary(request)
    new TimeZoneAwareLocaleContext() {
      def getLocale: Locale = {
        request.getAttribute(CookieLocaleResolver.LOCALE_REQUEST_ATTRIBUTE_NAME).asInstanceOf[Locale]
      }

      def getTimeZone: TimeZone = {
        request.getAttribute(CookieLocaleResolver.TIME_ZONE_REQUEST_ATTRIBUTE_NAME).asInstanceOf[TimeZone]
      }
    }
  }

  override def addCookie(response: HttpServletResponse, cookieValue: String) {
    // Mandatory cookie modification for angular to support the locale switching on the server side.
    super.addCookie(response, s"%22$cookieValue%22")
  }

  private def parseLocaleCookieIfNecessary(request: HttpServletRequest) {
    if (request.getAttribute(CookieLocaleResolver.LOCALE_REQUEST_ATTRIBUTE_NAME) == null) {
      // Retrieve and parse cookie value.
      val cookie: Cookie = WebUtils.getCookie(request, getCookieName)
      var locale: Locale = null
      var timeZone: TimeZone = null
      if (cookie != null) {
        var value: String = cookie.getValue
        // Remove the double quote
        value = StringUtils.replace(value, "%22", "")
        var localePart: String = value
        var timeZonePart: String = null
        val spaceIndex: Int = localePart.indexOf(' ')
        if (spaceIndex != -1) {
          localePart = value.substring(0, spaceIndex)
          timeZonePart = value.substring(spaceIndex + 1)
        }
        locale = if ("-" != localePart)
          StringUtils.parseLocaleString(localePart.replace('-', '_'))
        else null

        if (timeZonePart != null) {
          timeZone = StringUtils.parseTimeZoneString(timeZonePart)
        }
        if (logger.isTraceEnabled) {
          logger.trace("Parsed cookie value [" + cookie.getValue + "] into locale '" + locale + "'" + (if (timeZone
            != null) " and time zone '" + timeZone.getID + "'"
          else ""))
        }
      }

      val localeRequestAttr: Locale = if (locale != null) locale else determineDefaultLocale(request)
      val timeZoneRequestAttr: TimeZone = if (timeZone != null) timeZone else determineDefaultTimeZone(request)

      request.setAttribute(CookieLocaleResolver.LOCALE_REQUEST_ATTRIBUTE_NAME, localeRequestAttr)
      request.setAttribute(CookieLocaleResolver.TIME_ZONE_REQUEST_ATTRIBUTE_NAME, timeZoneRequestAttr)
    }
  }
}
