package com.budget.inmemory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import com.budget.beans.Value;
import com.budget.services.ApplicationService;

public class ApplicationInMemory implements ApplicationService {

    private Map<UUID, Value> values;

    public ApplicationInMemory() {
        this.values = new LinkedHashMap<UUID, Value>();
    }

    @Override
    public Map<UUID, Value> readValues() {
        return values;
    }

    @Override
    public Value readValue(String id) {
        return values.get(UUID.fromString(id));
    }

    @Override
    public void createValue(Value value) {
        updateValue(value);
    }

    @Override
    public void updateValue(Value value) {
        values.put(value.getId(), value);
    }

    @Override
    public void deleteValue(String id) {
        values.remove(UUID.fromString(id));
    }

    @Override
    public void createOrUpdateValue(Value value) {
        Value localValue = readValue(value.getId().toString());
        if (localValue == null) {
            createValue(value);
        } else {
            updateValue(value);
        }
    }

}
