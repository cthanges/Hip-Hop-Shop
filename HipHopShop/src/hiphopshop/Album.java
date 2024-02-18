/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hiphopshop;

/**
 *
 * @author Charran Thangeswaran
 */
import javafx.scene.control.CheckBox;

public class Album {
    private final String title;
    private final double price;
    public CheckBox select;

    public Album(String albumTitle, double albumPrice) {
        this.title = albumTitle;
        this.price = albumPrice;
        select = new CheckBox();
    }

    public String getTitle() {
        return this.title;
    }

    public double getPrice() {
        return this.price;
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }

}
