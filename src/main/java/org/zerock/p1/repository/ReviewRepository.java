package org.zerock.p1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.p1.entity.Movie;
import org.zerock.p1.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}

