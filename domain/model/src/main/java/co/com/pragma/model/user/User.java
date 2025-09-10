package co.com.pragma.model.user;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private String name;
    private String lastName;
    private Long document;
    private String email;
    private Long salary;
    private LocalDate birthDate;

}
