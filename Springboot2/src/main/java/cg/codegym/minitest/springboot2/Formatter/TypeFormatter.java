package cg.codegym.minitest.springboot2.Formatter;

import cg.codegym.minitest.springboot2.Model.Type;
import cg.codegym.minitest.springboot2.Service.iml.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
public class TypeFormatter implements Formatter<Type> {
    private final ITypeService provinceService;

    @Autowired
    public TypeFormatter(ITypeService provinceService) {
        this.provinceService = provinceService;
    }

    @Override
    public Type parse(String text, Locale locale) {
        Optional<Type> provinceOptional = provinceService.findById(Long.parseLong(text));
        return provinceOptional.orElse(null);
    }

    @Override
    public String print(Type object, Locale locale) {
        return "[" + object.getId() + ", " +object.getName() + "]";
    }
}
