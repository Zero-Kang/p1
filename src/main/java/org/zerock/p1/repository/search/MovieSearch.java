package org.zerock.p1.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieSearch {



    Page<Object[]> getList1(String keyword, Pageable pageable);


}
