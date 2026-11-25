package proxy;

import handler.LoggingInvocationHandler;
import logging.TestLoggingInterface;

import java.lang.reflect.Proxy;

public class ProxyFactory {

    public static TestLoggingInterface create(Object target) {
        return (TestLoggingInterface) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new LoggingInvocationHandler(target)
        );
    }
}
