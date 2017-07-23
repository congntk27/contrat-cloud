package com.contractcloud.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Signature.
 */

@Document
public class Signature implements Serializable
{

	private static final long	serialVersionUID	= 1L;

	@NotNull
	@Field("sign_by")
	private String				signBy;

	@Field("sign_date")
	private ZonedDateTime		signDate;

	public String getSignBy()
	{
		return signBy;
	}

	public Signature signby(String signby)
	{
		this.signBy = signby;
		return this;
	}

	public void setSignBy(String signby)
	{
		this.signBy = signby;
	}

	public ZonedDateTime getSignDate()
	{
		return signDate;
	}

	public Signature signdate(ZonedDateTime signdate)
	{
		this.signDate = signdate;
		return this;
	}

	public void setSignDate(ZonedDateTime signdate)
	{
		this.signDate = signdate;
	}

	@Override
	public String toString()
	{
		return "Signature{" +
				", signby='" + getSignBy() + "'" +
				", signdate='" + getSignDate() + "'" +
				"}";
	}
}
