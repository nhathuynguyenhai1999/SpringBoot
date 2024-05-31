package cg.codegym.minitest.springboot2.Repository;

import cg.codegym.minitest.springboot2.Model.Type;
import org.springframework.data.repository.CrudRepository;

public interface ITypeRepository extends CrudRepository<Type, Long> {
}
