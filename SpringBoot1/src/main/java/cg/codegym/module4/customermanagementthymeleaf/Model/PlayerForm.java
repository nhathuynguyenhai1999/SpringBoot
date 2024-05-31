package cg.codegym.module4.customermanagementthymeleaf.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;



@Setter
@Getter
public class PlayerForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

//    @ManyToOne
//    @JoinColumn(name = "positions_id")
    private Positions province;
    private MultipartFile img;

    public PlayerForm(){}

    public PlayerForm(Long id, String firstName, String lastName, Positions province, MultipartFile img) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.province = province;
        this.img = img;
    }

}
