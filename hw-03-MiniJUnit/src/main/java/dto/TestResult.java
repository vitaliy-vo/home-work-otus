package dto;

import java.util.Optional;

public record TestResult(
    String testName,
    boolean isSuccess,
    Optional<Throwable> exception
){
    public static TestResult success(String name){
        return new TestResult(name, true, Optional.empty());
    }
    public static TestResult failure(String name, Throwable ex){
        return new TestResult(name, false, Optional.of(ex));
    }
}
