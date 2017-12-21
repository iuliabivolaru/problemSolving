package bvb;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by iuliab on 17.12.2017.
 */
public class ElementsProcessor {

    private static class ProcessingResult {
        private final long sequenceNumber;

        ProcessingResult(long sequenceNumber) {
            this.sequenceNumber = sequenceNumber;
        }
    }

    private static ProcessingResult process(ProducedElement element) throws InterruptedException {
        return new ProcessingResult(element.getSequenceNumber());
    }

    public static void main(String[] args) throws InterruptedException,
            ExecutionException {
        List<ProducedElement> elements = generateElements();
        List<Callable<ProcessingResult>> tasks = processElements(elements);
        ExecutorService service = Executors.newFixedThreadPool(10);
        displayElements(tasks, service);
    }

    private static void displayElements(List<Callable<ProcessingResult>> tasks, ExecutorService service)
            throws InterruptedException, ExecutionException {
        System.out.println("Display the sequence number of the generated elements:");
        try {
            List<Future<ProcessingResult>> results = service.invokeAll(tasks);
            for (Future<ProcessingResult> future : results) {
                System.out.println(future.get().sequenceNumber);
            }
        } finally {
            service.shutdown();
        }
    }

    private static List<Callable<ProcessingResult>> processElements(List<ProducedElement> elements) {
        List<Callable<ProcessingResult>> tasks = new ArrayList<>();
        for (ProducedElement element : elements) {
            Callable<ProcessingResult> resultCallable = () -> process(element);
            tasks.add(resultCallable);
        }
        return tasks;
    }

    private static List<ProducedElement> generateElements() {
        List<ProducedElement> producedElements = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            producedElements.add(new ProducedElement(i));
        }
        return producedElements;
    }
}
