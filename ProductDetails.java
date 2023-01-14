package com.example.supplychain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class ProductDetails {
   public TableView<Product> productTable = new TableView<>();
   public Pane getAllProducts(){
       TableColumn id = new TableColumn("Id");
       id.setCellValueFactory(new PropertyValueFactory<>("id"));

       TableColumn name = new TableColumn("Name");
       name.setCellValueFactory(new PropertyValueFactory<>("name"));

       TableColumn price = new TableColumn("Price");
       price.setCellValueFactory(new PropertyValueFactory<>("price"));


       ProductDetails pd = new ProductDetails();

       //this was for testing
//       final ObservableList<Product> data = FXCollections.observableArrayList();
//       data.add(new Product(1,"Leno",999));
//       data.add(new Product(2,"Mac",1000));
//       final ObservableList<Product> data = (ObservableList<Product>) pd.getAllProducts();
       ObservableList<Product> items = Product.getAllProducts();
       productTable.setItems(items);
       productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       productTable.setMinSize(SupplyChain.WIDTH,SupplyChain.HEIGHT);
       productTable.getColumns().addAll(id,name,price);

       Pane tablePane = new Pane();
       tablePane.setMinSize(SupplyChain.WIDTH,SupplyChain.HEIGHT);
       tablePane.getChildren().add(productTable);
       return tablePane;
   }

   public Pane getProductsByName(String searchName){
       TableColumn id = new TableColumn("Id");
       id.setCellValueFactory(new PropertyValueFactory<>("id"));
       TableColumn name = new TableColumn("Name");
       name.setCellValueFactory(new PropertyValueFactory<>("name"));
       TableColumn price = new TableColumn("Price");
       price.setCellValueFactory(new PropertyValueFactory<>("price"));


       ProductDetails pd = new ProductDetails();
       ObservableList<Product> items = Product.getProductsByName(searchName);
       productTable.setItems(items);
       productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       productTable.setMinSize(SupplyChain.WIDTH,SupplyChain.HEIGHT);
       productTable.getColumns().addAll(id,name,price);

       Pane tablePane = new Pane();
       tablePane.setMinSize(SupplyChain.WIDTH,SupplyChain.HEIGHT);
       tablePane.getChildren().add(productTable);
//       tablePane.setStyle("-fx-background-color: black;");
       return tablePane;
   }

   public Product getSelectedRowValue(){
       if(productTable == null){
           System.out.println("Table object not found");
           return null;
       }
       if(productTable.getSelectionModel().getSelectedIndex()!=-1){
           Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
           System.out.println(selectedProduct.getId()+" "+
                                selectedProduct.getName()+" "+
                                selectedProduct.getPrice());
           return selectedProduct;
       }else{
           System.out.println("Nothing selected");
           return null;
       }
   }
}
