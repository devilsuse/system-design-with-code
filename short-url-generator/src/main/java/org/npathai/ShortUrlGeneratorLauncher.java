package org.npathai;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.npathai.properties.ApplicationProperties;
import org.npathai.util.NullSafe;
import org.npathai.zookeeper.DefaultZkManager;
import org.npathai.zookeeper.DefaultZkManagerFactory;
import org.npathai.zookeeper.ZkManager;
import spark.Spark;

import java.io.IOException;
import java.util.Properties;

import static spark.Spark.before;

public class ShortUrlGeneratorLauncher {
    private static final Logger LOG = LogManager.getLogger(ShortUrlGeneratorLauncher.class);

    public static final int PORT = Integer.parseInt(System.getenv("PORT"));
    private Router router;
    private DefaultZkManager zkManager;
    private Properties applicationProperties;

    public static void main(String[] args) throws Exception {
        ShortUrlGeneratorLauncher shortUrlGeneratorLauncher = new ShortUrlGeneratorLauncher();
        try {
            shortUrlGeneratorLauncher.start();
            LOG.info("Started successfully, listening for requests on port: {}", PORT);
            shortUrlGeneratorLauncher.awaitInitialization();
            Thread.currentThread().join();
        } finally {
            shortUrlGeneratorLauncher.stop();
            LOG.info("Stopped successfully");
        }
    }

    private void setupSpark() {
        Spark.port(PORT);
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Headers", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, OPTIONS");
        });
    }

    public void start() throws Exception {
        readApplicationProperties();
        DefaultZkManagerFactory zkManagerFactory = new DefaultZkManagerFactory();
        zkManager = zkManagerFactory.createConnected(
                applicationProperties.getProperty(ApplicationProperties.ZOOKEEPER_URL.name()));

        setupSpark();

        router = new Router(applicationProperties, zkManager);
        router.initRoutes();
    }

    private void readApplicationProperties() throws IOException {
        applicationProperties = new Properties();
        applicationProperties.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
    }

    public void awaitInitialization() {
        Spark.awaitInitialization();
    }

    public void stop() throws Exception {
        Spark.stop();
        NullSafe.ifNotNull(router, Router::stop);
        NullSafe.ifNotNull(zkManager, ZkManager::stop);
    }
}
