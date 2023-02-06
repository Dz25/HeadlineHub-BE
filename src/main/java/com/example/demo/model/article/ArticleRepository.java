package com.example.demo.model.article;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	List<Article> findByTitle(String title);
	Optional<Article> findByUrl(String url);
	Optional<Article> findById(String id);
	
}
