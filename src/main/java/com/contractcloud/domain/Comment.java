package com.contractcloud.domain;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Comment.
 */

@Document
public class Comment implements Serializable
{

	private static final long	serialVersionUID	= 1L;

	@Field("author")
	private String				author;

	@Field("mail")
	private String				mail;

	@Field("comment")
	private String				comment;

	public String getAuthor()
	{
		return author;
	}

	public Comment author(String author)
	{
		this.author = author;
		return this;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public String getMail()
	{
		return mail;
	}

	public Comment mail(String mail)
	{
		this.mail = mail;
		return this;
	}

	public void setMail(String mail)
	{
		this.mail = mail;
	}

	public String getComment()
	{
		return comment;
	}

	public Comment comment(String comment)
	{
		this.comment = comment;
		return this;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	@Override
	public String toString()
	{
		return "Comment{" +
				", author='" + getAuthor() + "'" +
				", mail='" + getMail() + "'" +
				", comment='" + getComment() + "'" +
				"}";
	}
}
