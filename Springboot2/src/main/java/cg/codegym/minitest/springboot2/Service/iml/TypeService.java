package cg.codegym.minitest.springboot2.Service.iml;

import cg.codegym.minitest.springboot2.Model.Type;
import cg.codegym.minitest.springboot2.Repository.ITypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class TypeService implements ITypeService {

    @Autowired
    private ITypeRepository iTypeRepository;

    @Override
    public Iterable<Type> findAll() {
        return iTypeRepository.findAll();
    }

    @Override
    public Type save(Type province) {
        return iTypeRepository.save(province);
    }

    @Override
    public Optional<Type> findById(Long id) {
        return iTypeRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        iTypeRepository.deleteById(id);
    }
}
