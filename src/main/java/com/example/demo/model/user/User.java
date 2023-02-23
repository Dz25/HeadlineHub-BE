package com.example.demo.model.user;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.model.userArticle.UserArticle;
import com.example.demo.model.article.Article;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


//User is the owner, so operations to saved article or delete articles is done by set user
@Entity
@Table(name = "users")
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
	
    
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Set<UserArticle> articles = new HashSet<>();
	
	public User() {
		
	}

	public User(String email, String password){
		this.email = email;
		this.password = password;
	}

	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
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

	public Set<UserArticle> getArticles() {
		return articles;
	}

	public void setArticles(Set<UserArticle> articles) {
		this.articles = articles;
	}

	
	public void addArticle(Article article) {
        UserArticle userArticle = new UserArticle(this, article);
        this.articles.add(userArticle);
//        article.getUsers().add(userArticle);
    }
 
    public void removeArticle(Article article) {
        UserArticle userArticle = new UserArticle(this,article);
		this.articles.remove(userArticle);
    }
}
