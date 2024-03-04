package com.epam.labtaskspringcore.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JsonDataContainer {
    @JsonProperty("Trainer")
    public List<Trainer> trainer;
    @JsonProperty("Trainee")
    public List<Trainee> trainee;
    @JsonProperty("Training")
    public List<Training> training;
}
