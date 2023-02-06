package com.example.demo.model.user;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.model.article.Article;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@ManyToMany(cascade = {
        CascadeType.ALL
    })
	@JoinTable(
			name = "savedArticle",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "article_id")
			)
	private Set<Article> savedArticles = new HashSet<Article>();
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Article> getSavedArticles() {
		return savedArticles;
	}

	public void setSavedArticles(Set<Article> savedArticles) {
		this.savedArticles = savedArticles;
	}

	public User() {
		
	}
	
	public User(String name, String email, String password) {
		setName(name);
		setEmail(email);
		setPassword(password);
	}
}
