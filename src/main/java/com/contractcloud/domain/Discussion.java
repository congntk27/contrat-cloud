package com.contractcloud.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Discussion.
 */

@Document(collection = "discussion")
public class Discussion implements Serializable
{

	private static final long	serialVersionUID	= 1L;

	@Id
	private String				id;

	@NotNull
	@Field("contract_id")
	private String				contractId;

	@Field("comments")
	private Comment[]			comments;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getContractId()
	{
		return contractId;
	}

	public Discussion contractid(String contractid)
	{
		this.contractId = contractid;
		return this;
	}

	public void setContractId(String contractid)
	{
		this.contractId = contractid;
	}

	public Comment[] getComments()
	{
		return comments;
	}

	public void setComments(Comment[] comments)
	{
		this.comments = comments;
	}

	@Override
	public String toString()
	{
		return "Discussion{" +
				", contractid='" + getContractId() + "'" +
				"}";
	}
}
