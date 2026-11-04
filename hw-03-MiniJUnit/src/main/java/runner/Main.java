package runner;

import dto.TestSummary;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Запуск StackTest ===");
        TestSummary summary = TestRunner.runTests("tests.StackTest");
        summary.printReport();
    }
}
