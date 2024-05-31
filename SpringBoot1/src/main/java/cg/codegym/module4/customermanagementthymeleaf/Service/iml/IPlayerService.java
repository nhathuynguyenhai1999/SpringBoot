package cg.codegym.module4.customermanagementthymeleaf.Service.iml;

import cg.codegym.module4.customermanagementthymeleaf.Model.Player;
import cg.codegym.module4.customermanagementthymeleaf.Model.Positions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPlayerService extends IGenerateService<Player> {
    Iterable<Player> findAllByProvince(Positions province);
    Page<Player> findAll(Pageable pageable);
    Page<Player> findAll1(Pageable pageable) throws Exception;
    Player findOne(Long id) throws Exception;
    Page<Player> findAllByFirstNameContaining(Pageable pageable, String name);
    Player findByID(Long id);
}
