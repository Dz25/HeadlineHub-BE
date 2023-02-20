package com.example.demo.model.articleComment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment,Long> {
    List<ArticleComment> findByArticleId(String id);
}
