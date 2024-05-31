package cg.codegym.minitest.springboot2.Repository;

import cg.codegym.minitest.springboot2.Model.Computer;
import cg.codegym.minitest.springboot2.Model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface IComputerRepository extends CrudRepository<Computer,Long> {
    Iterable<Computer> findAllByType(Type type);

    Page<Computer> findAll(Pageable pageable);

    Page<Computer> findAllByNameContaining(Pageable pageable, String name);

    boolean existsByCode(String code);
}
