package com.epam.labtaskspringcore.global;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class PrometheusCounter {

    private Counter successfulGetTrainerHitCounter;
    private Counter unsuccessfulGetTrainerHitCounter;

    private Counter successfulGetTraineeHitCounter;
    private Counter unsuccessfulGetTraineeHitCounter;

    public PrometheusCounter(MeterRegistry meterRegistry) {
        this.successfulGetTrainerHitCounter = Counter
                .builder("successful_hit_for_getTrainer_counter")
                .description("number of successful hits: trainer-get")
                .register(meterRegistry);
        this.unsuccessfulGetTrainerHitCounter = Counter
                .builder("unsuccessful_hit_for_getTrainer_counter")
                .description("number of unsuccessful hits: trainer-get")
                .register(meterRegistry);
        this.successfulGetTraineeHitCounter = Counter
                .builder("successful_hit_for_getTrainee_counter")
                .description("number of successful hits: trainee-get")
                .register(meterRegistry);
        this.unsuccessfulGetTraineeHitCounter = Counter
                .builder("unsuccessful_hit_for_getTrainee_counter")
                .description("number of unsuccessful hits: trainee-get")
                .register(meterRegistry);
    }
}
