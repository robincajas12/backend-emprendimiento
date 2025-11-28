package com.project007.config;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class CdiContainerManager {

    private static SeContainer container;

    // Inicializa el contenedor una sola vez.
    public static synchronized void initialize() {
        if (container == null) {
            container = SeContainerInitializer.newInstance().initialize();
        }
    }

    public static SeContainer getContainer() {
        if (container == null) {
            initialize();
        }
        return container;
    }

    // Cierra el contenedor cuando la aplicación se detiene.
    public static void shutdown() {
        if (container != null && container.isRunning()) {
            container.close();
        }
    }
}
