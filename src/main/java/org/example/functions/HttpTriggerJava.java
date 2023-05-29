package org.example.functions;

import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import io.micrometer.core.instrument.Metrics;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HttpTriggerJava {

    @FunctionName("HttpTriggerJava")
    public HttpResponseMessage run(
        @HttpTrigger(name = "req", methods = HttpMethod.GET)
        HttpRequestMessage<Optional<String>> request
    ) {
        Metrics.counter("my_counter").increment();
        return request.createResponseBuilder(HttpStatus.OK).body("Hello").build();
    }
}
