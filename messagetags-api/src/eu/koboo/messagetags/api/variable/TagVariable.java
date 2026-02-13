package eu.koboo.messagetags.api.variable;

public record TagVariable(String name, String value) {

    public TagVariable(String name, String value) {
        if(name == null) {
            throw new NullPointerException("Name cannot be null!");
        }
        name = name.trim();
        if(name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        this.name = name;
        if(value == null) {
            throw new NullPointerException("Value cannot be null!");
        }
        value = value.trim();
        if(value.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be empty!");
        }
        this.value = value;
    }

    public static TagVariable of(String name, String value) {
        return new TagVariable(name, value);
    }
}
