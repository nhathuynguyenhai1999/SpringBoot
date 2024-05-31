package cg.codegym.minitest.springboot2.Service.iml;

import cg.codegym.minitest.springboot2.Model.Computer;
import cg.codegym.minitest.springboot2.Model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IComputerService extends IGenerateService<Computer> {
    Iterable<Computer> findAllByType(Type type);
    Page<Computer> findAll(Pageable pageable);
    Computer findOne(Long id) throws Exception;
    Page<Computer> findAllByNameContaining(Pageable pageable, String name);
    //fix
    Computer findByID(Long id);
}
