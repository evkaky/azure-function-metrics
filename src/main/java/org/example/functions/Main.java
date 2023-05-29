package org.example.functions;

import com.microsoft.azure.functions.ExecutionContext;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.tracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}

@Qualifier("HttpTriggerJava")
@Service
class EventHandler implements Function<Message<String>, String> {
    private static Logger log = LoggerFactory.getLogger(EventHandler.class);

    @Autowired
    MeterRegistry meterRegistry;

    @Autowired
    Tracer tracer;

    @Override
    public String apply(Message<String> message) {
        ExecutionContext context = (ExecutionContext) message.getHeaders().get("executionContext");

        log.info("log from spring");

        // metrics
        Metrics.counter("my.counter1").increment();
        meterRegistry.counter("my.counter2").increment();

        // traces
        log.info("trace id = {}", tracer.currentTraceContext().context().traceId());

        return "some response";
    }
}
