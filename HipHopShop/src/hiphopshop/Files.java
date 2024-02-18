/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hiphopshop;

/**
 *
 * @author Charran Thangeswaran
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Files {

    public void albumFileWrite(ArrayList<Album> albums) throws IOException{
        FileWriter albumFile = new FileWriter("album.txt", true); //written at end of file rather than beginning
        for(Album a: albums){
            String albumInfo = a.getTitle() + ", " + a.getPrice() + "\n";
            albumFile.write(albumInfo);
        }
        albumFile.close();
    }

    public void customerFileWrite(ArrayList<Customer> customers) throws IOException{
        FileWriter customerFile = new FileWriter("customer.txt", true);
        for(Customer c: customers){
            String outputText = c.getUsername() + ", " + c.getPassword() + ", " + c.getPoints() + "\n";
            customerFile.write(outputText);
        }
        customerFile.close();
    }

    public void albumFileReset() throws IOException {
        FileWriter albumFile = new FileWriter("album.txt", false);
        albumFile.close();
    }

    public void customerFileReset() throws IOException {
        FileWriter customerFile = new FileWriter("customer.txt", false);
        customerFile.close();
    }

    public ArrayList<Album> readAlbumFile() throws IOException{
        Scanner scan = new Scanner(new FileReader("album.txt"));
        ArrayList<Album> tempAlbumHolder = new ArrayList<>();

        while(scan.hasNext()) {
            String[] albumInfo = scan.nextLine().split(",");
            String title = albumInfo[0];
            double price = Double.parseDouble(albumInfo[1]);
            tempAlbumHolder.add(new Album(title, price));
        }
        return tempAlbumHolder;
    }

    public ArrayList<Customer> readCustomerFile() throws IOException{
        Scanner scan = new Scanner(new FileReader("customer.txt"));
        ArrayList<Customer> tempCustomerHolder = new ArrayList<>();

        while(scan.hasNext()) {
            String[] customerInfo = scan.nextLine().split(", ");
            String username = customerInfo[0];
            String password = customerInfo[1];
            int points = Integer.parseInt(customerInfo[2]);
            tempCustomerHolder.add(new Customer(username, password));
            tempCustomerHolder.get(tempCustomerHolder.size()-1).setPoints(points);
        }
        return tempCustomerHolder;
    }

}
