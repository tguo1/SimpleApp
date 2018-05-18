package db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.connection.Connection;
import drivers.MongoDriver;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.Mock;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import static org.mockito.Mockito.*;

public class MokitoTest {

    @Mock
    private MongoClient mockClient;
    @Mock
    private MongoCollection mockCollection;
    @Mock
    private MongoDatabase mockDB;
    @Mock
    private MongoDriver wrapper;

    @Before
    public void setUp() {
        when(mockClient.getDatabase(anyString())).thenReturn(mockDB);
        when(mockDB.getCollection(anyString())).thenReturn(mockCollection);

    }

    public static void main(String[] args) {





    }
}
