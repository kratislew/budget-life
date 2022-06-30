package com.budget.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import com.budget.beans.*;
import com.budget.services.ApplicationService;

public class ApplicationDao implements ApplicationService {

    public Map<UUID, Value> readValues() {
        Value value = null;
        Map<UUID, Value> values = new LinkedHashMap<UUID, Value>();

        try {
            // get connection to database
            Connection connection = DBConnection.getConnectionToDatabase();

            // write select query to get all values
            String sql = "select * from userValues;";
            PreparedStatement statement = connection.prepareStatement(sql);

            // execute query, get result set and return User info
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                value = new Expense();
                value.setId(UUID.fromString(set.getString("uuid")));
                value.setTitle(set.getString("title"));
                value.setAmount(set.getDouble("amount"));
                value.setDesc(set.getString("description"));
                // value.setCreateTimestamp(Date.parse(set.getDate("createTimestamp")));
                values.put(value.getId(), value);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return values;
    }

    public Value readValue(String id) {
        Value value = null;
        try {
            // get connection to database
            Connection connection = DBConnection.getConnectionToDatabase();

            // write select query to get the value
            String sql = "select * from userValues where uuid=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);

            // execute query, get resultset and return Value info
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                value = new Expense();
                value.setId(UUID.fromString(set.getString("uuid")));
                value.setTitle(set.getString("title"));
                value.setAmount(set.getDouble("amount"));
                value.setDesc(set.getString("description"));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return value;
    }

    public void createValue(Value value) {
        try {
            // get connection to database
            Connection connection = DBConnection.getConnectionToDatabase();

            // write select query to get the value
            String sql = "insert into userValues (uuid, title, amount, description) values (?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, value.getId().toString());
            statement.setString(2, value.getTitle());
            statement.setDouble(3, value.getAmount());
            statement.setString(4, value.getDesc());

            // execute query, update resultset
            statement.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void updateValue(Value value) {
        try {
            // get connection to database
            Connection connection = DBConnection.getConnectionToDatabase();

            // write select query to get the value
            String sql = "update userValues set title=?, amount=?, description=? where uuid=?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, value.getTitle());
            statement.setDouble(2, value.getAmount());
            statement.setString(3, value.getDesc());
            statement.setString(4, value.getId().toString());

            // execute query, update resultset
            statement.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void deleteValue(String id) {
        try {
            // get connection to database
            Connection connection = DBConnection.getConnectionToDatabase();

            // write select query to get the value
            String sql = "delete from userValues where uuid=?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, UUID.fromString(id).toString());

            // execute query, delete resultset
            statement.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
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
