package ca.uqam.mgl7361.lel.gp1.common;

@FunctionalInterface
public interface RunnableWithException {
    void run() throws Exception;
}
