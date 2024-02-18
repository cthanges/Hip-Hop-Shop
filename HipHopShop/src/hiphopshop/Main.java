/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hiphopshop;

/**
 *
 * @author Charran Thangeswaran
 */
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.IOException;

public class Main extends Application {

    private final Owner owner = new Owner();
    private Customer currentCustomer;
    private static final Files files = new Files();

    Button loginButton = new Button("Login");
    Button albumsButton = new Button("Albums & Mixtapes");
    Button customersButton = new Button("Customers");
    Button logoutButton = new Button("Logout");
    Button backButton = new Button("\uD83E\uDC60");
    Button buyButton = new Button("Buy");
    Button pointsBuyButton = new Button("Redeem points and Buy");
    TextField userTextField = new TextField();
    PasswordField passTextField = new PasswordField();
    HBox hb = new HBox();

    TableView<Album> albumsTable = new TableView<>();
    final TableView.TableViewFocusModel<Album> defaultFocusModel = albumsTable.getFocusModel();
    ObservableList<Album> albums = FXCollections.observableArrayList();

    public ObservableList<Album> addAlbums(){
        albums.addAll(Owner.albums);
        return albums;
    }

    TableView<Customer> customersTable = new TableView<>();
    ObservableList<Customer> customers = FXCollections.observableArrayList();

    public ObservableList<Customer> addCustomers(){
        customers.addAll(owner.getCustomers());
        return customers;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hip-Hop Shop");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(loginScreen(false), 650, 600));
        primaryStage.show();
        System.out.println("Hip-Hop Shop was opened");

        try{
            owner.restockArrays();
            System.out.println("Arrays restocked from files");
        }
        catch (IOException e){
            System.out.println("File Importing Error");
        }

        loginButton.setOnAction(e -> {
            boolean logged_in = false;

            if(userTextField.getText().equals(owner.getUsername()) && passTextField.getText().equals(owner.getUsername())) {
                primaryStage.setScene(new Scene(ownerStartScreen(), 650, 600));
                logged_in = true;
            }
            for(Customer c: owner.getCustomers()) {
                if (userTextField.getText().equals(c.getUsername()) && passTextField.getText().equals(c.getPassword())) {
                    currentCustomer = c;
                    primaryStage.setScene(new Scene(customerHomeScreen(0), 650, 600));
                    logged_in = true;
                }
            }
            if(!logged_in) {
                primaryStage.setScene(new Scene(loginScreen(true),650, 600));
            }
        });

        logoutButton.setOnAction(e -> {
            primaryStage.setScene(new Scene(loginScreen(false), 650, 600));
            for(Album a: Owner.albums){
                a.setSelect(new CheckBox());
            }
            userTextField.clear();
            passTextField.clear();
        });

        albumsButton.setOnAction(e -> primaryStage.setScene(new Scene(albumsTableScreen(), 650, 600)));

        customersButton.setOnAction(e -> primaryStage.setScene(new Scene(customerTableScreen(), 650, 600)));
        backButton.setOnAction(e -> primaryStage.setScene(new Scene(ownerStartScreen(), 650, 600)));

        pointsBuyButton.setOnAction(e -> {
            boolean albumSelected = false;
            for(Album a: Owner.albums) {
                if (a.getSelect().isSelected()) {
                    albumSelected = true;
                }
            }
            if(!albumSelected){
                primaryStage.setScene(new Scene(customerHomeScreen(1),650, 600));
            } else if(currentCustomer.getPoints() == 0){
                primaryStage.setScene(new Scene(customerHomeScreen(2), 650, 600));
            } else if(currentCustomer.getPoints() != 0){
                primaryStage.setScene(new Scene(checkoutScreen(true), 650, 500));
            }
        });

        buyButton.setOnAction(e -> {
            boolean albumSelected = false;
            for(Album a: Owner.albums) {
                if (a.getSelect().isSelected()) {
                    albumSelected = true;
                }
            }
            if(albumSelected){
                primaryStage.setScene(new Scene(checkoutScreen(false),650, 600));
            } else primaryStage.setScene(new Scene(customerHomeScreen(1), 650, 600));
        });

        primaryStage.setOnCloseRequest(e -> {
            System.out.println("Exited the Hip-Hop Shop");
            try {
                files.albumFileReset();
                files.customerFileReset();
                System.out.println("Files reset");
                files.albumFileWrite(Owner.albums);
                files.customerFileWrite(owner.getCustomers());
                System.out.println("Files updated with current array data");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        //css
        {
            buyButton.setStyle("-fx-background-color: #808080;");
            pointsBuyButton.setStyle("-fx-background-color: #808080;");
            customersButton.setStyle("-fx-background-color: #808080;" + "-fx-font-size:25;" + "-fx-background-radius: 10;");
            albumsButton.setStyle("-fx-background-color: #808080;" + "-fx-font-size:25;" + "-fx-background-radius: 10;");
            logoutButton.setStyle("-fx-background-color: #808080; -fx-text-fill: black;");
            backButton.setStyle("-fx-background-color: #808080;" + "-fx-font-size:14;");
            loginButton.setStyle("-fx-background-color: #808080");

            customersTable.setStyle("-fx-control-inner-background: #AD8762;" +
                    "-fx-selection-bar: #AD8762; -fx-selection-bar-non-focused: #AD8762;" + "-fx-border-color: #AD8762;" +
                    "-fx-table-cell-border-color: #AD8762;" + "-fx-background-color: #AD8762;");

            albumsTable.setStyle("-fx-control-inner-background: #AD8762;" + "-fx-border-color: #AD8762;" +
                    "-fx-selection-bar: #AD8762; -fx-selection-bar-non-focused: #AD8762;" +
                    "-fx-table-cell-border-color: #AD8762;" + "-fx-background-color: #AD8762;" + "-fx-column-header-background: #AD8762;");
        }
    }
//LOGIN SCREEN
public Group loginScreen(boolean loginError) {
    Group lis = new Group();
    HBox header = new HBox();
    Image rawLogo = new Image("file:src/vinyl.png");
    ImageView logo = new ImageView(rawLogo);
    logo.setFitHeight(50);
    logo.setFitWidth(50);
    Label brand = new Label("HIP-HOP SHOP");
    brand.setFont(new Font("VERDANA", 40));
    brand.setTextFill(Color.GOLDENROD);
    header.getChildren().addAll(brand, logo);
    header.setSpacing(15);
    header.setAlignment(Pos.CENTER);

    VBox loginBox = new VBox();
    loginBox.setPadding(new Insets(30, 65, 45, 65));
    loginBox.setStyle("-fx-background-color: #AD8762;" +
                      "-fx-background-radius: 10;" +
                      "-fx-background-insets: 10 20 10 20;");
    loginBox.setSpacing(6);

    String borderStyle = "-fx-border-color: gray; -fx-border-width: 1; -fx-border-radius: 5;";
    userTextField.setStyle("-fx-background-color: #FFFFFF; " + borderStyle);
    passTextField.setStyle("-fx-background-color: #FFFFFF; " + borderStyle);
    userTextField.setMinSize(200, 30);
    passTextField.setMinSize(200, 30);

    Text user = new Text("Username:");
    Text pass = new Text("Password:");
    loginButton.setMinWidth(174);

    loginButton.setStyle("-fx-background-color: #808080; " + 
                         "-fx-text-fill: black;");

    loginBox.getChildren().addAll(user, userTextField, pass, passTextField, loginButton);

    if(loginError){
        Text errorMsg = new Text("Invalid username or password.");
        errorMsg.setFill(Color.RED);
        loginBox.getChildren().add(errorMsg);
    }

    VBox bg = new VBox();
    bg.getChildren().addAll(header, loginBox);
    bg.setStyle("-fx-background-color: #27592D;");
    bg.setPadding(new Insets(80, 150, 200, 150));
    bg.setSpacing(80);

    lis.getChildren().addAll(bg);
    return lis;
}

public Group customerHomeScreen(int type) {
    Group bookstore = new Group();
    albumsTable.getItems().clear();
    albumsTable.getColumns().clear();
    albumsTable.setFocusModel(null);

    Font font = new Font(14);
    Text welcomeMsg = new Text("Welcome, " + currentCustomer.getUsername() + ".");
    welcomeMsg.setFont(font);
    Text status1 = new Text(" Status: ");
    status1.setFont(font);
    Text status2 = new Text(currentCustomer.getStatus());
    status2.setFont(font);
    status2.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 14px;" +
            "-fx-stroke: black;" + "-fx-stroke-width: 0.5px;");

    if (currentCustomer.getStatus().equals("GOLD")) {
        status2.setFill(Color.GOLD);
    } else {
        status2.setFill(Color.SILVER);
    }

    Text points = new Text(" Points: " + currentCustomer.getPoints());
    points.setFont(font);

    //Album title column
    TableColumn<Album, String> titleColumn = new TableColumn<>("Title");
    titleColumn.setMinWidth(200);
    titleColumn.setStyle("-fx-alignment: CENTER;");
    titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

    //Album price column
    TableColumn<Album, Double> priceColumn = new TableColumn<>("Price");
    priceColumn.setMinWidth(100);
    priceColumn.setStyle("-fx-alignment: CENTER;");
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    //Checkbox column
    TableColumn<Album, String> selectColumn = new TableColumn<>("Select");
    selectColumn.setMinWidth(100);
    selectColumn.setStyle("-fx-alignment: CENTER;");
    selectColumn.setCellValueFactory(new PropertyValueFactory<>("select"));

    albumsTable.setItems(addAlbums());
    albumsTable.getColumns().addAll(titleColumn, priceColumn, selectColumn);

    HBox info = new HBox();
    info.getChildren().addAll(status1, status2, points);
    BorderPane header = new BorderPane();
    header.setLeft(welcomeMsg);
    header.setRight(info);

    HBox bottom = new HBox();
    bottom.setAlignment(Pos.BOTTOM_CENTER);
    bottom.setSpacing(5);
    buyButton.setStyle("-fx-background-color: #808080; -fx-text-fill: black;");
    pointsBuyButton.setStyle("-fx-background-color: #808080; -fx-text-fill: black;");
    logoutButton.setStyle("-fx-background-color: #808080; -fx-text-fill: black;");
    bottom.getChildren().addAll(buyButton, pointsBuyButton, logoutButton);

    VBox vbox = new VBox();
    String errMsg = "";
    if (type == 1) {
        errMsg = "Please select an album/mixtape before proceeding.";
    } else if (type == 2) {
        errMsg = "No points available to redeem.";
    }
    Text warning = new Text(errMsg);
    warning.setFill(Color.RED);
    vbox.setStyle("-fx-background-color: #27592D;");
    vbox.setSpacing(10);
    vbox.setAlignment(Pos.CENTER);
    vbox.setPadding(new Insets(40, 200, 30, 100));
    vbox.getChildren().addAll(header, albumsTable, bottom, warning);

    bookstore.getChildren().addAll(vbox);

    return bookstore;
}

public Group checkoutScreen(boolean usedPoints){
    Group cos = new Group();
    double total, subtotal = 0, discount;
    int pointsEarned, i = 0, albumCount = 0;
    String[][] albumsBought = new String[25][2];

    for(Album a: Owner.albums){
        if(a.getSelect().isSelected()){
            subtotal += a.getPrice();
            albumsBought[i][0] = a.getTitle();
            albumsBought[i][1] = String.valueOf(a.getPrice());
            i++;
        }
    }

    if(usedPoints){
        if((double)currentCustomer.getPoints()/100 >= subtotal){
            discount = subtotal;
            currentCustomer.setPoints(-(int)subtotal*100);
        }
        else{
            discount = ((double)currentCustomer.getPoints()/100);
            currentCustomer.setPoints(-currentCustomer.getPoints());
        }
    }else discount = 0;

    total = subtotal - discount;
    pointsEarned = (int)total*10;
    currentCustomer.setPoints(pointsEarned);

    HBox header = new HBox();
    header.setAlignment(Pos.CENTER);
    header.setSpacing(15);
    header.setPadding(new Insets(0,0,25,0));
    Label brandName = new Label("HIP-HOP SHOP");
    brandName.setFont(new Font("VERDANA", 40));
    brandName.setTextFill(Color.GOLDENROD);
    Image rawLogo = new Image("file:src/vinyl.png");
    ImageView logo = new ImageView(rawLogo);
    logo.setFitHeight(50);
    logo.setFitWidth(50);
    header.getChildren().addAll(brandName, logo);

    VBox receipt = new VBox();
    receipt.setSpacing(7);
    Text receiptTxt = new Text("Receipt");
    receiptTxt.setFont(Font.font(null, FontWeight.BOLD, 12));
    receiptTxt.setFill(Color.BLACK);
    Line thickLine = new Line(0, 150, 400, 150);
    thickLine.setStrokeWidth(3);
    receipt.getChildren().addAll(receiptTxt, thickLine);

    VBox receiptItems = new VBox();
    receiptItems.setStyle("-fx-background-color: #27592D;");
    receiptItems.setSpacing(7);
    for (i = 0; i<25; i++) {
        if(albumsBought[i][0] != null){
            Text albumTitle = new Text(albumsBought[i][0]);
            Text albumPrice = new Text(albumsBought[i][1]);
            BorderPane item = new BorderPane();
            item.setLeft(albumTitle);
            item.setRight(albumPrice);
            Line thinLine = new Line(0, 150, 400, 150);
            receiptItems.getChildren().addAll(item, thinLine);
            albumCount++;
        }
    }

    ScrollPane scrollReceipt = new ScrollPane(receiptItems);
    scrollReceipt.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollReceipt.setStyle("-fx-background-color: transparent;");
    scrollReceipt.setFitToWidth(true);
    if(albumCount<=4){
        scrollReceipt.setFitToHeight(true);
    } else scrollReceipt.setPrefHeight(130);

    Text subtotalTxt = new Text("Subtotal: $" + (Math.round(subtotal*100.0))/100.0);
    subtotalTxt.setFill(Color.BLACK);
    Text pointsDisc = new Text("Points Discount: $" + (Math.round(discount*100.0))/100.0);
    pointsDisc.setFill(Color.BLACK);
    Text totalTxt = new Text("Total: $" + (Math.round(total*100.0))/100.0);
    totalTxt.setFont(new Font("Arial", 15));
    totalTxt.setFill(Color.BLACK);
    Line thickLine2 = new Line(0, 150, 400, 150);
    thickLine2.setStrokeWidth(3);
    receipt.getChildren().addAll(scrollReceipt, subtotalTxt, pointsDisc, totalTxt, thickLine2);

    VBox bottom = new VBox();
    bottom.setSpacing(40);
    bottom.setAlignment(Pos.CENTER);
    Text info = new Text("You have earned " + pointsEarned + " points " +
            "& your current status is " + currentCustomer.getStatus() + "\n\t\t\tThank you for your purchase!");
    info.setFill(Color.BLACK);
    bottom.getChildren().addAll(info, logoutButton);

    VBox screen = new VBox();
    screen.setStyle("-fx-background-color: #27592D;");
    screen.setPadding(new Insets(60,105,500,100));
    screen.setAlignment(Pos.CENTER);
    screen.setSpacing(10);
    screen.getChildren().addAll(header, receipt, bottom);

    cos.getChildren().addAll(screen);
    Owner.albums.removeIf(b -> b.getSelect().isSelected());
    return cos;
}

public VBox ownerStartScreen() {
    VBox osc = new VBox();
    osc.setStyle("-fx-background-color: #27592D;");
    osc.setAlignment(Pos.CENTER);
    osc.setSpacing(100);
    osc.setPadding(new Insets(80, 0, 30, 0));

    HBox buttons = new HBox();
    buttons.setAlignment(Pos.CENTER);
    buttons.setSpacing(40);
    Line vLine = new Line(150, 0, 150, 200);
    vLine.setStroke(Color.BLACK);
    buttons.getChildren().addAll(albumsButton, vLine, customersButton);

    albumsButton.setPrefSize(200, 150);
    albumsButton.setStyle("-fx-background-color: #808080;" +
                        "-fx-text-fill: black;" +
                        "-fx-font-size: 18px;");

    customersButton.setPrefSize(200, 150);
    customersButton.setStyle("-fx-background-color: #808080;" +
                            "-fx-text-fill: black;" +
                            "-fx-font-size: 18px;");

    osc.getChildren().addAll(buttons, logoutButton);
    return osc;
}

public Group albumsTableScreen() {
    Group bt = new Group();
    hb.getChildren().clear();
    albumsTable.getItems().clear();
    albumsTable.getColumns().clear();
    albumsTable.setFocusModel(defaultFocusModel);

    Label label = new Label("ALBUMS");
    label.setFont(new Font("VERDANA", 30));
    label.setTextFill(Color.GOLDENROD);

    // Album title column
    TableColumn<Album, String> titleColumn = new TableColumn<>("Title");
    titleColumn.setMinWidth(200);
    titleColumn.setStyle("-fx-alignment: CENTER;");
    titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

    // Album price column
    TableColumn<Album, Double> priceColumn = new TableColumn<>("Price");
    priceColumn.setMinWidth(100);
    priceColumn.setStyle("-fx-alignment: CENTER;");
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    albumsTable.setItems(addAlbums());
    albumsTable.getColumns().addAll(titleColumn, priceColumn);

    final TextField addAlbumTitle = new TextField();
    addAlbumTitle.setPromptText("Title");
    addAlbumTitle.setMaxWidth(titleColumn.getPrefWidth());
    final TextField addAlbumPrice = new TextField();
    addAlbumPrice.setPromptText("Price");
    addAlbumPrice.setMaxWidth(priceColumn.getPrefWidth());
    addAlbumTitle.setStyle("-fx-background-color: #AD8762;"); 
    addAlbumPrice.setStyle("-fx-background-color: #AD8762;");

    VBox core = new VBox();
    final Button addButton = new Button("Add");
    addButton.setStyle("-fx-background-color: #808080;"); 
    addButton.setTextFill(Color.BLACK); 
    Label albumAddErr = new Label("Invalid Input");
    albumAddErr.setTextFill(Color.RED);

    addButton.setOnAction(e -> {
        try {
            double price = Math.round((Double.parseDouble(addAlbumPrice.getText()))*100);
            Owner.albums.add(new Album(addAlbumTitle.getText(), price/100));
            albumsTable.getItems().clear();
            albumsTable.setItems(addAlbums());
            addAlbumTitle.clear();
            addAlbumPrice.clear();
            core.getChildren().remove(albumAddErr);
        } catch (Exception exception) {
            if (!core.getChildren().contains(albumAddErr)) {
                core.getChildren().add(albumAddErr);
            }
        }
    });

    final Button deleteButton = new Button("Delete");
    deleteButton.setStyle("-fx-background-color: #808080;");
    deleteButton.setTextFill(Color.BLACK);
    deleteButton.setOnAction(e -> {
        Album selectedItem = albumsTable.getSelectionModel().getSelectedItem();
        albumsTable.getItems().remove(selectedItem); 
        Owner.albums.remove(selectedItem); 
    });

    hb.getChildren().addAll(addAlbumTitle, addAlbumPrice, addButton, deleteButton);
    hb.setSpacing(3);
    hb.setAlignment(Pos.CENTER);

    HBox back = new HBox();
    back.setPadding(new Insets(5));
    back.getChildren().addAll(backButton);

    core.setAlignment(Pos.CENTER);
    core.setSpacing(5);
    core.setPadding(new Insets(0, 0, 0, 150));
    core.getChildren().addAll(label, albumsTable, hb);

    VBox vbox = new VBox();
    vbox.setStyle("-fx-background-color: #27592D;");
    vbox.setPadding(new Insets(0, 200, 60, 0));
    vbox.setAlignment(Pos.CENTER);
    vbox.getChildren().addAll(back, core);

    bt.getChildren().addAll(vbox);

    return bt;
}

public Group customerTableScreen() {
    Group ct = new Group();
    hb.getChildren().clear();
    customersTable.getItems().clear();
    customersTable.getColumns().clear();

    Label label = new Label("CUSTOMERS");
    label.setFont(new Font("VERDANA", 30));
    label.setTextFill(Color.GOLDENROD); 

    //Customer username column
    TableColumn<Customer, String> usernameCol = new TableColumn<>("Username");
    usernameCol.setMinWidth(140);
    usernameCol.setStyle("-fx-alignment: CENTER;");
    usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

    //Customer password column
    TableColumn<Customer, String> passwordCol = new TableColumn<>("Password");
    passwordCol.setMinWidth(140);
    passwordCol.setStyle("-fx-alignment: CENTER;");
    passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));

    //Customer points column
    TableColumn<Customer, Integer> pointsCol = new TableColumn<>("Points");
    pointsCol.setMinWidth(100);
    pointsCol.setStyle("-fx-alignment: CENTER;");
    pointsCol.setCellValueFactory(new PropertyValueFactory<>("points"));

    customersTable.setItems(addCustomers());
    customersTable.getColumns().addAll(usernameCol, passwordCol, pointsCol);

    final TextField addUsername = new TextField();
    addUsername.setPromptText("Username");
    addUsername.setMaxWidth(usernameCol.getPrefWidth());
    final TextField addPassword = new TextField();
    addPassword.setMaxWidth(passwordCol.getPrefWidth());
    addPassword.setPromptText("Password");
    addPassword.setStyle("-fx-background-color: #AD8762;");
    addUsername.setStyle("-fx-background-color: #AD8762;");

    VBox core = new VBox();
    Text customerAddErr = new Text("Customer already exists!");
    customerAddErr.setFill(Color.RED);

    final Button addButton = new Button("Add");
    addButton.setStyle("-fx-background-color: #808080;");
    addButton.setTextFill(Color.BLACK);

    addButton.setOnAction(e -> {
        boolean duplicate = false;

        for(Customer c: owner.getCustomers()){
            if((c.getUsername().equals(addUsername.getText()) && c.getPassword().equals(addPassword.getText())) ||
                    (addUsername.getText().equals(owner.getUsername()) && addPassword.getText().equals(owner.getPassword()))){
                duplicate = true;
                if(!core.getChildren().contains(customerAddErr)){
                    core.getChildren().add(customerAddErr);
                }
            }
        }

        if(!(addUsername.getText().equals("") || addPassword.getText().equals("")) && !duplicate) {
            owner.addCustomer(new Customer(addUsername.getText(), addPassword.getText()));
            customersTable.getItems().clear();
            customersTable.setItems(addCustomers());
            core.getChildren().remove(customerAddErr); 
            addPassword.clear();
            addUsername.clear();
        }
    });

    final Button deleteButton = new Button("Delete");
    deleteButton.setStyle("-fx-background-color: #808080;");
    deleteButton.setTextFill(Color.BLACK); 

    deleteButton.setOnAction(e -> {
        Customer selectedItem = customersTable.getSelectionModel().getSelectedItem();
        customersTable.getItems().remove(selectedItem); 
        owner.deleteCustomer(selectedItem);
    });

    hb.getChildren().addAll(addUsername, addPassword, addButton, deleteButton);
    hb.setAlignment(Pos.CENTER);
    hb.setSpacing(3);

    HBox back = new HBox();
    back.setPadding(new Insets(5));
    back.getChildren().addAll(backButton);
    

    core.setAlignment(Pos.CENTER);
    core.setSpacing(5);
    core.setPadding(new Insets(0,0,0,110));
    core.getChildren().addAll(label, customersTable, hb);

    VBox vbox = new VBox();
    vbox.setStyle("-fx-background-color: #27592D;");
    vbox.setPadding(new Insets(0, 150, 60, 0));
    vbox.getChildren().addAll(back, core);
    vbox.setAlignment(Pos.CENTER);

    ct.getChildren().addAll(vbox);
    return ct;
}//complete

    public static void main(String[] args) {
        launch(args);
    }
}
