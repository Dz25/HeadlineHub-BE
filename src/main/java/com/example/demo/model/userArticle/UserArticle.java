package com.example.demo.model.userArticle;

import java.time.LocalDate;
import java.util.Objects;

import com.example.demo.model.article.Article;
import com.example.demo.model.user.User;

import jakarta.persistence.*;

@Entity
public class UserArticle {

	@EmbeddedId
	private UserArticleKey id;
	
	@Override
	public int hashCode() {
		return Objects.hash(article, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserArticle other = (UserArticle) obj;
		return Objects.equals(article, other.article)
				&&  Objects.equals(user, other.user);
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("articleId")
	@JoinColumn(name = "article_id")
	private Article article;

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
		this.id = new UserArticleKey(user.getId(), article.getId());
	}
	
}
