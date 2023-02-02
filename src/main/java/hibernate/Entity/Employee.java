package hibernate.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee")
@NoArgsConstructor
@Getter
@Setter

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id")
    private Long employee_id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "country")
    private String country;
    @Column(name = "Age")
    private int age;
    @Column(name = "Role")
    private String role;
    @Column(name = "Salary")
    private double salary;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;




    public Employee(String firstName, String lastName, String country, int age, String role, double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.age = age;
        this.role = role;
        this.salary = salary;
    }



    @Override
    public String toString() {
        return "Employee{" +
                "id=" + employee_id +
                ", firstname='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", country='" + country + '\'' +
                ", age='" + age + '\'' +
                ", role='" + role + '\'' +
                ", salary=" + salary +
                '}';
    }
}
