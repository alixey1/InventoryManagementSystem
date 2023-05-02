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
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the controller class for the modify part screen of the program.
 * FUTURE ENHANCEMENT: When modifying a part, I would like the previous information to still appear somewhere on the screen.
 */

public class ModifyPartController implements Initializable {
    public Label partMachineIDLabel;
    public TextField modifyPartIDText;
    public TextField modifyPartNameText;
    public TextField modifyPartInvText;
    public TextField modifyPartsPriceText;
    public TextField modifyPartsMaxText;
    public TextField modifyPartsMachineIDText;
    public TextField modifyPartsMinText;
    public RadioButton modifyInHouseRadio;
    public RadioButton modifyOutsourcedRadio;
    public Button modifySavePartButton;
    public Button modifyCancelPartButton;
    private static Part partToModify = null;
    public static void passPart(Part passedPart) {
        partToModify = passedPart;
        //System.out.println(passedPart.getName());
    }
    public int partIndex = Inventory.allParts.indexOf(partToModify);
    public int modifyId = partToModify.getId();
    public String modifyIdStr = String.valueOf(modifyId); //Converted int to String to display in field


    public String modifyName = partToModify.getName();
    public int modifyStock = partToModify.getStock();
    public String modifyStockStr = String.valueOf(modifyStock); //Converted int to String to display in field

    public double modifyCost = partToModify.getPrice();
    public String modifyCostStr = String.valueOf(modifyCost); //Converted double to String to display in field

    public int modifyMax = partToModify.getMax();
    public String modifyMaxStr = String.valueOf(modifyMax); //Converted int to String to display in field

    public int modifyMin = partToModify.getMin();
    public String modifyMinStr = String.valueOf(modifyMin); //Converted int to String to display in field

    /**
     * This method is called when the in house radio button is clicked.
     * @param actionEvent Triggers the event.
     */
    public void onModifyInHouseRadioClicked(ActionEvent actionEvent) {
        System.out.println("In-house radio clicked");
        partMachineIDLabel.setText("Machine ID");
    }

    /**
     * This method is called when the outsourced radio button is clicked.
     * @param actionEvent Triggers the event.
     */
    public void onModifyOutsourcedRadioClicked(ActionEvent actionEvent) {
        System.out.println("Outsourced radio button clicked");
        partMachineIDLabel.setText("Company Name");
    }



    /**
     * This method is called when the save part button is clicked.
     * RUNTIME ERROR: The modified part was saving as a new part instead of an updated part. I fixed this by using update instead of new on line 128.
     * @param actionEvent
     * @throws IOException
     */
    public void onModifySavePartButtonClicked(ActionEvent actionEvent) throws IOException {
       System.out.println("save button clicked");
        //int partIndex = 0;
        try {
            if (modifyInHouseRadio.isSelected()) {
                //Receive String input from associated text fields
                String idStr = modifyPartIDText.getText();
                String nameStr = modifyPartNameText.getText();
                String stockStr = modifyPartInvText.getText();
                String costStr = modifyPartsPriceText.getText();
                String maxStr = modifyPartsMaxText.getText();
                String minStr = modifyPartInvText.getText();
                String machineIdStr = modifyPartsMachineIDText.getText();

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
                InHouse modifiedInHousePart = new InHouse(id, nameStr, cost, stock, max, min, machineId) {
                };

                //Transfer variables to new inHousePart
                modifiedInHousePart.setId(id);
                modifiedInHousePart.setName(nameStr);
                modifiedInHousePart.setPrice(cost);
                modifiedInHousePart.setStock(stock);
                modifiedInHousePart.setMax(max);
                modifiedInHousePart.setMin(min);
                modifiedInHousePart.setMachineId(machineId);
                try {
                    if (modifiedInHousePart.getMin() > modifiedInHousePart.getMax()) {
                        throw new ArithmeticException("Min is greater than Max.");
                    }
                    try {
                        if (modifiedInHousePart.getStock() < modifiedInHousePart.getMin() || modifiedInHousePart.getStock() > modifiedInHousePart.getMax()) {
                            throw new ArithmeticException("Inventory must be between Min and Max");
                        }


                        Inventory.updatePart(partIndex, modifiedInHousePart);



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
                String idStr = modifyPartIDText.getText();
                String nameStr = modifyPartNameText.getText();
                String stockStr = modifyPartInvText.getText();
                String costStr = modifyPartsPriceText.getText();
                String maxStr = modifyPartsMaxText.getText();
                String minStr = modifyPartsMinText.getText();
                String machineIdStr = modifyPartsMachineIDText.getText();

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

                Outsourced modifyOutsourcedPart = new Outsourced(id, nameStr, cost, stock, min, max, machineIdStr);

                modifyOutsourcedPart.setId(id);
                modifyOutsourcedPart.setName(nameStr);
                modifyOutsourcedPart.setPrice(cost);
                modifyOutsourcedPart.setStock(stock);
                modifyOutsourcedPart.setMin(min);
                modifyOutsourcedPart.setMax(max);
                modifyOutsourcedPart.setCompanyName(machineIdStr);


                Inventory.updatePart(partIndex, modifyOutsourcedPart);
            }

            // code to return to main view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/amandalixey/lixeysoftware1/MainScreen.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent, 850, 400);
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();


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

    }


    /**
     * This method is called when the cancel button is clicked.
     * @param actionEvent
     * @throws IOException
     */
    public void onModifyCancelPartButtonClicked(ActionEvent actionEvent) throws IOException {

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
     * RUNTIME ERROR: The radio buttons were not loading in selected. I fixed this by adding lines 244-254.
     * @param url The URL location of the FXML file.
     * @param resourceBundle The ResourceBundle instance associated with the FXML file.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(partToModify.getName());
        modifyPartIDText.setText(Integer.toString(partToModify.getId()));
        modifyPartNameText.setText(partToModify.getName());
        modifyPartInvText.setText(Integer.toString(partToModify.getStock()));
        modifyPartsPriceText.setText(Integer.toString((int) partToModify.getPrice()));
        modifyPartsMaxText.setText(Integer.toString(partToModify.getMax()));
        modifyPartsMinText.setText(Integer.toString(partToModify.getMin()));

        if(partToModify instanceof InHouse){ //Check to see if its in house
            int modifyMachComp = ((InHouse)partToModify).getMachineId();
            String modifyMachCompStr = String.valueOf(modifyMachComp);
            modifyPartsMachineIDText.setText(modifyMachCompStr); //Update the text field
            modifyInHouseRadio.fire(); // Set radio button to indicate instance type
        }

        else if(partToModify instanceof Outsourced){
            String modifyMachComp = ((Outsourced)partToModify).getCompanyName();
            modifyPartsMachineIDText.setText(modifyMachComp);
            modifyOutsourcedRadio.fire();

            //Set fields to partToModify attributes
            modifyPartIDText.setText(modifyIdStr);
            modifyPartNameText.setText(modifyName);
            modifyPartInvText.setText(modifyStockStr);
            modifyPartsPriceText.setText(modifyCostStr);
            modifyPartsMaxText.setText(modifyMaxStr);
            modifyPartsMinText.setText(modifyMinStr);
        }

    }
}
