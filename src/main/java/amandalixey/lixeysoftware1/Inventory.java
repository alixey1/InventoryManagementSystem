package amandalixey.lixeysoftware1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * This is the class that holds all the parts and products and keeps a record of the inventory.
 */
public class Inventory {
public static ObservableList<Part> allParts= FXCollections.observableArrayList();
public static ObservableList<Product> allProducts= FXCollections.observableArrayList();
    public static void addPart(Part newPart) {

        System.out.println("add part");
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId) {
        for (Part part : allParts ) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    public static Product lookupProduct(int productId) {
        for (Product product : allProducts ) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getName().toLowerCase().contains(partName.toLowerCase())) {
                foundParts.add(allParts.get(i));
            }
        }
        return  foundParts;

    }

    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getName().toLowerCase().contains(productName.toLowerCase())) {
                foundProducts.add(allProducts.get(i));
            }
        }
        return  foundProducts;
    }

    public int getPartIndex(Part part) {
        return allParts.indexOf(part);
    }

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }

    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
