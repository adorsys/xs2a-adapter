package de.adorsys.xs2a.adapter.codegen;

public abstract class Param {
    protected final String name;
    protected final String description;

    public Param(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getParamRef() {
        return "{\"$ref\":\"#/components/parameters/" + name + "\"},";
    }

    public abstract String getParamSchema();
}
