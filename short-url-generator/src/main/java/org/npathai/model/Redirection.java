package org.npathai.model;

import java.sql.Timestamp;
import java.time.Clock;
import java.util.Objects;

public class Redirection {
    private String id;
    private final String longUrl;
    private long createdAtMillis;
    private long expiryTimeInMillis;

    public Redirection(String id, String lonUrl, long createdAtMillis, long expiryTimeInMillis) {
        this.id = id;
        this.longUrl = lonUrl;
        this.createdAtMillis = createdAtMillis;
        this.expiryTimeInMillis = expiryTimeInMillis;
    }

    public String id() {
        return id;
    }

    public String longUrl() {
        return longUrl;
    }

    public long createdAt() {
        return createdAtMillis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Redirection redirection = (Redirection) o;
        return Objects.equals(id, redirection.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public long expiryTimeInMillis() {
        return expiryTimeInMillis;
    }

    public boolean isExpired(Clock clock) {
        return expiryTimeInMillis <= clock.millis();
    }

    @Override
    public String toString() {
        return "Redirection{" +
                "id='" + id + '\'' +
                ", longUrl='" + longUrl + '\'' +
                ", createdAt=" + new Timestamp(createdAtMillis) +
                ", expiresAt=" + new Timestamp(expiryTimeInMillis) +
                '}';
    }
}
