package cg.codegym.module4.customermanagementthymeleaf.Repository;

import cg.codegym.module4.customermanagementthymeleaf.Model.Positions;
import org.springframework.data.repository.CrudRepository;

public interface IPositionRepository extends CrudRepository<Positions, Long> {
}
