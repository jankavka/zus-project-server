package cz.kavka.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    @Email
    private String email;

    @NotBlank(message = "Vyplňte uživatelské heslo")
    @Size(message = "Heslo musí mít alespoň 6 znaků", min = 6)
    private String password;

    @JsonProperty("isAdmin")
    private boolean isAdmin;

}
