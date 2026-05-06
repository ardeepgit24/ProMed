package com.promed.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DbConnectionVerifier implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DbConnectionVerifier.class);
    private final DataSource dataSource;

    public DbConnectionVerifier(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(ApplicationArguments args) {
        try (Connection conn = dataSource.getConnection()) {
            log.info("✅ PostgreSQL connection verified! DB: {}, URL: {}",
                    conn.getCatalog(), conn.getMetaData().getURL());
        } catch (Exception ex) {
            log.error("❌ Failed to connect to PostgreSQL: {}", ex.getMessage());
        }
    }
}

