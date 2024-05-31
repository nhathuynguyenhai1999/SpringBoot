package cg.codegym.minitest.springboot2.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "computer")
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Code không được để trống")
    @Pattern(regexp = "^CG.{6,}$", message = "Code phải bắt đầu bằng 'CG' và có tổng cộng 8 ký tự")
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    public Computer() {
    }

}