package com.example.ecomm;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class ECommerce extends Application {
    ProductList productList = new ProductList();
    Pane root;
    Pane bodyPane;
    Pane allOrders;
    Button back=new Button("Back");
    Button signInButton = new Button("Sign In");
    Button signOutButton = new Button("Sign Out");
    Button buyNowButton = new Button("Buy Now");
    Button removedButton = new Button("Remove");
    Button closeButton = new Button("Exit");
    Button ordersButton = new Button("Your Orders");
    Label welcomeLabel = new Label("");
    Customer loggedInCustomer=null;
    private final int width = 500;
    private final int height = 500;
    private final int headerLine = 50;
    private GridPane headerBar(Button button) {
        GridPane header = new GridPane();
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search an item");
        Button searchButton = new Button("Search");

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // all products to show for now later will change
                bodyPane.getChildren().clear();
                // only searched products to show
                bodyPane.getChildren().add(productList.getAllSearchedProducts(searchBar.getText()));
                root.getChildren().clear();
                if (loggedInCustomer == null)
                    root.getChildren().addAll(headerBar(signInButton), bodyPane, footerBar());
                else root.getChildren().addAll(headerBar(signOutButton), bodyPane, footerBar());
            }
        });

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) { // sign in button will disaapper
                bodyPane.getChildren().clear();
                bodyPane.getChildren().addAll(loginPage());
                root.getChildren().clear();
                root.getChildren().addAll(headerBar(closeButton), bodyPane);
            }
        });

        signOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) { // sign out button will disappear
                loggedInCustomer = null;
                welcomeLabel.setText("");
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginPage());
                root.getChildren().clear();
                root.getChildren().addAll(headerBar(closeButton), bodyPane);
            }
        });

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleCloseButtonAction();
            }
        });
        header.setHgap(10);
        header.add(searchBar, 0, 0);
        header.add(searchButton, 1, 0);
        header.add(button, 2, 0);
        header.add(welcomeLabel, 3, 0);

//        header.add(signInButton, 2, 0);
//        header.add(signOutButton, 3, 0);
//        header.add(welcomeLabel, 0, 1);

        return header;
    }
    private GridPane SignupPage() {
        Label userLabel = new Label("Name : ");
        Label mobileLabel = new Label("Mobile : ");
        Label emailLabel = new Label("E-mail : ");
        Label passLabel = new Label("Password : ");
        Label addressLabel = new Label("Address : ");

        TextField userName = new TextField();
        TextField mobileNumber = new TextField();
        TextField emailName = new TextField();
        PasswordField userPassword = new PasswordField();
        TextField userAddress = new TextField();

        userName.setPromptText("Enter Your User Name");
        mobileNumber.setPromptText("Enter Mobile Number");
        emailName.setPromptText("Enter Your E-mail here");
        userPassword.setPromptText("Enter Your Password");
        userAddress.setPromptText("Fill Your Address");

        Button signupButton = new Button("Sign Up");
        Label messageLabel = new Label("");

        signupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String username = userName.getText();
                String usermobile = mobileNumber.getText();
                String useremail = emailName.getText();
                String userpass = userPassword.getText();
                String useraddress = userAddress.getText();
                try {
                    loggedInCustomer = Login.customerLogin(useremail, userpass);
                    if (loggedInCustomer == null) {
                        // add headerBar, show prod list, footer
                        // update new user in database
                        Signup.customerSignup(username, usermobile, useremail, userpass, useraddress);
                        loggedInCustomer = Login.customerLogin(useremail, userpass);
                        messageLabel.setText("Sign Up Successful!!!");
                        welcomeLabel.setText("Welcome " + username);
                        bodyPane.getChildren().clear();
                        bodyPane.getChildren().add(productList.getAllProducts());
                        root.getChildren().clear();
                        root.getChildren().addAll(headerBar(signOutButton), welcomeLabel, bodyPane, footerBar());
                    }
                    else {

                        showDialogue("You are already registered, please Sign In!!!!");
                        loginPage();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        GridPane singupPane = new GridPane();
        singupPane.setTranslateX(100);
        singupPane.setTranslateY(100);
        singupPane.setVgap(10);
        singupPane.setHgap(10);
        singupPane.setScaleY(1.5);
        singupPane.setScaleX(1.5);

        singupPane.add(userLabel, 0, 0);
        singupPane.add(userName, 1, 0);
        singupPane.add(mobileLabel, 0, 1);
        singupPane.add(mobileNumber, 1, 1);
        singupPane.add(emailLabel, 0, 2);
        singupPane.add(emailName, 1, 2);
        singupPane.add(passLabel, 0, 3);
        singupPane.add(userPassword, 1, 3);
        singupPane.add(addressLabel, 0, 4);
        singupPane.add(userAddress, 1, 4);

        singupPane.add(signupButton, 0, 5);
        singupPane.add(messageLabel, 1, 2);

        return singupPane;
    }

    private GridPane loginPage() {
        Label userLabel = new Label("E-mail : ");
        Label passLabel = new Label("Password : ");
        TextField userName = new TextField();
        userName.setPromptText("Enter Your E-mail");
        PasswordField userPassword = new PasswordField();
        userPassword.setPromptText("Enter Your Password");
        Button loginButton = new Button("Login");
        Label messageLabel = new Label("");

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String user = userName.getText();
                String pass = userPassword.getText();
                try {
                    loggedInCustomer = Login.customerLogin(user, pass);
                    if (loggedInCustomer != null) {
                        messageLabel.setText("Login Successful!!");
                        welcomeLabel.setText("Welcome " + loggedInCustomer.getName());
                        bodyPane.getChildren().clear();
                        bodyPane.getChildren().addAll(productList.getAllProducts());
                        root.getChildren().clear();
                        root.getChildren().addAll(headerBar(signOutButton), bodyPane, footerBar());
                    }
                    else {
                        messageLabel.setText("Login Failed!!");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        GridPane loginPane = new GridPane();
        loginPane.setTranslateX(100);
        loginPane.setTranslateY(100);
        loginPane.setVgap(10);
        loginPane.setHgap(10);
        loginPane.setScaleY(1.5);
        loginPane.setScaleX(1.5);
        loginPane.add(userLabel, 0, 0);
        loginPane.add(userName, 1, 0);
        loginPane.add(passLabel, 0, 1);
        loginPane.add(userPassword, 1, 1);
        loginPane.add(loginButton, 0, 2);
        loginPane.add(messageLabel, 1, 2);

        return loginPane;
    }

    public void showDialogue(String message) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Order Status");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(message);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }
    boolean includeremove=false;
    private GridPane footerBar() {

        GridPane footer = new GridPane();
        footer.setTranslateY(headerLine + height);
        footer.add(buyNowButton, 0, 0);
        footer.add(ordersButton, 2, 0);
        System.out.println(includeremove);
        if(includeremove){
            footer.add(removedButton,0,0);
            footer.setHgap(2);
            footer.add(back,3,0);
            buyNowButton.setVisible(false);
            includeremove=false;
        }
        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                boolean orderStatus = false;
                try {
                    if (product != null && loggedInCustomer != null) {
                        orderStatus = Order.placeOrder(loggedInCustomer, product);
                        //Order.myorder(loggedInCustomer, product);//1
                    }
                    if (orderStatus==true) {

                        showDialogue("Order Successful!!");

                    }
                    else {
                        showDialogue("Order can't be placed, please Sign In!!");
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                buyNowButton.setVisible(true);
                bodyPane.getChildren().clear();
                bodyPane.getChildren().addAll(productList.getAllProducts());
                root.getChildren().clear();
                root.getChildren().addAll(headerBar(signOutButton), bodyPane, footerBar());
            }
        });
        ordersButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                includeremove=true;
                productList.getorderedlist(loggedInCustomer);
                bodyPane.getChildren().clear();
                bodyPane.getChildren().addAll( productList.getorderedlist(loggedInCustomer));
                root.getChildren().clear();

                root.getChildren().addAll(headerBar(signOutButton), bodyPane, footerBar());

            }
        });
        removedButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Product product = productList.getSelectedProduct();
                    boolean orderStatus = false;
                    orderStatus = Order.removeorder(loggedInCustomer, product);
                    ProductList.removerow();
                    if (orderStatus==true) {
                        showDialogue("Order Deleted!!");
                    }
                    else {
                        showDialogue("Order can't be deleted");
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return footer;
    }
    private Pane createContent() {
        root = new Pane();
        root.setPrefSize(width,height + 2 * headerLine);

        bodyPane = new Pane();
        bodyPane.setPrefSize(width, height);
        bodyPane.setTranslateY(headerLine);
        bodyPane.setTranslateX(10);
        bodyPane.getChildren().add(SignupPage());
        root.getChildren().addAll(headerBar(signInButton), bodyPane);

        return root;
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("E-Commerce");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleCloseButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    public static void main(String[] args) {
        launch();
    }
}
