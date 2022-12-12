package com.golubovich.project_trpo_tofi.model;

public enum RequestStatus {
    NEW ("Новая"),
    REJECTED("Откланена"),
    COMPLETED("Выполнена"),
    DELETED("Удалена");

    private final String title;

    RequestStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
