package logging;

import annotation.Log;

public class TestLogging implements TestLoggingInterface {

    @Log
    public void calculation(int param) {
        System.out.println("вызван calculation с одним параметром");
    }

    @Log
    public void calculation(int param1, int param2) {
        System.out.println("вызван calculation с двумя параметрами");
    }

    @Log
    public void calculation(int param1, int param2, String param3) {
        System.out.println("вызван calculation с тремя параметрами");
    }
    @Log
    public void calculation(int param1, int param2, String param3,Boolean param4) {
        System.out.println("вызван calculation с четыремя параметрами");
    }

}
