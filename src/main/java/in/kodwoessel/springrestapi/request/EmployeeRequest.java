package in.kodwoessel.springrestapi.request;

import in.kodwoessel.springrestapi.model.Department;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
public class EmployeeRequest {
    @NotBlank(message = "Name should not be null")
    private String name;

    private String department;

    private Long age = 0L;

    private String location;

    @Email(message = "Please enter a valid email address")
    private String email;


}
