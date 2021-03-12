package de.adorsys.xs2a.adapter.codegen;

class Header extends Param {
    public Header(String name, String description) {
        super(name, description);
    }

    @Override
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
