package interpreter.model.adt;

import interpreter.exceptions.ADTException;

public interface IDictionary<K, V> {
    void put(K key, V value);
    V lookup(K key) throws ADTException;
    boolean isDefined(K key);
    void update(K key, V value) throws ADTException;
    void remove(K key);
}
