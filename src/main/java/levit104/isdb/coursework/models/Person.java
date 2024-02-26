package levit104.isdb.coursework.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import levit104.isdb.coursework.validation.ErrorMessages;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = ErrorMessages.EMPTY_VALUE)
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = ErrorMessages.EMPTY_VALUE)
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = ErrorMessages.EMPTY_VALUE)
    @Email(message = ErrorMessages.INVALID_EMAIL)
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = ErrorMessages.EMPTY_VALUE)
    @Column(nullable = false, unique = true)
    private String telNumber;

    @NotBlank(message = ErrorMessages.EMPTY_VALUE)
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = ErrorMessages.EMPTY_VALUE)
    @Column(nullable = false)
    private String password;

    private String role;
}
