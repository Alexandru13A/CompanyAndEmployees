package hibernate.Service;

import hibernate.Entity.Company;
import hibernate.Entity.Employee;
import hibernate.Main.main;
import hibernate.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.query.Query;

import org.hibernate.cfg.Configuration;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public class EmployeeService {

    private static Configuration cfg;
    private static SessionFactory sf;
    private static Session session;


    public static void getAllEmployees() {
        sf = HibernateUtil.getSessionFactory();
        session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Employee");
        List<Employee> employees = query.list();
        for (Employee employee : employees) {
            System.out.println("ID: " + employee.getEmployee_id() + " First Name: " + employee.getFirstName() + " Last Name: "
                    + employee.getLastName() + " Country: " + employee.getCountry() + " Age: " +
                    employee.getAge() + " Employee Role: " + employee.getRole() + " Salary: " + employee.getSalary());
        }
    }

    public static void getEmployeeById(Scanner sc) {
        Company company = new Company();
        sf = HibernateUtil.getSessionFactory();
        session = sf.openSession();
        session.beginTransaction();
        System.out.println("Insert the Employee id: ");
        Long id = sc.nextLong();
        Query query = session.createQuery("from Employee where employee_id=: employee_id");
        query.setParameter("employee_id", id);
        if ((Employee) query.uniqueResult() == null) {
            System.out.println("The Employee with this id don't exists");
            getEmployeeById(sc);
        } else {
            Employee employee = (Employee) query.uniqueResult();
            System.out.println("ID: " + employee.getEmployee_id() + " First Name: " + employee.getFirstName() + " Last Name: "
                    + employee.getLastName() + " Country: " + employee.getCountry() + " Age: " +
                    employee.getAge() + " Employee Role: " + employee.getRole() + " Salary: " + employee.getSalary() + ", Company ID: " + company.getCompany_id());
            session.getTransaction();
            session.close();
        }
    }

    public static void insertEmployee(Scanner sc) {
        cfg = new Configuration().configure();
        sf = cfg.buildSessionFactory();
        Employee employee = new Employee();

        System.out.println("Employee First Name: ");
        String fName = sc.next();
        employee.setFirstName(fName);

        System.out.println("Employee Last Name: ");
        String lName = sc.next();
        employee.setLastName(lName);

        System.out.println("Employee Country");
        String country = sc.next();
        employee.setCountry(country);

        System.out.println("Employee Age: ");
        int age = sc.nextInt();
        employee.setAge(age);

        System.out.println("Employee Role: ");
        String role = sc.next();
        employee.setRole(role);

        System.out.println("Employee Salary: ");
        double salary = sc.nextDouble();
        employee.setSalary(salary);


        session = sf.openSession();
        session.beginTransaction();
        session.save(employee);
        session.getTransaction().commit();
        session.close();


    }

    public static void deleteEmployeeById(Scanner sc) {
        sf = HibernateUtil.getSessionFactory();
        session = sf.openSession();
        session.beginTransaction();
        System.out.println("Enter the id of the Employee you want to delete !");
        Long id = sc.nextLong();
        Query query = session.createQuery("delete from Employee where employee_id =:employee_id");
        query.setParameter("employee_id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();

    }


    public static void updateEmployeeById(Scanner sc) {
        sf = HibernateUtil.getSessionFactory();
        session = sf.openSession();
        session.beginTransaction();
        Employee employee = new Employee();
        System.out.println("Enter the id of the Employee you want to update:");
        Long id = sc.nextLong();
        Query query = session.createQuery("from Employee WHERE employee_id =: employee_id");
        query.setParameter("employee_id", id);
        if ((Employee) query.uniqueResult() == null) {
            System.out.println("NO EMPLOYEE FOUND WITH THIS ID");
            updateEmployeeById(sc);
        } else {
            query = session.createQuery("UPDATE Employee SET firstName =: firstName,lastName=:lastName,country=:country,age=:age,role=:role,salary=:salary WHERE employee_id=:employee_id");
            query.setParameter("employee_id", id);

            System.out.println("Employee First Name: ");
            String fName = sc.next();
            query.setParameter("firstName",fName);

            System.out.println("Employee Last Name: ");
            String lName = sc.next();
            query.setParameter("lastName",lName);

            System.out.println("Employee Country");
            String country = sc.next();
            query.setParameter("country",country);

            System.out.println("Employee Age: ");
            int age = sc.nextInt();
            query.setParameter("age",age);

            System.out.println("Employee Role: ");
            String role = sc.next();
            query.setParameter("role",role);

            System.out.println("Employee Salary: ");
            double salary = sc.nextDouble();
            query.setParameter("salary",salary);

            query.executeUpdate();
            session.getTransaction().commit();
        }
        session.close();
    }

    public static void setEmployeeToCompany(Scanner sc) {

        sf = HibernateUtil.getSessionFactory();
        session = sf.openSession();
        session.beginTransaction();
        System.out.println("Enter the ID of Employee you want to address at Company");
        long eid = sc.nextLong();
        Employee employee = session.get(Employee.class, eid);
        Query query = session.createQuery("UPDATE Employee SET company_id = company_id WHERE employee_id =: employee_id");
        query.setParameter("employee_id", Long.valueOf(eid));
        System.out.println("Enter the Company id:");
        Long id = sc.nextLong();
        Company company = session.get(Company.class, id);
        employee.setCompany(company);
        company.getEmployees().add(employee);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public static void employeeMenu() {
        Employee employee = new Employee();
        System.out.println("1.Get all employees  " + "\n" +
                "2.Get Employee by ID, " + "\n" +
                "3.Insert Employee, " + "\n" +
                "4.Delete Employee, " + "\n" +
                "5.Update Employee, " + "\n" +
                "6.Address Company to Employee" + "\n" +
                "7.Exit ");
        Scanner sc = new Scanner(System.in);
        int choise = sc.nextInt();
        switch (choise) {
            case 1:
                System.out.println("Employee List: ");
                getAllEmployees();
                break;
            case 2:
                System.out.println("Employee With ID" + employee.getEmployee_id() + "is: ");
                getEmployeeById(sc);
                break;
            case 3:
                System.out.println("INSERT THE EMPLOYEE");
                insertEmployee(sc);
                System.out.println("SUCCESSFUL ADDITION ! ");
                break;
            case 4:
                System.out.println("DELETE EMPLOYEE BY ID");
                deleteEmployeeById(sc);
                break;
            case 5:
                System.out.println("UPDATE THE EMPLOYEE");
                updateEmployeeById(sc);
            case 6:
                System.out.println("Address Company to Employee");
                setEmployeeToCompany(sc);
            case 7:
                new main();
                break;

            default:
                System.out.println("NOT A VALID CHOISE , TRY AGAIN !");
                employeeMenu();
        }
    }
}
