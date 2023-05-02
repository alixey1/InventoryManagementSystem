package amandalixey.lixeysoftware1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * author: Amanda Lixey
 * email: amandalixey@gmail.com
 * Calls the start method
 * Javadoc folder location: file:///C:/Users/adaml/IdeaProjects/LixeySoftware1/javadoc/amandalixey.lixeysoftware1/module-summary.html
 * FUTURE ENHANCEMENT: In the future, I would like the program to load in with previous data still saved within the program.
 */

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/amandalixey/lixeysoftware1/MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 829, 406);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        InHouse p1= new InHouse(1,"brakes", 14.99, 15, 1,20, 222);
        Outsourced p2= new Outsourced(2,"seat", 16.99, 15, 1,20, "Seat Inc");
        Inventory.addPart(p1);
        Inventory.addPart(p2);
        Product b1= new Product(1, "Humvee", 4999.99, 15, 1, 20);
        Product b2= new Product(2, "Camaro", 40000.99, 5, 1, 10);

        Inventory.addProduct(b1);
        Inventory.addProduct(b2);
        launch();
    }

}