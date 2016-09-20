package io.koju.autopos.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;


/**
 * Properties specific to AutoPOS.
 * <p>
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "autopos", ignoreUnknownFields = false)
public class AutoposProperties {

    private final Async async = new Async();

    private final Http http = new Http();

    private final Mail mail = new Mail();

    private final Security security = new Security();

    private final Metrics metrics = new Metrics();

    private final CorsConfiguration cors = new CorsConfiguration();

    public Async getAsync() {
        return this.async;
    }

    public Http getHttp() {
        return this.http;
    }

    public Mail getMail() {
        return this.mail;
    }

    public Security getSecurity() {
        return this.security;
    }

    public Metrics getMetrics() {
        return this.metrics;
    }

    public CorsConfiguration getCors() {
        return this.cors;
    }

    public static class Async {

        private int corePoolSize = 2;

        private int maxPoolSize = 50;

        private int queueCapacity = 10000;

        public int getCorePoolSize() {
            return this.corePoolSize;
        }

        public int getMaxPoolSize() {
            return this.maxPoolSize;
        }

        public int getQueueCapacity() {
            return this.queueCapacity;
        }

        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public void setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }
    }

    public static class Http {

        private final Cache cache = new Cache();

        public Cache getCache() {
            return this.cache;
        }

        public static class Cache {

            private int timeToLiveInDays = 1461;

            public int getTimeToLiveInDays() {
                return this.timeToLiveInDays;
            }

            public void setTimeToLiveInDays(int timeToLiveInDays) {
                this.timeToLiveInDays = timeToLiveInDays;
            }
        }
    }

    public static class Mail {

        private String from = "AutoPOS@localhost";

        public String getFrom() {
            return this.from;
        }

        public void setFrom(String from) {
            this.from = from;
        }
    }

    public static class Security {

        private final Authentication authentication = new Authentication();

        public Authentication getAuthentication() {
            return this.authentication;
        }

        public static class Authentication {

            private final Jwt jwt = new Jwt();

            public Jwt getJwt() {
                return this.jwt;
            }

            public static class Jwt {

                private String secret;

                private long tokenValidityInSeconds = 1800;
                private long tokenValidityInSecondsForRememberMe = 2592000;

                public String getSecret() {
                    return this.secret;
                }

                public long getTokenValidityInSeconds() {
                    return this.tokenValidityInSeconds;
                }

                public long getTokenValidityInSecondsForRememberMe() {
                    return this.tokenValidityInSecondsForRememberMe;
                }

                public void setSecret(String secret) {
                    this.secret = secret;
                }

                public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
                    this.tokenValidityInSeconds = tokenValidityInSeconds;
                }

                public void setTokenValidityInSecondsForRememberMe(long tokenValidityInSecondsForRememberMe) {
                    this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
                }
            }
        }
    }

    public static class Metrics {

        private final Jmx jmx = new Jmx();

        private final Spark spark = new Spark();

        private final Graphite graphite = new Graphite();

        private final Logs logs = new Logs();

        public Jmx getJmx() {
            return this.jmx;
        }

        public Spark getSpark() {
            return this.spark;
        }

        public Graphite getGraphite() {
            return this.graphite;
        }

        public Logs getLogs() {
            return this.logs;
        }

        public static class Jmx {

            private boolean enabled = true;

            public boolean isEnabled() {
                return this.enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }
        }

        public static class Spark {

            private boolean enabled = false;

            private String host = "localhost";

            private int port = 9999;

            public boolean isEnabled() {
                return this.enabled;
            }

            public String getHost() {
                return this.host;
            }

            public int getPort() {
                return this.port;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public void setHost(String host) {
                this.host = host;
            }

            public void setPort(int port) {
                this.port = port;
            }
        }

        public static class Graphite {

            private boolean enabled = false;

            private String host = "localhost";

            private int port = 2003;

            private String prefix = "AutoPOS";

            public boolean isEnabled() {
                return this.enabled;
            }

            public String getHost() {
                return this.host;
            }

            public int getPort() {
                return this.port;
            }

            public String getPrefix() {
                return this.prefix;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public void setHost(String host) {
                this.host = host;
            }

            public void setPort(int port) {
                this.port = port;
            }

            public void setPrefix(String prefix) {
                this.prefix = prefix;
            }
        }

        public static class Logs {

            private boolean enabled = false;

            private long reportFrequency = 60;

            public boolean isEnabled() {
                return this.enabled;
            }

            public long getReportFrequency() {
                return this.reportFrequency;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public void setReportFrequency(long reportFrequency) {
                this.reportFrequency = reportFrequency;
            }
        }
    }
}
