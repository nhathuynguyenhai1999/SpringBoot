package cg.codegym.module4.customermanagementthymeleaf.Formatter;

import cg.codegym.module4.customermanagementthymeleaf.Model.Positions;
import cg.codegym.module4.customermanagementthymeleaf.Service.iml.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
public class ProvinceFormatter implements Formatter<Positions> {
    private final IPositionService provinceService;

    @Autowired
    public ProvinceFormatter(IPositionService provinceService) {
        this.provinceService = provinceService;
    }

    @Override
    public Positions parse(String text, Locale locale) {
        Optional<Positions> provinceOptional = provinceService.findById(Long.parseLong(text));
        return provinceOptional.orElse(null);
    }

    @Override
    public String print(Positions object, Locale locale) {
        return "[" + object.getId() + ", " +object.getName() + "]";
    }
}
