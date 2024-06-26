package com.eum.haetsal.domain.report;

import com.eum.haetsal.domain.marketpost.MarketPost;
import com.eum.haetsal.domain.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    boolean existsByMarketPostAndProfile(MarketPost marketPost, Profile profile);
}
