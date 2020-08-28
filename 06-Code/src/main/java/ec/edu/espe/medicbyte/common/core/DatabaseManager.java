package ec.edu.espe.medicbyte.common.core;

import com.mongodb.MongoClient;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

/**
 *
 * @author Andres Jonathan J.
 */
public class DatabaseManager {
    public static class DatabaseContext {
        private final String modelsPackage;
        private final String databaseName;
        private final String host;
        private final int port;

        public DatabaseContext(String modelsPackage, String databaseName, String host, int port) {
            this.modelsPackage = modelsPackage;
            this.databaseName = databaseName;
            this.host = host;
            this.port = port;
        }

        public String getModelsPackage() {
            return modelsPackage;
        }

        public String getDatabaseName() {
            return databaseName;
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }
    }
    
    private final DatabaseContext context;
    private final Morphia morphia = new Morphia();
    private MongoClient client;
    private Datastore datastore;
    private boolean configured = false;
    
    public DatabaseManager(DatabaseContext context) {
        this.context = context;
        setup(context);
    }
    
    public Datastore getDatastore() {
        return datastore;
    }
    
    public DatabaseContext getContext() {
        return context;
    }
    
    private void setup(DatabaseContext context) {
        if (configured) {
            return;
        }
        
        morphia.mapPackage(context.getModelsPackage());
        client = new MongoClient(context.getHost(), context.getPort());
        datastore = morphia.createDatastore(client, context.getDatabaseName());
        datastore.ensureIndexes();
        configured = true;
    }
}
