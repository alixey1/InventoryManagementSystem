package amandalixey.lixeysoftware1;

import javafx.collections.FXCollections;
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

import static amandalixey.lixeysoftware1.Inventory.allParts;

/**
 * This is the controller class for the modify product screen of the program.
 * FUTURE ENHANCEMENT: When modifying a product I would like to be able to see previously associated parts and other products that share the same parts.
 */

public class modifyProductController2 implements Initializable {


    ObservableList<Part> finalPartList = FXCollections.observableArrayList();

    public TextField modifyProductIDText;
    public TextField modifyProductNameText;
    public TextField modifyProductInvText;
    public TextField modifyProductPriceText;
    public TextField modifyProductMaxText;
    public TextField modifyProductMinText;
    public TextField partSearch;
    public TableView<Part> modifyAddPartToProductTable;
    public TableColumn partIDCol;
    public TableColumn partNameCol;
    public TableColumn partInventoryLevelCol;
    public TableColumn partPriceCol;
    public Button modifyAddPartToProductButton;
    public TableView<Part> modifyPartRemovalTable;
    public TableColumn partRemovalIDCol;
    public TableColumn partRemovalNameCol;
    public TableColumn partRemovalInventoryLevelCol;
    public TableColumn partRemovalPriceCol;
    public Button removeAssociatedPartButton;
    public Button saveModifyProductButton;
    public Button cancelModifyProductButton;
    public int productIndex = Inventory.allProducts.indexOf(productToModify);
    private static Product productToModify = null;
    public static void passProduct(Product passedProduct) {
        productToModify = passedProduct;
        //System.out.println(passedPart.getName());
    }
    private static Part partToModify = null;
    public static void passPart(Part passedPart) {
        partToModify = passedPart;
    }
    public int modifyId = productToModify.getId();
    public String modifyIdStr = String.valueOf(modifyId); //Converted int to String to display in field


    public String modifyName = productToModify.getName();
    public int modifyStock = productToModify.getStock();
    public String modifyStockStr = String.valueOf(modifyStock); //Converted int to String to display in field

    public double modifyCost = productToModify.getPrice();
    public String modifyCostStr = String.valueOf(modifyCost); //Converted double to String to display in field

    public int modifyMax = productToModify.getMax();
    public String modifyMaxStr = String.valueOf(modifyMax); //Converted int to String to display in field

    public int modifyMin = productToModify.getMin();
    public String modifyMinStr = String.valueOf(modifyMin); //Converted int to String to display in field

    /**
     * This method is called when the part search bar is used.
     * @param actionEvent Triggers the event.
     */
    public void onPartSearch(ActionEvent actionEvent) {
        //Receive user input
        String query = partSearch.getText();


        if (query.isEmpty()) {
            modifyAddPartToProductTable.setItems(allParts);
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
                modifyAddPartToProductTable.setItems(parts);

            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
                alert.setTitle("Uh oh!");
                alert.setContentText("No matching name found.");
                alert.showAndWait();
            }
        }

    }
    /**
     * This method is called when the add button on the modify product page is clicked.
     * RUNTIME ERROR: When adding a part to a product it would not show up in the final part table. I fixed this by adding the productToModify method.
     * @param actionEvent Triggers the event.
     */
    public void onModifyAddPartToProductButtonClicked(ActionEvent actionEvent) {


        System.out.println("add part to product button clicked");


        productToModify.addAssociatedPart((Part)modifyAddPartToProductTable.getSelectionModel().getSelectedItem());
        finalPartList = productToModify.getAllAssociatedParts();
        modifyPartRemovalTable.setItems(finalPartList);
    }

    /**
     * This method is called when the remove associated part button is clicked.
     * RUNTIME ERROR: The button was not removing associated parts. I fixed this by adding the productToModify method.
     * @param actionEvent Triggers the event.
     */
    public void onRemoveAssociatedPartButtonClicked(ActionEvent actionEvent) {
        System.out.println("remove associated part button clicked");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Warning!");
            alert.setTitle("You're about to remove a prt from the list.");
            alert.setContentText("Remove part?");

            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                productToModify.deleteAssociatedPart((Part) modifyAddPartToProductTable.getSelectionModel().getSelectedItem());
                finalPartList = productToModify.getAllAssociatedParts();
                modifyPartRemovalTable.setItems(finalPartList);
            }
        }

    /**
     * This method is called when the save button on the modify product page is clicked.
     * RUNTIME ERROR: Modified products were saving as a new part instead of updating the old one. I fixed this by using update instead of new on line 213.
     * @param actionEvent Triggers the event.
     */
    public void onSaveModifyProductButtonClicked(ActionEvent actionEvent) {


        try {
            //Get Field text and add to strings
            String prodIdStr = modifyProductIDText.getText();
            String prodNameStr = modifyProductNameText.getText();
            String prodStockStr = modifyProductInvText.getText();
            String prodPriceStr = modifyProductPriceText.getText();
            String prodMaxStr = modifyProductMaxText.getText();
            String prodMinStr = modifyProductMinText.getText();

            //Convert strings into integers
            int id = Integer.parseInt(prodIdStr);
            int stock = Integer.parseInt(prodStockStr);
            double cost = Double.parseDouble(prodPriceStr);
            int max = Integer.parseInt(prodMaxStr);
            int min = Integer.parseInt(prodMinStr);

            //Product product = new Product(id,prodNameStr,cost,stock,max,min){};


            productToModify.setId(id);
            productToModify.setName(prodNameStr);
            productToModify.setPrice(cost);
            productToModify.setStock(stock);
            productToModify.setMax(max);
            productToModify.setMin(min);

            try {
                if (productToModify.getMin() > productToModify.getMax()) {
                    throw new ArithmeticException("Min is greater than Max.");
                }
                try {

                    if(productToModify.getStock() < productToModify.getMin() || productToModify.getStock() > productToModify.getMax()){
                        throw new ArithmeticException("Inventory must be between Min and Max");
                    }

                    //Update inventory with the product
                    Inventory.updateProduct(productIndex, productToModify);


                    //Go back to the main screen

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/amandalixey/lixeysoftware1/MainScreen.fxml"));
                    Parent parent = loader.load();
                    Scene scene = new Scene(parent, 850, 400);
                    Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }
                catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Inventory must be between Min and Max.");
                    alert.showAndWait();
                }
            }
            catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Min must be greater than Max.");
                alert.showAndWait();
            }
        }
        catch (NumberFormatException nfe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Incorrect format. Please check your input.");
            alert.showAndWait();
        }




    }




    /**
     * This method is called when the cancel button is clicked.
     * @param actionEvent
     * @throws IOException
     */
    public void onCancelModifyProductButtonClicked(ActionEvent actionEvent) throws IOException {
        System.out.println("Cancel button clicked");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/amandalixey/lixeysoftware1/MainScreen.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent, 850, 400);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    /**
     * This method initializes the controller class.
     * RUNTIME ERROR: The tables were not loading in populated. I fixed this by adding the columns to the initialize method.
     * @param url The URL location of the FXML file.
     * @param resourceBundle The ResourceBundle instance associated with the FXML file.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partRemovalIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partRemovalNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partRemovalInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partRemovalPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyAddPartToProductTable.setItems(allParts);

        modifyPartRemovalTable.setItems(productToModify.getAllAssociatedParts());


        modifyProductIDText.setText(modifyIdStr);
        modifyProductNameText.setText(modifyName);
        modifyProductInvText.setText(modifyStockStr);
        modifyProductPriceText.setText(modifyCostStr);
        modifyProductMaxText.setText(modifyMaxStr);
        modifyProductMinText.setText(modifyMinStr);

        modifyProductIDText.setEditable(false);


        System.out.println(productToModify.getName());
        modifyProductIDText.setText(Integer.toString(productToModify.getId()));
        modifyProductNameText.setText(productToModify.getName());
        modifyProductInvText.setText(Integer.toString(productToModify.getStock()));
        modifyProductPriceText.setText(Integer.toString((int) productToModify.getPrice()));
        modifyProductMaxText.setText(Integer.toString(productToModify.getMax()));
        modifyProductMinText.setText(Integer.toString(productToModify.getMin()));

    }
}
