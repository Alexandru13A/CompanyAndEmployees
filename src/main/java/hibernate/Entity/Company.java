package hibernate.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "company")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Company {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long company_id;
    private String company_name;


    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();


    public Company(String company_name) {
        this.company_name = company_name;
    }


}
