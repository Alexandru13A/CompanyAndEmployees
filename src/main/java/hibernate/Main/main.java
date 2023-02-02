package hibernate.Main;

import hibernate.Service.CompanyService;
import hibernate.Service.EmployeeService;
import hibernate.util.HibernateUtil;
import org.hibernate.Session;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {


        boolean isInLoop = true;

        while (isInLoop) {
            System.out.println("1.Employee , 2.Company, 3.EXIT");
            Scanner sc = new Scanner(System.in);
            int choise = sc.nextInt();
            switch (choise) {
                case 1:
                    EmployeeService.employeeMenu();
                    break;
                case 2:
                    CompanyService.companyMenu();
                    break;
                case 3:
                    isInLoop = false;
                    break;
                default:
                    System.out.println("NOT A VALID CHOISE TRY AGAIN !");
            }
        }


    }
}
