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
import java.util.Random;
import java.util.ResourceBundle;

import static amandalixey.lixeysoftware1.Inventory.allParts;

/**
 * This is the controller class for the add product screen of the program.
 * FUTURE ENHANCEMENT: When adding a product, I would like to have the option to add a picture of the product that the user can see on the main screen when they select the product.
 */
public class AddProductController  implements Initializable {
    public Part transferPart = null;
    public Product transferProduct;

    public TextField addProductIDText;
    public TextField addProductNameText;
    public TextField addProductInvText;
    public TextField addProductPriceText;
    public TextField addProductMaxText;
    public TextField addProductMinText;
    public TextField partSearch;
    public TableView<Part> addPartToProductTable;
    public TableColumn partIDCol;
    public TableColumn partNameCol;
    public TableColumn partInventoryLevelCol;
    public TableColumn partPriceCol;
    public Button addPartToProductButton;
    public TableView<Part> partRemovalTable;
    public TableColumn partRemovalIDCol;
    public TableColumn partRemovalNameCol;
    public TableColumn partRemovalInventoryLevelCol;
    public TableColumn partRemovalPriceCol;
    public Button removeAssociatedPartButton;
    public Button saveAddProductButton;
    public Button cancelAddProductButton;



    /**
     * This method is called when the part search bar is used.
     * @param actionEvent Triggers the event.
     */
    public void onPartSearch(ActionEvent actionEvent) {


        ///Receive user input
        String query = partSearch.getText();

        if(query.isEmpty()){
            addPartToProductTable.setItems(allParts);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
            alert.setTitle("Error");
            alert.setContentText("Please enter a valid Part ID or Name.");
            alert.showAndWait();
        }else {

            try {
                //Add query results to a list of parts
                ObservableList<Part> parts = Inventory.lookupPart(query);

                if (parts.size() == 0) {
                    int id = Integer.parseInt(query);

                    //Look up id and put into part
                    Part part = Inventory.lookupPart(id);

                    //If a part is found, add it to parts.
                    if (part != null) {
                        parts.add(part);
                    }else { //If no part is found, throw alert
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
                        alert.setTitle("Error!");
                        alert.setContentText("No matching ID found.");
                        alert.showAndWait();
                    }
                }

                //Finally, add the parts list to the parts table.
                addPartToProductTable.setItems(parts);

            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
                alert.setTitle("Error!");
                alert.setContentText("No matching name found.");
                alert.showAndWait();
            }
        }
    }

    /**
     * This method is called when the add associated part button on the add product page is clicked.
     * @param actionEvent Triggers the event.
     */
    public void onAddPartToProductButtonClicked(ActionEvent actionEvent) {
        
        System.out.println("add part to product button clicked");
        transferPart = (Part) addPartToProductTable.getSelectionModel().getSelectedItem();
        transferProduct.addAssociatedPart(transferPart);
        partRemovalTable.setItems(transferProduct.getAllAssociatedParts());
        partRemovalInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partRemovalPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        addPartToProductTable.setItems(allParts);

        /////RANDOM ID GENERATOR
        Random randomId = new Random();

        }

    /**
     * This method is called when the remove associated part button is clicked.
     * @param actionEvent Triggers the event.
     */
    public void onRemoveAssociatedPartButtonClicked(ActionEvent actionEvent) {

        System.out.println("remove associated part button clicked");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Warning!");
        alert.setTitle("You're about to remove a part from the list.");
        alert.setContentText("Remove part?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            transferPart = partRemovalTable.getSelectionModel().getSelectedItem();
            transferProduct.deleteAssociatedPart(transferPart);
            partRemovalTable.setItems(transferProduct.getAllAssociatedParts());
        }
    }

    /**
     * This method is called when the save button on the add product page is clicked.
     * RUNTIME ERROR: Products were saving as doubles. I fixed this by removing repetition of line 179.
     * @param actionEvent Triggers the event.
     */
    public void onSaveAddProductButtonClicked(ActionEvent actionEvent) {

        System.out.println("save button clicked");
            //Get field text and put it into strings
            String prodIdStr = addProductIDText.getText();
            String prodNameStr = addProductNameText.getText();
            String prodStockStr = addProductInvText.getText();
            String prodPriceStr = addProductPriceText.getText();
            String prodMaxStr = addProductMaxText.getText();
            String prodMinStr = addProductMinText.getText();

            //Convert strings to integers
            int id = Integer.parseInt(prodIdStr);
            int stock = Integer.parseInt(prodStockStr);
            double cost = Double.parseDouble(prodPriceStr);
            int max = Integer.parseInt(prodMaxStr);
            int min = Integer.parseInt(prodMinStr);

            //Create the new product
            //Product product = new Product(id,prodNameStr,cost,stock,max,min){};
            transferProduct.setId(id);
            transferProduct.setName(prodNameStr);
            transferProduct.setPrice(cost);
            transferProduct.setStock(stock);
            transferProduct.setMax(max);
            transferProduct.setMin(min);

            //Add the final transferProduct to the allProducts list in Inventory
            Inventory.addProduct(transferProduct);
            cancelAddProductButton.fire();


        }




    /**
     * This method is called when the cancel button is clicked.
     * @param actionEvent
     * @throws IOException
     */

    public void onCancelAddProductButtonClicked(ActionEvent actionEvent) throws IOException {
        System.out.println("cancel button clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/amandalixey/lixeysoftware1/MainScreen.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent, 850, 400);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method initializes the controller class.
     * RUNTIME ERROR: The ID was able to be edited. I fixed this by adding line 233.
     * @param url The URL location of the FXML file.
     * @param resourceBundle The ResourceBundle instance associated with the FXML file.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set up column names for each table
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partRemovalIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partRemovalNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partRemovalInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partRemovalPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        addPartToProductTable.setItems(allParts);

        /////RANDOM ID GENERATOR
        Random randomId = new Random();

        // Generate random ints from 0 to 999
        int idResult = randomId.nextInt(1000);

        //Initialize a new product with default values
        transferProduct = new Product(0,"na",1,1,1,10);


        //Set the productId field to the random result and not be editable
        addProductIDText.setText(String.valueOf(idResult));
        addProductIDText.setEditable(false);


    }

}
