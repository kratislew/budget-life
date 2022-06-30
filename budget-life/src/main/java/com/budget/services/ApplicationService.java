package com.budget.services;

import java.util.Map;
import java.util.UUID;

import com.budget.beans.Value;

public interface ApplicationService {

    public Map<UUID, Value> readValues();

    public Value readValue(String id);

    public void createValue(Value value);

    public void updateValue(Value value);

    public void deleteValue(String id);

    public void createOrUpdateValue(Value value);

}
