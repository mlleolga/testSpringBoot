package com.testspringboot.persistance;


public enum HistoryType {


    CREATE_USER("create_user"), CREATE_INTEGRATION("create_integration"), UPDATE_BALANCE("update_balance")
    , SAVE_MESSAGE("save_message");

    private String description;

    public String getDescription() {
        return description;
    }

    HistoryType(String description) {
        this.description = description;
    }
}
