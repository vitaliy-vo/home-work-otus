import logging.TestLogging;
import logging.TestLoggingInterface;
import proxy.ProxyFactory;

public class Demo {

    public static void main(String[] args) {
        TestLoggingInterface logging = ProxyFactory.create(new TestLogging());
        logging.calculation(6);
        logging.calculation(6, 10);
        logging.calculation(6, 10, "hello");
        logging.calculation(6, 10, "hello",false);
    }
}
