package cg.codegym.minitest.springboot2.Controller.API;

import cg.codegym.minitest.springboot2.Model.Computer;
import cg.codegym.minitest.springboot2.Service.iml.IComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/computer1")
public class ComputerAPIController1 {
    @Autowired
    public IComputerService computerService;
    @PostMapping
    public ResponseEntity<Computer> createComputer(@RequestBody Computer computer) {

        return new ResponseEntity<>(computerService.save(computer), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Iterable<Computer>> listComputers() {
        return new ResponseEntity<>(computerService.findAll(), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Computer> deleteComputer(@PathVariable Long id) {
        Optional<Computer> smartphoneOptional = computerService.findById(id);
        if (!smartphoneOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        computerService.remove(id);
        return new ResponseEntity<>(smartphoneOptional.get(), HttpStatus.NO_CONTENT);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Computer> editComputer(@PathVariable Long id) {
        Optional<Computer> smartphone= computerService.findById(id);
        return new ResponseEntity<>(smartphone.get(), HttpStatus.OK);
    }
    @PutMapping ("/{id}")
    public ResponseEntity<Computer> editComputer1(@PathVariable Long id, @RequestBody Computer computer) {
        Computer sm = new Computer();
        sm.setId(id);
        sm.setName(computer.getName());
        sm.setCode(computer.getCode());
        sm.setType(computer.getType());
        return new ResponseEntity<>(computerService.save(sm), HttpStatus.OK);
    }
}
