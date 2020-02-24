package de.adorsys.xs2a.adapter.codegen;

public class AdditionalQueryParams extends Param {

    public AdditionalQueryParams(String name, String description) {
        super(name, description);
    }

    public String getParamSchema() {
        return "\n" +
            "      \"" + name + "\": {\n" +
            "        \"name\": \"" + name + "\",\n" +
            "        \"in\": \"query\",\n" +
            "        \"description\": \"" + description + "\",\n" +
            "        \"schema\": {\n" +
            "          \"type\": \"object\",\n" +
            "          \"additionalProperties\": {\n" +
            "            \"type\": \"string\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"required\": false\n" +
            "      },";
    }
}
