/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitymanagerdemo;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Address;
import model.Customer;

/**
 *
 * @author sarun
 */
public class EntityManagerDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(printAllCustomer());
        System.out.println(printCustomerByCity("Bangkok"));
    }

    public void persist(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public static void createData(Long id, String firstname, String lastname, String email, String street, String city, String country, String zipcode){
        Customer customer = new Customer(id, firstname, lastname, email); 
        Address address = new Address(id, street, city, zipcode, country); 
        address.setCustomerFk(customer);
        customer.setAddressId(address); 
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();


        em.getTransaction().begin();
        try {
            em.persist(address);
            em.persist(customer);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public static List<Customer> printAllCustomer(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createNamedQuery("Customer.findAll");
        List<Customer> customerList = (List<Customer>) query.getResultList();
        return customerList;
    }
    
    public static List<Address> printAllAddress(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createNamedQuery("Address.findAll");
        List<Address> addressList = (List<Address>) query.getResultList();
        return addressList;
    }
    
    public static List<Address> printCustomerByCity(String city){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createNamedQuery("Address.findByCity");
        query.setParameter("city", city);
        List<Address> addressList = (List<Address>) query.getResultList();
        return addressList;
    }
    
}
