package com.contractcloud.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.contractcloud.domain.enumeration.ContratStatus;

/**
 * A Contract.
 */

@Document(collection = "contract")
public class Contract implements Serializable
{

	private static final long	serialVersionUID	= 1L;

	@Id
	private String				id;

	@NotNull
	@Field("title")
	private String				title;

	@Field("description")
	private String				description;

	@Field("signature_A")
	private Signature			signatureA;

	@Field("signature_B")
	private Signature			signatureB;

	@Field("status")
	private ContratStatus		status;

	@Field("discussions")
	private Discussion[]		discussions;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public Contract title(String title)
	{
		this.title = title;
		return this;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getDescription()
	{
		return description;
	}

	public Contract description(String description)
	{
		this.description = description;
		return this;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public ContratStatus getStatus()
	{
		return status;
	}

	public Contract status(ContratStatus status)
	{
		this.status = status;
		return this;
	}

	public void setStatus(ContratStatus status)
	{
		this.status = status;
	}

	public Signature getSignatureA()
	{
		return signatureA;
	}

	public void setSignatureA(Signature signatureA)
	{
		this.signatureA = signatureA;
	}

	public Signature getSignatureB()
	{
		return signatureB;
	}

	public void setSignatureB(Signature signatureB)
	{
		this.signatureB = signatureB;
	}

	public Discussion[] getDiscussions()
	{
		return discussions;
	}

	public void setDiscussions(Discussion[] discussions)
	{
		this.discussions = discussions;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		Contract contract = (Contract) o;
		if (contract.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), contract.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "Contract{" +
				"id=" + getId() +
				", title='" + getTitle() + "'" +
				", description='" + getDescription() + "'" +
				", status='" + getStatus() + "'" +
				"}";
	}
}
