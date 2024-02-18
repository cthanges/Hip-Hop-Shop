/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hiphopshop;

/**
 *
 * @author Charran Thangeswaran
 */
import java.io.IOException;
import java.util.ArrayList;

public class Owner{
    private static final Files files = new Files();
    protected static ArrayList<Album> albums = new ArrayList<>();
    private static final ArrayList<Customer> customers = new ArrayList<>();

    public void restockArrays() throws IOException {
        ArrayList<Album> tempAlbum = files.readAlbumFile();
        ArrayList<Customer> tempCustomers = files.readCustomerFile();
        albums.addAll(tempAlbum);
        customers.addAll(tempCustomers);
    }

    public String getUsername(){
        return "admin";
    }
    public String getPassword(){
        return "admin";
    }

    public void addCustomer(Customer created){
        customers.add(created);
    }

    public void deleteCustomer(Customer selected){
        customers.remove(selected);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Customer> getCustomers(){
        return (ArrayList<Customer>) customers.clone();
    }

}
