package com.apploidxxx.heliosbackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Arthur Kupriyanov
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final Logger logger = LoggerFactory.getLogger(WebConfig.class);
    //
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");
        String jdbcUrl = System.getenv("DB_JDBC_URL");
        if (username == null || password == null || jdbcUrl == null) {
            Properties properties = new Properties();
            try {
                properties.load(WebConfig.class.getClassLoader().getResourceAsStream("local_configs.properties"));
                username = properties.getProperty("DB_USERNAME");
                password = properties.getProperty("DB_PASSWORD");
                jdbcUrl = properties.getProperty("DB_JDBC_URL");
                logger.debug("Database username, password and url loaded from local properties");
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.debug("Database username, password and url loaded from env");
        }


        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(jdbcUrl);

        return dataSource;
    }

}
