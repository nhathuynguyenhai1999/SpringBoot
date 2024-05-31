package cg.codegym.minitest.springboot2.Controller.API;

import cg.codegym.minitest.springboot2.Model.Computer;
import cg.codegym.minitest.springboot2.Service.iml.IComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/computers")
public class ComputerAPIController {

    @Autowired
    private IComputerService computerService;

    @GetMapping
    public ResponseEntity<Iterable<Computer>> findAllComputers() {
        List<Computer> computers = (List<Computer>) computerService.findAll();
        if (computers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(computers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Computer> findById(@PathVariable Long id) {
        Optional<Computer> computerOptional = computerService.findById(id);
        return computerOptional.map(computer -> new ResponseEntity<>(computer, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Computer> saveComputer(@RequestBody Computer computer) {
        return new ResponseEntity<>(computerService.save(computer), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Computer> updateComputer(@PathVariable Long id, @RequestBody Computer computer) {
        Optional<Computer> computerOptional = computerService.findById(id);
        if (!computerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        computer.setId(computerOptional.get().getId());
        return new ResponseEntity<>(computerService.save(computer), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComputer(@PathVariable Long id) {
        Optional<Computer> computerOptional = computerService.findById(id);
        if (!computerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        computerService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
