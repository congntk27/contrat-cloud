package com.contractcloud.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Discussion.
 */

@Document(collection = "discussion")
public class Discussion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("contractid")
    private String contractid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContractid() {
        return contractid;
    }

    public Discussion contractid(String contractid) {
        this.contractid = contractid;
        return this;
    }

    public void setContractid(String contractid) {
        this.contractid = contractid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Discussion discussion = (Discussion) o;
        if (discussion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), discussion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Discussion{" +
            "id=" + getId() +
            ", contractid='" + getContractid() + "'" +
            "}";
    }
}
