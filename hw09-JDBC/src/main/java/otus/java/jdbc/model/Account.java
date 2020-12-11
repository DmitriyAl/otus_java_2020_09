package otus.java.jdbc.model;

import otus.java.jdbc.anotation.Id;

import java.util.UUID;

public class Account {
    @Id
    private UUID number;
    private String type;
    private float rest;

    public Account() {
    }

    public Account(String type, float rest) {
        this.type = type;
        this.rest = rest;
    }

    public Account(UUID number, String type, float rest) {
        this.number = number;
        this.type = type;
        this.rest = rest;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Account account = (Account) object;

        if (Float.compare(account.rest, rest) != 0) return false;
        return type != null ? type.equals(account.type) : account.type == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (rest != +0.0f ? Float.floatToIntBits(rest) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "number=" + number +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }
}
