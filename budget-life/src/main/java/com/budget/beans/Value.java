package com.budget.beans;

import java.util.Date;
import java.util.UUID;

public abstract class Value {
    private UUID id;
    private double amount;
    private String title;
    private Date date;
    private String desc;

    public Value() {
    }

    public Value(double amount, String title, String desc) {
        this.id = UUID.randomUUID();
        this.amount = amount;
        this.title = title;
        this.desc = desc;
        this.date = new Date();
    }

    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @param set id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param set title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the content
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param set amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param setDate
     */
    public void setDate(Date date) {
        this.date = date;
    }
    
    /**
     * @return the description
     */
    public String getDesc() {
    	return desc;
    }
    
    /**
     * @param set description
     */
    public void setDesc(String desc) {
    	this.desc = desc;
    }

}
