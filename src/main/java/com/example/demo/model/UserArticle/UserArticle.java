package com.example.demo.model.UserArticle;

import java.time.LocalDate;

import com.example.demo.model.article.Article;
import com.example.demo.model.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class UserArticle {

	@EmbeddedId
	private UserArticleKey id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("articleId")
	@JoinColumn(name = "article_id")
	private Article article;
	
	@Column(name = "comment")
	private String comment;
	
	@Column(name = "created_on")
    private LocalDate createdOn =  LocalDate.now();

	public UserArticleKey getId() {
		return id;
	}

	public void setId(UserArticleKey id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDate getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDate createdOn) {
		this.createdOn = createdOn;
	}

	public UserArticle() {
		
	}
	
	public UserArticle(User user, Article article) {
		this.user = user;
		this.article = article;
	}
	
}
