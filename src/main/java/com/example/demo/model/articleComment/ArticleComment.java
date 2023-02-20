package com.example.demo.model.articleComment;

import com.example.demo.model.article.Article;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Table(name = "articleComment")
public class ArticleComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "postedTime")
    private LocalDate postedTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Article article;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(LocalDate postedTime) {
        this.postedTime = postedTime;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public ArticleComment(){

    }

    public ArticleComment(String comment, Long userId, Article article){
        this.comment = comment;
        this.userId = userId;
        this.article = article;
        this.postedTime = LocalDate.now();
    }
}
