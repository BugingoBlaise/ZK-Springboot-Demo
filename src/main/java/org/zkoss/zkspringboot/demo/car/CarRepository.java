package org.zkoss.zkspringboot.demo.car;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("select c from Car c where lower(c.make) like lower(concat('%', :q, '%')) or lower(c.model) like lower(concat('%', :q, '%'))")
    Page<Car> search(@Param("q") String q, Pageable pageable);
}
