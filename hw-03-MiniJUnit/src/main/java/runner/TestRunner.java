package runner;

import annotation.After;
import annotation.Before;
import annotation.Test;
import dto.TestResult;
import dto.TestSummary;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class TestRunner {


    private TestRunner() {
        throw new UnsupportedOperationException("Utility class");
    }


    public static TestSummary runTests(String className) {
        Objects.requireNonNull(className, "Имя класса не может быть null");
        try {
            return runTests(Class.forName(className));
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Тестовый класс не найден: " + className, e);
        }
    }


    public static TestSummary runTests(Class<?> clazz) {
        Objects.requireNonNull(clazz, "Класс не может быть null");

        List<Method> beforeMethods = findAnnotatedMethods(clazz, Before.class);
        List<Method> testMethods = findAnnotatedMethods(clazz, Test.class);
        List<Method> afterMethods = findAnnotatedMethods(clazz, After.class);


        List<TestResult> testResults = testMethods.stream()
                .map(method -> runSingleTestCycle(clazz, method, beforeMethods, afterMethods))
                .toList();

        return new TestSummary(testResults);
    }

    private static List<Method> findAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotation))
                .toList();
    }

    private static TestResult runSingleTestCycle(Class<?> clazz, Method testMethod,
                                                 List<Method> beforeMethods, List<Method> afterMethods) {
        Object testInstance;
        try {
            Constructor<?> constructor = clazz.getConstructor();
            testInstance = constructor.newInstance();
        } catch (NoSuchMethodException e) {
            return TestResult.failure(testMethod.getName(),
                    new RuntimeException("Отсутствует публичный конструктор без параметров", e));
        } catch (Exception e) {
            return TestResult.failure(testMethod.getName(),
                    new RuntimeException("Не удалось создать экземпляр тестового класса", e));
        }

        Throwable testError = null;

        try {
            // 1. Выполнение @Before блоков
            executeMethods(testInstance, beforeMethods);

            // 2. Выполнение @Test
            testMethod.invoke(testInstance);
        } catch (InvocationTargetException e) {

            testError = e.getCause();
        } catch (Exception e) {

            testError = e;
        } finally {
            // 3. Выполнение @After
            try {
                executeMethods(testInstance, afterMethods);
            } catch (InvocationTargetException e) {

                    if (testError != null) {
                    testError.addSuppressed(e.getCause());
                } else {
                    testError = e.getCause();
                }
            } catch (Exception e) {
                if (testError != null) {
                    testError.addSuppressed(e);
                } else {
                    testError = e;
                }
            }
        }

        return testError == null
                ? TestResult.success(testMethod.getName())
                : TestResult.failure(testMethod.getName(), testError);
    }

    private static void executeMethods(Object instance, List<Method> methods)
            throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            method.invoke(instance);
        }
    }
}
