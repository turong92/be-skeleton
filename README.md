#  be-skeleton

#### core-domain application-core-domain.yml sample
- - -
    spring:
        datasource:
            driver-class-name: com.p6spy.engine.spy.P6SpyDriver
            url: jdbc:p6spy:mysql://{DB_ENDPOINT:PORT/SCHEMA}?useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true
            username: {DB_USER}
            password: {DB_PW}
        jpa:
            database: mysql
            hibernate:
                ddl-auto: none
            properties:
                hibernate:
                    globally_quoted_identifiers: true
                    query.in_clause_parameter_padding: true
                    ddl-auto: none
                    cache:
                        use_second_level_cache: false
                        use_query_cache: false
        data:
            redis:
                host: localhost
                port: 6379
    liquibase:
        change-log: classpath:db/changelog/db.changelog-master.xml
        enabled: false
    decorator:
        datasource:
            p6spy:
                enable-logging: true
