package in.kodwoessel.springrestapi.model;

import in.kodwoessel.springrestapi.request.EmployeeRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;


@Setter
@Getter
@ToString
@Entity
@Table(name="tbl_employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Name should not be null")
	private String name;

	private Long age = 0L;

	private String location;

	@Email(message = "Please enter a valid email address")
	private String email;

	@JoinColumn(name = "department_id")
	@ManyToOne
	private Department department;

	@CreationTimestamp
	@Column(name="created_at", nullable = false, updatable = false)
	private Date createdAt;

	@UpdateTimestamp
	@Column(name="updated_at")
	private Date updatedAt;

	public Employee() {

	}

	public Employee(EmployeeRequest req){
		this.name = req.getName();
		this.age = req.getAge();
		this.email = req.getEmail();
		this.location = req.getLocation();
	}
}
