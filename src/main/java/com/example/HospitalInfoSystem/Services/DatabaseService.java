package com.example.HospitalInfoSystem.Services;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DatabaseService {

    @Value("${spring.datasource.username}")
    private String dbUser ;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    private final String backupFilePath = "backup.sql";

    @PreDestroy
    public void backupDatabaseOnShutDown() {
        System.out.println("Starting backup on application startup...");
        backupDatabase(backupFilePath);
    }

    @PostConstruct
    public void restoreDatabaseOnStartUp() {
        System.out.println("Restoring database on application shutdown...");
        restoreDatabase(backupFilePath);
    }

    public void backupDatabase(String backupFilePath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "C:\\Program Files\\PostgreSQL\\16\\bin\\pg_dump",
                    "-U", dbUser ,
                    "-w",
                    "-f", backupFilePath,
                    dbUrl.split("/")[3] // Извлекаем имя базы данных из URL
            );
            processBuilder.environment().put("PGPASSWORD", dbPassword);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Backup completed successfully.");
            } else {
                System.out.println("Backup failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void restoreDatabase(String backupFilePath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "C:\\Program Files\\PostgreSQL\\16\\bin\\pg_restore",
                    "-U", dbUser ,
                    "-d", dbUrl.split("/")[3], // Извлекаем имя базы данных из URL
                    "-f", backupFilePath
            );
            processBuilder.environment().put("PGPASSWORD", dbPassword);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Restore completed successfully.");
            } else {
                System.out.println("Restore failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
