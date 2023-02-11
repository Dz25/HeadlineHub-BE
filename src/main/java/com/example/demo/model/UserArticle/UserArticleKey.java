package com.example.demo.model.UserArticle;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserArticleKey implements Serializable{

	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "article_id")
	private Long articleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public UserArticleKey() {
		
	}
	
	public UserArticleKey(Long userId, Long articleId) {
		super();
		this.userId = userId;
		this.articleId = articleId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(articleId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserArticleKey other = (UserArticleKey) obj;
		return Objects.equals(articleId, other.articleId) && Objects.equals(userId, other.userId);
	}
	
	
}
