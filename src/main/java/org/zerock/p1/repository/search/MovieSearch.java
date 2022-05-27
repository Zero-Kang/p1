package org.zerock.p1.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.p1.entity.Movie;

public interface MovieSearch {



    Page<Object[]> getList1(String keyword, Pageable pageable);


    Page<Movie> getList2(String keyword, Pageable pageable);

    Page<Object[]> getList3(String keyword, Pageable pageable);
}
