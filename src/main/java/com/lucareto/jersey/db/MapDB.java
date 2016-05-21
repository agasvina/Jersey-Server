package com.lucareto.jersey.db;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public abstract class MapDB<T> {
    private final DB db;
    private final ConcurrentNavigableMap<String, T> dataMap;
    
    protected MapDB(final String dbName) {
        db = DBInstance.INSTANCE.getDbSingleton();
        dataMap = db.getTreeMap(dbName);
    }
    
    //See: singleton pattern. 
    private enum DBInstance {
        INSTANCE;
        private DB dBSingleton;
        
        private DBInstance() {
            String mapDbFilePath = System.getProperty("dbpath");
            if(Objects.nonNull(mapDbFilePath) && !mapDbFilePath.isEmpty()) {
            try {
                Path path = Paths.get(mapDbFilePath).getParent();
                Files.createDirectories(path);
                dBSingleton = DBMaker.newFileDB(new File(mapDbFilePath)).closeOnJvmShutdown().make();
            } catch (Exception e) {
                new RuntimeException("Unable to create DB instance");
            }
            } else 
                dBSingleton = DBMaker.newMemoryDB().closeOnJvmShutdown().make();
        }
        
        private DB getDbSingleton() {
            return dBSingleton;
        }
    }
    
    protected void put(final String key, final T value)  {
        dataMap.put(key, value);
        db.commit();
    }
    
    protected T get(final String key) {
        return dataMap.get(key);
    }
    
    protected boolean remove (final String key) {
        return Objects.nonNull(dataMap.remove(key));
    }
    
    protected List<T> getAll() {
        return new ArrayList<>(dataMap.values());
    }
}
