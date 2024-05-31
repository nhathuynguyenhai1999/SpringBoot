package cg.codegym.module4.customermanagementthymeleaf.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "positions")
public class Positions {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Positions() {
    }

    @Column(unique = true)


    public String getName() {
        return name;
    }

}