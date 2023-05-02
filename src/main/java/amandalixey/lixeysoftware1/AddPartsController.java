package amandalixey.lixeysoftware1;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the controller class for the add part screen of the program.
 * FUTURE ENHANCEMENT: When adding a part, I would like to be able to add a picture of the part that will be displayed when selecting the part on the main screen.
 */
public class AddPartsController implements Initializable {


    public TextField addPartIDText;
    public Label partMachineIDLabel;
    public TextField addPartNameText;
    public TextField addPartInvText;
    public TextField addPartsPriceText;
    public TextField addPartsMaxText;
    public TextField addPartsMachineIDText;
    public Label addPartMinLabel;
    public TextField addPartsMinText;
    public Button addSavePartButton;
    public Button addCancelPartButton;
    public RadioButton addOutsourcedRadio;
    public RadioButton addInHouseRadio;
    private Part part;
    private static int idCount = 0;



    /**
     * This method is called when the in house radio button is clicked.
     * @param actionEvent Triggers the event.
     */
    public void onAddInHouseRadioClicked(ActionEvent actionEvent) {
        System.out.println("In-house radio button clicked");
        partMachineIDLabel.setText("Machine ID");

    }

    /**
     * This method is called when the outsourced radio button is clicked.
     * @param actionEvent Triggers the event.
     */
    public void onAddOutsourcedRadioClicked(ActionEvent actionEvent) {

        System.out.println("Outsourced radio button clicked");
        partMachineIDLabel.setText("Company Name");
    }




    /**
     * This method is called when the save part button is clicked.
     * RUNTIME ERROR: The error message stating that inventory must be between min and max was showing up no matter what. I fixed this by changing the placement.
     * @param actionEvent
     * @throws IOException
     */
    public void onAddSavePartButtonClicked(ActionEvent actionEvent) throws IOException {
        System.out.println("Save button clicked in Add Part Screen");

        try {
            if (addInHouseRadio.isSelected()) {
                //Receive String input from associated text fields
                String idStr = addPartIDText.getText();
                String nameStr = addPartNameText.getText();
                String stockStr = addPartInvText.getText();
                String costStr = addPartsPriceText.getText();
                String maxStr = addPartsMaxText.getText();
                String minStr = addPartInvText.getText();
                String machineIdStr = addPartsMachineIDText.getText();

                //Convert strings to integers
                int id = Integer.parseInt(idStr);
                int stock = Integer.parseInt(stockStr);
                double cost = Double.parseDouble(costStr);
                int max = Integer.parseInt(maxStr);
                int min = Integer.parseInt(minStr);

                //CREATE AN IN HOUSE PART
                //Parse machineIdStr to an Int
                int machineId = Integer.parseInt(machineIdStr);

                //Create a new inHouse Part
                InHouse inHousePart = new InHouse(id, nameStr, cost, stock, max, min, machineId) {
                };

                //Transfer variables to new inHousePart
                inHousePart.setId(id);
                inHousePart.setName(nameStr);
                inHousePart.setPrice(cost);
                inHousePart.setStock(stock);
                inHousePart.setMax(max);
                inHousePart.setMin(min);
                inHousePart.setMachineId(machineId);
                try {
                    if (inHousePart.getMin() > inHousePart.getMax()) {
                        throw new ArithmeticException("Min is greater than Max.");
                    }
                    try {
                        if (inHousePart.getStock() < inHousePart.getMin() || inHousePart.getStock() > inHousePart.getMax()) {
                            throw new ArithmeticException("Inventory must be between Min and Max");
                        }

                        //Add newly created part to the allParts list in Inventory
                        Inventory.addPart(inHousePart);



                    } catch (ArithmeticException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Inventory must be between Min and Max.");
                        alert.showAndWait();
                    }

                } catch (ArithmeticException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Max must be greater than Min.");
                    alert.showAndWait();
                }
            } else {
                String idStr = addPartIDText.getText();
                String nameStr = addPartNameText.getText();
                String stockStr = addPartInvText.getText();
                String costStr = addPartsPriceText.getText();
                String maxStr = addPartsMaxText.getText();
                String minStr = addPartsMinText.getText();
                String machineIdStr = addPartsMachineIDText.getText();

                int id = Integer.parseInt(idStr);
                int stock = Integer.parseInt(stockStr);
                double cost = Double.parseDouble(costStr);
                int max = Integer.parseInt(maxStr);
                int min = Integer.parseInt(minStr);

                if (stock < min || stock > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Inventory must be between Min and Max.");
                    alert.showAndWait();
                    return;
                }

                Outsourced outsourcedPart = new Outsourced(id, nameStr, cost, stock, min, max, machineIdStr);

                outsourcedPart.setId(id);
                outsourcedPart.setName(nameStr);
                outsourcedPart.setPrice(cost);
                outsourcedPart.setStock(stock);
                outsourcedPart.setMin(min);
                outsourcedPart.setMax(max);
                outsourcedPart.setCompanyName(machineIdStr);

                Inventory.addPart(outsourcedPart);
            }

            // code to return to main view

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter valid values for all fields.");
            alert.showAndWait();
        } catch (ArithmeticException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/amandalixey/lixeysoftware1/MainScreen.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent, 850, 400);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    };



    /**
     * This method is called when the cancel button is clicked.
     * @param actionEvent
     * @throws IOException
     */
    public void onAddCancelPartButtonClicked(ActionEvent actionEvent) throws IOException {

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
     * @param url The URL location of the FXML file.
     * @param resourceBundle The ResourceBundle instance associated with the FXML file.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Create a random object
        Random randomId = new Random();

        // Generate random ints from 0 to 999
        int idResult = randomId.nextInt(1000);

        addPartIDText.setText(String.valueOf(idResult));
        addPartIDText.setEditable(false);
    }
}
