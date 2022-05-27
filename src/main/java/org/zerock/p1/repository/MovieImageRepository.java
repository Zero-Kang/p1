package org.zerock.p1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.p1.entity.Movie;
import org.zerock.p1.entity.MovieImage;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {
}

