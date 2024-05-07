package com.eum.haetsal.domain.marketpost;


import com.eum.haetsal.controller.DTO.request.enums.MarketType;
import com.eum.haetsal.domain.category.MarketCategory;
import com.eum.haetsal.domain.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MarketPostRepository extends JpaRepository<MarketPost,Long> {

    @Query("SELECT e FROM MarketPost e " +
            "WHERE (:marketCategory IS NULL OR e.marketCategory = :marketCategory) " +
            "AND (:marketType IS NULL OR e.marketType = :marketType) " +
            "AND (:status IS NULL OR e.status = :status) " +
            "AND e.isDeleted = false " +
            "AND (e.profile NOT IN :profiles) " + // Exclude blocked users
            "ORDER BY e.pullUpDate DESC")
    Optional<List<MarketPost>> findByFiltersWithoutBlocked(
            @Param("marketCategory") MarketCategory marketCategory,
            @Param("marketType") MarketType marketType,
            @Param("status") Status status,
            @Param("profiles") List<Profile> profiles
    );
    @Query("SELECT e FROM MarketPost e " +
            "WHERE (:marketCategory IS NULL OR e.marketCategory = :marketCategory) " +
            "AND (:marketType IS NULL OR e.marketType = :marketType) " +
            "AND (:status IS NULL OR e.status = :status) " +
            "AND e.isDeleted = false " +
            "ORDER BY e.pullUpDate DESC")
    Optional<List<MarketPost>> findByFilters(
            @Param("marketCategory") MarketCategory marketCategory,
            @Param("marketType") MarketType marketType,
            @Param("status") Status status
    );





    Optional<List<MarketPost>> findByProfileAndIsDeletedFalseOrderByPullUpDateDesc(Profile profile);
    Optional<List<MarketPost>> findByProfileAndIsDeletedFalse(Profile profile);

    @Query("SELECT mp FROM MarketPost mp " +
            "WHERE mp.title LIKE %:title% " +
            "AND mp.isDeleted = false " +
            "AND (mp.profile NOT IN :profiles) " + // Exclude blocked users
            "ORDER BY mp.pullUpDate DESC")
    Optional<List<MarketPost>> findByKeywordsWithoutBlocked(
            @Param("title") String title,
            @Param("profiles") List<Profile> profiles
    );
    @Query("SELECT mp FROM MarketPost mp " +
            "WHERE mp.title LIKE %:title% " +
            "AND mp.isDeleted = false " +
            "ORDER BY mp.pullUpDate DESC")
    Optional<List<MarketPost>> findByKeywords(
            @Param("title") String title
    );
    @Query("SELECT mp FROM MarketPost mp " +
            "WHERE mp.profile = :profile " +
            "AND mp.isDeleted = false " +
            "AND (mp.status  IN :statusList) ")

    List<MarketPost> findByProfileAndDeletedAndStatusIn(@Param("profile") Profile profile,@Param("statusList") List<Status> statusList);

}
