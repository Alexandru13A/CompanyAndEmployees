package hibernate.Service;

import hibernate.Entity.Company;
import hibernate.Main.main;
import hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class CompanyService {

    private static Configuration cfg;
    private static SessionFactory sf;
    private static Session session;


    public static void getAllCompanys() {
        sf = HibernateUtil.getSessionFactory();
        session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from company");
        List<Company> companys = query.list();
        for (Company company : companys) {
            System.out.println("Company ID: " + company.getCompany_id() + " Company Name: "
                    + company.getCompany_name());
        }
    }

    public static void getCompanyByName(Scanner sc) {
        sf = HibernateUtil.getSessionFactory();
        session = sf.openSession();
        session.beginTransaction();
        System.out.println("Insert the Company Name: ");
        String name = sc.next();
        Query query = session.createQuery("from Company WHERE company_name =: company_name");
        query.setParameter("company_name", name);
        Company company = (Company) query.uniqueResult();
        System.out.println("Company id: " + company.getCompany_id() + ", Company Name: " + company.getCompany_name());
    }

    public static void insertCompany(Scanner sc) {
        cfg = new Configuration().configure();
        sf = HibernateUtil.getSessionFactory();
        Company company = new Company();

        System.out.println("Insert the Company name :");
        String name = sc.next();
        company.setCompany_name(name);

        session = sf.openSession();
        session.beginTransaction();
        session.save(company);
        session.getTransaction().commit();
        session.close();

    }

    public static void deleteCompany(Scanner sc) {

        sf = HibernateUtil.getSessionFactory();
        session = sf.openSession();
        session.beginTransaction();
        System.out.println("Insert The Company id:");
        long id = sc.nextInt();
        Query query = session.createQuery("delete from Company where company_id =:company_id");
        query.setParameter("company_id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();

    }

    public static void updateCompany(Scanner sc) {
        sf = HibernateUtil.getSessionFactory();
        session = sf.openSession();
        session.beginTransaction();
        Company company = new Company();
        System.out.println("Enter the id of Comapny you want to update: ");
        long id = sc.nextLong();
        Query query = session.createQuery("from Company where company_id =: company_id");
        query.setParameter("company_id", id);
        if ((Company) query.uniqueResult() == null) {
            System.out.println("NO COMPANY FOUND WITH THIS ID");
            updateCompany(sc);
        } else {
            query = session.createQuery("update Company set company_name =: company_name where company_id =: company_id");
            query.setParameter("company_id", id);
            System.out.println("Enter the new Company name: ");
            String name = sc.next();
            query.setParameter("company_name", name);
            query.executeUpdate();
            session.getTransaction().commit();


        }
        session.close();
    }

    public static void getCompanyWithAllEmployees(Scanner sc) {
        sf = HibernateUtil.getSessionFactory();
        session = sf.openSession();
        session.beginTransaction();
        System.out.println("Insert The Company ID");
        Long id = sc.nextLong();
        Query query = session.createQuery("FROM Company c LEFT JOIN FETCH c.employees WHERE c.id = :company_id");
        query.setParameter("company_id", id);
        Company company = (Company) query.uniqueResult();
        System.out.println("Company ID: " + company.getCompany_id() + ", Company name: " + company.getCompany_name() + "\n" + "Number of Employees: " + company.getEmployees().size());


    }

    public static void companyMenu() {
        Company company = new Company();
        System.out.println("1.Get all companys  " + "\n" +
                "2.Get Companys by Name, " + "\n" +
                "3.Insert Company, " + "\n" +
                "4.Delete Company, " + "\n" +
                "5.Update Company, " + "\n" +
                "6.Update Company, " + "\n" +
                "7.Get all Employees from Company, " + "\n" +
                "8.Exit ");
        Scanner sc = new Scanner(System.in);
        int choise = sc.nextInt();
        switch (choise) {
            case 1:
                System.out.println("Company List: ");
                getAllCompanys();
                break;
            case 2:
                System.out.println("Company With Name" + company.getCompany_name() + "is: ");
                getCompanyByName(sc);
                break;
            case 3:
                System.out.println("INSERT THE COMPANY");
                insertCompany(sc);
                System.out.println("INSERTION SUCCESSFUL ! ");
                break;
            case 4:
                System.out.println("DELETE COMPANY BY ID");
                deleteCompany(sc);
                break;
            case 5:
                System.out.println("UPDATE THE COMPANY");
                updateCompany(sc);
            case 6:
                getCompanyWithAllEmployees(sc);
                break;
            case 7:
                System.out.println("All Employees from Company");
                getCompanyWithAllEmployees(sc);
            case 8:
                new main();
                break;

            default:
                System.out.println("NOT A VALID CHOOSE , TRY AGAIN !");
                companyMenu();
        }
    }


}
