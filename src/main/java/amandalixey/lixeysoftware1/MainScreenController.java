package amandalixey.lixeysoftware1;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static amandalixey.lixeysoftware1.Inventory.*;

/**
 * The main screen that allows users to access the other features of the program.
 * FUTURE ENHANCEMENT: I would like to make the tables return to being populated after a user has finished searching for something rather than only displaying the last search.
 */
public class MainScreenController implements Initializable {


    public Label TheMainLabel;
    public TableColumn partsIDCol;
    public TableColumn partNameCol;
    public TableColumn partsInventoryLevelCol;
    public TableColumn partsPriceCol;
    public TableView productsTable;
    public TableColumn productIDCol;
    public TableColumn productNameCol;
    public TableColumn productsInventoryLevelCol;
    public TableColumn productsPriceCol;
    public Button addPartsButton;
    public Button modifyPartsButton;
    public Button deletePartsButton;
    public Button addProductButton;
    public Button modifyProductButton;
    public Button deleteProductButton;
    public TextField partSearch;
    public TextField productSearch;
    public TableView<Part> partsTable;

    /**
     * This method initializes the controller class.
     * RUNTIME ERROR: The tables were not loading in populated. I fixed this by adding the columns to the initialize method.
     * @param url The URL location of the FXML file.
     * @param resourceBundle The ResourceBundle instance associated with the FXML file.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("I am initialized");
        partsTable.setItems(allParts);
        productsTable.setItems(allProducts);
        partsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    /**
     * This method is called when the exit button is clicked.
     * @param actionEvent Triggers the event.
     */
    public void OnExitButtonClicked(ActionEvent actionEvent) {
        System.out.println("Exit button clicked");
        System.exit(0);

    }



    /**
     * This method is called when the add product button is clicked.
     * @param actionEvent
     * @throws IOException
     */
    public void onAddProductButtonClicked(ActionEvent actionEvent) throws IOException {
        System.out.println("add product button clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/amandalixey/lixeysoftware1/AddProduct.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent, 680, 532);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /**
     * This method is called when the product search bar is used.
     * @param actionEvent Triggers the event.
     */
    public void onProductSearch(ActionEvent actionEvent) {


        //Receive user input
        String query = productSearch.getText();

        if (query.isEmpty()) {
            partsTable.setItems(allParts);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
            alert.setTitle("Error!");
            alert.setContentText("Please enter a valid Part ID or Name. Field cannot be blank.");
            alert.showAndWait();
        } else {

            try {
                //Add query results to a list of products
                ObservableList<Product> products = Inventory.lookupProduct(query);

                //Instead, if an Id is used and the products list is > 0, search by id and add to products list.
                if (products.size() == 0) {
                    int id = Integer.parseInt(query);
                    Product product = Inventory.lookupProduct(id);
                    if (product != null) {
                        products.add(product);
                    } else { //If no part is found, throw alert
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
                        alert.setTitle("Error!");
                        alert.setContentText("No matching ID found.");
                        alert.showAndWait();
                    }
                }

                //Finally, add the parts list to the products table.
                productsTable.setItems(products);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
                alert.setTitle("Error");
                alert.setContentText("No matching name found.");
                alert.showAndWait();
            }

        }
    }
    /**
     * This method is called when the product search bar is used.
     * @param actionEvent Triggers the event.
     */
    public void onPartSearch(ActionEvent actionEvent) {


        //Receive user input
        String query = partSearch.getText();


        if (query.isEmpty()) {
            partsTable.setItems(allParts);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
            alert.setTitle("Error!");
            alert.setContentText("Please enter a valid Part ID or Name. Field cannot be blank.");
            alert.showAndWait();
        } else {

            try {
                //Add query results to a list of parts
                ObservableList<Part> parts = Inventory.lookupPart(query);

                //Instead, if an Id is used and the parts list is = 0, search by id and add to parts list.
                if (parts.size() == 0) {
                    //Take the query and parse into an id.
                    int id = Integer.parseInt(query);

                    //Look up id and put into part
                    Part part = Inventory.lookupPart(id);

                    //If a part is found, add it to parts.
                    if (part != null) {
                        parts.add(part);
                    } else { //If no part is found, throw alert
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
                        alert.setTitle("Error!");
                        alert.setContentText("No matching ID found.");
                        alert.showAndWait();
                    }
                }

                //Finally, add the parts list to the parts table.
                partsTable.setItems(parts);

            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
                alert.setTitle("Error!");
                alert.setContentText("No matching name found.");
                alert.showAndWait();
            }
        }

    }


    /**
     * This method is called when the add part button is clicked.
     * @param actionEvent
     * @throws IOException
     */
    public void onAddPartsButtonClicked(ActionEvent actionEvent) throws IOException {
        System.out.println("add part button clicked");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/amandalixey/lixeysoftware1/AddPart.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent, 600, 400);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }




    /**
     * This method is called when the modify parts button is clicked.
     * @param actionEvent
     * @throws IOException
     */
    public void onModifyPartsButtonClicked(ActionEvent actionEvent) throws IOException {
        System.out.println("modify part button clicked");
        Part p= partsTable.getSelectionModel().getSelectedItem();
        ModifyPartController.passPart(p);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/amandalixey/lixeysoftware1/ModifyPart.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent, 600, 400);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    /**
     * This method is called when the delete parts button is clicked.
     * @param actionEvent Triggers the event.
     */
    public void onDeletePartsButtonClicked(ActionEvent actionEvent) {

        System.out.println("delete part button clicked");
        Alert partAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part? This cannot be undone.");
        Optional<ButtonType> result = partAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part part = (Part) partsTable.getSelectionModel().getSelectedItem();
            if (part != null) {
                deletePart((Part) partsTable.getSelectionModel().getSelectedItem());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Something went wrong");
                alert.setTitle("Error!");
                alert.setContentText("Please select something to delete.");
                alert.showAndWait();
            }

        }
    }

    /**
     * This method is called when the delete product button is clicked.
     * @param actionEvent Triggers the event.
     */
    public void onDeleteProductsButtonClicked(ActionEvent actionEvent) {

        System.out.println("delete product button clicked");

        try{
            Product testProduct;

            testProduct = (Product) productsTable.getSelectionModel().getSelectedItem();
            if(!testProduct.getAllAssociatedParts().isEmpty()){

                throw new Exception("");
            }
            else{
                Alert productAlert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this product?");
                Optional<ButtonType> result = productAlert.showAndWait();


                if(result.isPresent() && result.get() == ButtonType.OK) {
                    Product product = (Product) productsTable.getSelectionModel().getSelectedItem();

                    if (product != null) {
                        deleteProduct((Product) productsTable.getSelectionModel().getSelectedItem());
                    }else{
                        Alert alert = new Alert(Alert.AlertType.WARNING,"Something went wrong");
                        alert.setTitle("Error!");
                        alert.setContentText("Please select something to delete.");
                        alert.showAndWait();
                    }
                }

            }

        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Something went wrong");
            alert.setTitle("Error!");
            alert.setContentText("Cannot delete a Product with associated Parts.");
            alert.showAndWait();
        }

    }




    /**
     * This method is called when the modify product button is clicked.
     * RUNTIME ERROR: The button was not taking you to the ModifyProduct fxml file. I fixed this by creating a new class and copying my code to it and redirecting the path.
     * @param actionEvent
     * @throws IOException
     */
    public void onModifyProductButtonClicked(ActionEvent actionEvent) throws IOException {
        Product b= (Product) productsTable.getSelectionModel().getSelectedItem();
        modifyProductController2.passProduct(b);

        System.out.println("modify product button clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/amandalixey/lixeysoftware1/ModifyProduct.fxml"));
        Parent parent = loader. load();
        Scene scene = new Scene(parent, 680, 532);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}