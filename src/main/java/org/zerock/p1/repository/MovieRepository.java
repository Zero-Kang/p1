package org.zerock.p1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.p1.entity.Movie;
import org.zerock.p1.repository.search.MovieSearch;

public interface MovieRepository extends JpaRepository<Movie, Long>, MovieSearch {
}
