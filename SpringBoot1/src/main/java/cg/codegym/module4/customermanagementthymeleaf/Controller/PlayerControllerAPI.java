package cg.codegym.module4.customermanagementthymeleaf.Controller;

import cg.codegym.module4.customermanagementthymeleaf.Exception.DuplicateEmailException;
import cg.codegym.module4.customermanagementthymeleaf.Model.Player;
import cg.codegym.module4.customermanagementthymeleaf.Service.iml.IPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers/players")
public class PlayerControllerAPI {
    @Autowired
    private IPlayerService playerService;
    @GetMapping
    public ResponseEntity<Iterable<Player>> showPlayerList(){
        List<Player> playerList = (List<Player>) playerService.findAll();
        if (playerList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(playerList,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Player> findById(@PathVariable Long id) {
        Optional<Player> computerOptional = playerService.findById(id);
        return computerOptional.map(player -> new ResponseEntity<>(player, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<Player> saveComputer(@RequestBody Player player) throws DuplicateEmailException {
        return new ResponseEntity<>(playerService.save(player), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Player> updateFootballPlayer(@PathVariable Long id, @RequestBody Player player) throws DuplicateEmailException {
        Optional<Player> computerOptional = playerService.findById(id);
        if (!computerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        player.setId(computerOptional.get().getId());
        return new ResponseEntity<>(playerService.save(player), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComputer(@PathVariable Long id) {
        Optional<Player> playerOptional = playerService.findById(id);
        if (!playerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        playerService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
