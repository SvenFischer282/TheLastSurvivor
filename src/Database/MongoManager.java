package Database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.cdimascio.dotenv.Dotenv;


public class MongoManager {
    private static final Logger logger = LoggerFactory.getLogger(MongoManager.class);
    private static MongoClient client = null;

    private MongoManager() {
        if (client == null) {
            try {

                Dotenv dotenv = Dotenv.load();
                String uri = dotenv.get("MONGO_URI");
                ConnectionString connectionString = new ConnectionString(uri);
                MongoClientSettings settings = MongoClientSettings.builder()
                        .applyConnectionString(connectionString)
                        .build();
                client = MongoClients.create(settings);
                logger.info("Successfully connected to MongoDB.");
            } catch (MongoException e) {
                logger.error("Failed to connect to MongoDB.", e);
            }
        }
    }

    public static MongoClient getClient() {
        if (client == null) {
            new MongoManager();
        }
        return client;
    }

    public static MongoDatabase getDatabase() {
        return getClient().getDatabase("test");
    }
}