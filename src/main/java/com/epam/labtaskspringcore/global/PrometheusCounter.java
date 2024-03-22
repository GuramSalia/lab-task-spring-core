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

    private Counter hitGetTrainerCounter;
    private Counter notHitGetTrainerCounter;
    private Counter hitGetTraineeCounter;
    private Counter notHitGetTraineeCounter;

    public PrometheusCounter(MeterRegistry meterRegistry) {
        this.hitGetTrainerCounter = Counter
                .builder("hit_getTrainer_counter")
                .description("number of successful hits: trainer-get")
                .register(meterRegistry);
        this.notHitGetTrainerCounter = Counter
                .builder("not_hit_getTrainer_counter")
                .description("number of unsuccessful hits: trainer-get")
                .register(meterRegistry);
        this.hitGetTraineeCounter = Counter
                .builder("hit_getTrainee_counter")
                .description("number of successful hits: trainee-get")
                .register(meterRegistry);
        this.notHitGetTraineeCounter = Counter
                .builder("not_hit_getTrainee_counter")
                .description("number of unsuccessful hits: trainee-get")
                .register(meterRegistry);
    }
}
