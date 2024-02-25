package com.eum.haetsal.domain.apply;

import com.eum.haetsal.domain.marketpost.MarketPost;
import com.eum.haetsal.domain.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplyRepository extends JpaRepository<Apply,Long> {
    Optional<List<Apply>> findByMarketPostOrderByCreateDateDesc(MarketPost marketPost);

    Boolean existsByProfileAndMarketPost(Profile profile, MarketPost marketPost);
    Optional<Apply> findByProfileAndMarketPost(Profile profile, MarketPost marketPost);

    Optional<List<Apply>> findByProfile(Profile user);
    @Modifying
    @Query("DELETE FROM Apply a WHERE a.profile = :applicant AND a.marketPost IN :posts AND a.status = 'WAITING'")
    void deleteApplyByApplicantAndPostIn(@Param("applicant") Profile applicant, @Param("posts") List<MarketPost> posts);
    @Query("SELECT a FROM Apply a WHERE a.profile = :applicant AND a.marketPost IN :posts AND a.status = 'TRADING' OR a.status ='WAITING'")
    Optional<List<Apply>> findTradingAppliesByApplicantAndPostIn(@Param("applicant") Profile applicant, @Param("posts") List<MarketPost> posts);

    @Query("SELECT a FROM Apply a WHERE a.profile = :profile AND a.marketPost.isDeleted = false ")
    Optional<List<Apply>> findByProfileIsDeletedFalse(@Param("profile") Profile profile);




}
