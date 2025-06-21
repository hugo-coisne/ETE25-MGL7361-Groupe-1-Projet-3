package common;

@FunctionalInterface
public interface RunnableWithException {
    void run() throws Exception;
}
