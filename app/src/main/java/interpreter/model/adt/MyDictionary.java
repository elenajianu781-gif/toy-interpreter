package interpreter.model.adt;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


public class MyDictionary<K,V>implements  IDictionary<K,V> {
    private final Map<K, V> dictionary;

    public MyDictionary() {
        dictionary = new HashMap<>();
    }

    @Override
    public void put(K key,V value){
        dictionary.put(key,value);
    }
    @Override
    public V get(K key){
        if(!dictionary.containsKey(key)){
            throw new NoSuchElementException("Key not found: "+ key);
        }
        return dictionary.get(key);
    }

    @Override
    public boolean containsKey(K key){
        return dictionary.containsKey(key);
    }
    @Override
    public void remove(K key){

        dictionary.remove(key);
    }
}
