package com.example.supplychain;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class SupplyChain extends Application {

    //some variable or micros
    public static final int HEIGHT = 600;
    public static final int WIDTH = 700;
    private static final int UPPER_LINE = 50;


    //GUI for the application
    //body pane
    Pane bodyPane = new Pane();
    ProductDetails productDetails;
    Label loginLabel;
    boolean loggedin = false;

    Button orderButton;
    private GridPane hearBar(){
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: yellow;");
        gridPane.setPrefSize(WIDTH,UPPER_LINE-5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        TextField searchText = new TextField();
        searchText.setMinWidth(250);
        searchText.setPromptText("Please search here");

        loginLabel = new Label("Please Login!");
        Button loginButton = new Button("Login");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!loggedin){
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(loginPage());
//                    loginLabel.setText();
                    loginButton.setText("Logout");
                }else{
                    loggedin = false;
                    loginButton.setText("Login");
                    loginLabel.setText("Please Log In");
                }

            }
        });

        Button searchButton = new Button("Search");

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                productDetails = new ProductDetails();
                String search = searchText.getText();
                bodyPane.getChildren().add(productDetails.getProductsByName(search));
            }
        });
        gridPane.add(searchText,0,0);
        gridPane.add(searchButton,1,0);
        gridPane.add(loginLabel,2,0);
        gridPane.add(loginButton,3,0);

        return gridPane;
    }
    private GridPane loginPage(){
        Label emailLabel = new Label("Email");
        Label passwordLabel = new Label("Password");
        Label messageLabel = new Label("I am message");

        TextField emailTextField = new TextField();
        emailTextField.setPromptText("Please enter Email Id");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Please enter Password");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!loggedin){
                        String email = emailTextField.getText();
                        String password = passwordField.getText();
                        if(login.customerLogin(email,password)){
                            loginLabel.setText("Welcome :: "+email);
                            loggedin = true;
                            bodyPane.getChildren().clear();
                            productDetails = new ProductDetails();
                            bodyPane.getChildren().add(productDetails.getAllProducts());
                            messageLabel.setText("Login Success!");
                        }else{
                            messageLabel.setText("Invalid User");
                        }
        //                messageLabel.setText("email : "+email +"\npass: "+password);
                }
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(),bodyPane.getMinHeight());
        gridPane.setStyle("-fx-background-color: #C0C9A0;");
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.add(emailLabel,0,0);
        gridPane.add(emailTextField,1,0);
        gridPane.add(passwordLabel,0,1);
        gridPane.add(passwordField,1,1);
        gridPane.add(loginButton,0,3);
        gridPane.add(messageLabel,1,3);


        return gridPane;
    }

    private GridPane footerBar(){
        GridPane gridPane = new GridPane();
        orderButton = new Button("Buy Now");

        orderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                productDetails.getSelectedRowValue();
                if(!loggedin){
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(loginPage());
                }else{
                    Product product = productDetails.getSelectedRowValue();
                    if(product !=  null){
                        String email = loginLabel.getText();
                        email = email.substring(10);
                        System.out.println(email);
                        if(Order.placeSingeOrder(product,email)){
                            System.out.println("Order Placed");
                        }else{
                            System.out.println("Order Failed");
                        }
                    }else{
                        System.out.println("Please select a product");
                    }
                }
            }
        });

        gridPane.add(orderButton,0,0);
        gridPane.setMinWidth(WIDTH);
        gridPane.setTranslateY(HEIGHT+80);
        return gridPane;
    }
    Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(WIDTH,HEIGHT+UPPER_LINE+80);


        bodyPane.setTranslateY(UPPER_LINE);
        bodyPane.setMinSize(WIDTH,HEIGHT);
        bodyPane.setStyle("-fx-background-color: #C0C9C0;");
        productDetails = new ProductDetails();
        bodyPane.getChildren().add(productDetails.getAllProducts());

        root.getChildren().addAll(
                hearBar(),
                bodyPane,
                footerBar()
        );
        return root;
    }

    public Login login = new Login();
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("Supply Chain System !");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}