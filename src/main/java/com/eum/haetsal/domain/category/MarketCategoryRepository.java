package com.eum.haetsal.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketCategoryRepository extends JpaRepository<MarketCategory,Long> {
    Optional<MarketCategory> findByContents(String content);
}
