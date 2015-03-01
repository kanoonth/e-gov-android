package com.mapfap.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by mapfap on 2/28/15.
 */
public class TransactionInfoResponse {

    @JsonProperty("payments")
    private List<Transaction> transactions;

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
