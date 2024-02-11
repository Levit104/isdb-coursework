package levit104.isdb.coursework.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static levit104.isdb.coursework.validation.ErrorMessages.INVALID_EMAIL;
import static levit104.isdb.coursework.validation.ErrorMessages.NOT_BLANK;

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //    @NotNull
    @NotBlank(message = NOT_BLANK)
    private String firstName;

    //    @NotNull
    @NotBlank(message = NOT_BLANK)
    private String lastName;

    //    @NotNull
    @NotBlank(message = NOT_BLANK)
    @Email(message = INVALID_EMAIL)
    @Column(unique = true)
    private String email;

    //    @NotNull
    @NotBlank(message = NOT_BLANK)
    @Column(unique = true)
    private String telNumber;

    //    @NotNull
    @NotBlank(message = NOT_BLANK)
    @Column(unique = true)
    private String username;

    //    @NotNull
    @NotBlank(message = NOT_BLANK)
    private String password;

    private String role;
}
