package handler;

import annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class LoggingInvocationHandler implements InvocationHandler {

    private final Object target;

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method targetMethod = target.getClass()
                .getMethod(method.getName(), method.getParameterTypes());
        if (targetMethod.isAnnotationPresent(Log.class)) {
            System.out.println("executed method: " + method.getName()
                    + ", param: " + (args == null ? "" : String.join(", ",
                    Arrays.stream(args).map(String::valueOf).toArray(String[]::new))));
        }
        return method.invoke(target, args);
    }
}
