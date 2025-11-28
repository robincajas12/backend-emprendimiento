package com.project007;

import com.project007.config.CdiContainerManager;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoApplication.class)
                .run(args);
    }

    @PostConstruct
    public void onStart() {
        System.out.println("Inicializando Contenedor CDI...");
        CdiContainerManager.initialize();
        System.out.println("Contenedor CDI inicializado.");
    }

    @PreDestroy
    public void onExit() {
        System.out.println("Cerrando Contenedor CDI...");
        CdiContainerManager.shutdown();
        System.out.println("Contenedor CDI cerrado.");
    }
}

