package org.zkoss.zkspringboot.demo.car;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CarService {

    private final CarRepository repository;

    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    public Page<Car> list(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (query == null || query.isBlank()) {
            return repository.findAll(pageable);
        }
        return repository.search(query, pageable);
    }

    public Optional<Car> findById(Long id) {
        return repository.findById(id);
    }

    public Car save(Car car) {
        return repository.save(car);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
