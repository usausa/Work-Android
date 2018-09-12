package usausa.github.io.work.model;

@FunctionalInterface
public interface Factory<T> {

    T get();
}
