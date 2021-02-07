package pl.first.firstjava;

public interface Dao<T> extends AutoCloseable {

    public T read();

    public boolean write(T obj);
}
