package com.example.demo.model.user;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.example.demo.model.UserArticle.UserArticle;
import com.example.demo.model.article.Article;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
	
    private boolean loggedIn;
    
	@JsonIgnore
	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<UserArticle> savedArticles = new HashSet<>();
	
	public User() {
		
	}
	
	public User(String name, String email, String password) {
		setName(name);
		setEmail(email);
		setPassword(password);
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

	public Set<UserArticle> getSavedArticles() {
		return savedArticles;
	}

	public void setSavedArticles(Set<UserArticle> savedArticles) {
		this.savedArticles = savedArticles;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public void addArticle(Article article) {
        UserArticle userArticle = new UserArticle(this, article);
        savedArticles.add(userArticle);
        article.getUsers().add(userArticle);
    }
 
    public void removeArticle(Article article) {
        for (Iterator<UserArticle> iterator = savedArticles.iterator();
             iterator.hasNext(); ) {
            UserArticle userArticle = iterator.next();
             
            if (userArticle.getUser().equals(this) &&
                    userArticle.getArticle().equals(article)) {
                iterator.remove();
                userArticle.getArticle().getUsers().remove(userArticle);
                userArticle.setUser(null);
                userArticle.setArticle(null);
            }
        }
    }
}
