package dto;

import java.util.List;

public record TestSummary(List<TestResult> results) {
    public long getTotalCount(){return results.size();}
    public long getPassedCount(){return results.stream().filter(TestResult::isSuccess).count();}
    public long getFailedCount(){return results.stream().filter(r->!r.isSuccess()).count();}

    public void printReport() {
        System.out.println("\n=================================");
        System.out.println("      ОТЧЕТ О ТЕСТИРОВАНИИ       ");
        System.out.println("=================================");
        System.out.printf("Всего запущено : %d%n", getTotalCount());
        System.out.printf("Успешно        : %d%n", getPassedCount());
        System.out.printf("Упало          : %d%n", getFailedCount());
        System.out.println("---------------------------------");

        if (getFailedCount() > 0) {
            System.out.println("Детали ошибок:");
            results.stream()
                    .filter(r -> !r.isSuccess())
                    .forEach(r -> System.out.printf(" - %s: %s%n",
                            r.testName(),
                            r.exception().map(Throwable::getMessage).orElse("Неизвестная ошибка")));
        }
        System.out.println("=================================");
    }
}
