package de.adorsys.xs2a.adapter.codegen;

public class Header {
    private final String name;
    private final String description;

    public Header(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getParamRef() {
        return "{\"$ref\":\"#/components/parameters/" + name + "\"},";
    }

    public String getParamSchema() {
        return "\n" +
            "      \"" + name + "\": {\n" +
            "        \"name\": \"" + name + "\",\n" +
            "        \"in\": \"header\",\n" +
            "        \"description\": \"" + description + "\",\n" +
            "        \"schema\": {\n" +
            "          \"type\": \"string\"\n" +
            "        },\n" +
            "        \"required\": false\n" +
            "      },";
    }
}
