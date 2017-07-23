package com.contractcloud.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Signature.
 */

@Document(collection = "signature")
public class Signature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("signby")
    private String signby;

    @Field("signdate")
    private ZonedDateTime signdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSignby() {
        return signby;
    }

    public Signature signby(String signby) {
        this.signby = signby;
        return this;
    }

    public void setSignby(String signby) {
        this.signby = signby;
    }

    public ZonedDateTime getSigndate() {
        return signdate;
    }

    public Signature signdate(ZonedDateTime signdate) {
        this.signdate = signdate;
        return this;
    }

    public void setSigndate(ZonedDateTime signdate) {
        this.signdate = signdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Signature signature = (Signature) o;
        if (signature.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), signature.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Signature{" +
            "id=" + getId() +
            ", signby='" + getSignby() + "'" +
            ", signdate='" + getSigndate() + "'" +
            "}";
    }
}
