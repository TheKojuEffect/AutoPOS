package io.koju.autopos.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;


/**
 * Properties specific to JHipster.
 * <p>
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "jhipster", ignoreUnknownFields = false)
@Getter
public class JHipsterProperties {

    private final Async async = new Async();

    private final Http http = new Http();

    private final Mail mail = new Mail();

    private final Security security = new Security();

    private final Swagger swagger = new Swagger();

    private final Metrics metrics = new Metrics();

    private final CorsConfiguration cors = new CorsConfiguration();

    @Getter
    @Setter
    public static class Async {

        private int corePoolSize = 2;

        private int maxPoolSize = 50;

        private int queueCapacity = 10000;

    }

    @Getter
    public static class Http {

        private final Cache cache = new Cache();

        @Getter
        @Setter
        public static class Cache {

            private int timeToLiveInDays = 1461;

        }
    }

    @Getter
    @Setter
    public static class Mail {

        private String from = "AutoPOS@localhost";

    }

    @Getter
    public static class Security {

        private final Authentication authentication = new Authentication();

        @Getter
        public static class Authentication {

            private final Jwt jwt = new Jwt();

            @Getter
            @Setter
            public static class Jwt {

                private String secret;

                private long tokenValidityInSeconds = 1800;
                private long tokenValidityInSecondsForRememberMe = 2592000;

            }
        }
    }

    @Getter
    @Setter
    public static class Swagger {

        private String title = "AutoPOS API";

        private String description = "AutoPOS API documentation";

        private String version = "0.0.1";

        private String termsOfServiceUrl;

        private String contactName;

        private String contactUrl;

        private String contactEmail;

        private String license;

        private String licenseUrl;

    }

    @Getter
    public static class Metrics {

        private final Jmx jmx = new Jmx();

        private final Spark spark = new Spark();

        private final Graphite graphite = new Graphite();

        private final Logs logs = new Logs();

        @Getter
        @Setter
        public static class Jmx {

            private boolean enabled = true;

        }

        @Getter
        @Setter
        public static class Spark {

            private boolean enabled = false;

            private String host = "localhost";

            private int port = 9999;

        }

        @Getter
        @Setter
        public static class Graphite {

            private boolean enabled = false;

            private String host = "localhost";

            private int port = 2003;

            private String prefix = "AutoPOS";

        }

        @Getter
        @Setter
        public static class Logs {

            private boolean enabled = false;

            private long reportFrequency = 60;

        }
    }
}
