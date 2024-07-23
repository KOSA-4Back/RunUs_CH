package com.mysite.chat.global.docker;

import jakarta.annotation.PreDestroy;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * packageName    : com.mysite.chat.global.docker
 * fileName       : DockerComposeRunner
 * author         : Yeong-Huns
 * date           : 2024-07-21
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-21        Yeong-Huns       최초 생성
 */
@Component
public class DockerComposeRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder processBuilder;

        if (os.contains("win")) {
            processBuilder = new ProcessBuilder("bash", "-c", "./start-docker-compose.sh");
        } else {
            processBuilder = new ProcessBuilder("bash", "./start-docker-compose.sh");
        }

        processBuilder.inheritIO();
        Process process = processBuilder.start();
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            System.out.println("Docker Compose started successfully.");
        } else {
            System.err.println("Failed to start Docker Compose.");
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    System.err.println(errorLine);
                }
            }
        }
    }

    @PreDestroy
    public void onExit() throws Exception {
        System.out.println("Stopping Docker Compose...");

        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder processBuilder;

        if (os.contains("win")) {
            processBuilder = new ProcessBuilder("bash", "-c", "./stop-docker-compose.sh");
        } else {
            processBuilder = new ProcessBuilder("bash", "./stop-docker-compose.sh");
        }

        processBuilder.inheritIO();
        Process process = processBuilder.start();
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            System.out.println("Docker Compose stopped successfully.");
        } else {
            System.err.println("Failed to stop Docker Compose.");
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    System.err.println(errorLine);
                }
            }
        }
    }
}