package Admin;

import Data.UserGroup;
import User.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {

    private Admin a;

    private Image image;

    @FXML
    private TreeView treeView;

    @FXML
    private TextField UIDTextField;
    @FXML
    private TextField GIDTextField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button addUserButton;
    @FXML
    private Button addGroupButton;
    @FXML
    private Button showUserCountButton;
    @FXML
    private Button showGroupCountButton;
    @FXML
    private Button showTweetCountButton;
    @FXML
    private Button showPositivePercentButton;

    @FXML
    private Button validIDCheckButton;

    @FXML
    private void addUserButtonClick(){
        a = Admin.getInstance();
        try {

            String id = UIDTextField.getText();
            TreeItem selected = (TreeItem)treeView.getSelectionModel().getSelectedItem();
            String group = selected.getValue().toString();

            a.addUser(id, group);
            TreeItem<String> temp = new TreeItem<String>(id);
            selected.getChildren().add(temp);
        }
        catch(Exception e){
            String message = e instanceof NullPointerException ? "Please Select a group" : e.getMessage();
            Alert a = new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK);
            a.setTitle("error");
            a.show();
        }
    }

    @FXML
    private void addGroupButtonClick(){
        a = Admin.getInstance();
        try {
            String name = GIDTextField.getText();
            TreeItem selected = (TreeItem)treeView.getSelectionModel().getSelectedItem();
            String group = selected.getValue().toString();

            a.addGroup(name, group);
            TreeItem<String> temp = new TreeItem<String>(name, new ImageView(image));
            selected.getChildren().add(temp);
        }
        catch(RuntimeException e){
            String message = e instanceof NullPointerException ? "Please Select a group" : e.getMessage();
            Alert a = new Alert(Alert.AlertType.NONE, message, ButtonType.OK);
            a.setTitle("error");
            a.show();
        }
    }

    @FXML
    private void launchUserViewButtonClick(){
        a = Admin.getInstance();
        try {
            TreeItem selected = (TreeItem) treeView.getSelectionModel().getSelectedItem();
            String id = selected.getValue().toString();

            a.launchUserView(id);
        }
        catch(RuntimeException e){
            Alert a = new Alert(Alert.AlertType.NONE, "Please select a User from TreeView", ButtonType.OK);
            a.setTitle("error");
            a.show();
            return;
        }
    }

    @FXML
    private void showUserCountButtonClick(){
        a = Admin.getInstance();
        messageLabel.setText("User Count: " + a.countUsers());
    }

    @FXML
    private void showGroupCountButtonClick(){
        a = Admin.getInstance();
        messageLabel.setText("Group Count: " + a.countGroups());
    }

    @FXML
    private void showTweetCountButtonClick(){
        a = Admin.getInstance();
        messageLabel.setText("Tweet Count: " + a.countTweets());
    }

    @FXML
    private void showPositivePercentButtonClick(){
        a = Admin.getInstance();
        messageLabel.setText("Positivity: " + a.getPositivityRatio() + "%");
    }

    @FXML
    private void checkIDs(){
        a = Admin.getInstance();
        messageLabel.setText("Invalid ID count: " + a.countInvalidIDs());
    }

    @FXML
    private void findLastUpdatedUser(){
        a = Admin.getInstance();
        User target = a.findLastUpdatedUser();
        if(target == null){
            messageLabel.setText("No users exist.");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        Date date = new Date(target.getLastUpdatedTime());
        messageLabel.setText(target.getID() + " was updated at " + sdf.format(date));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        image = new Image("\\Admin\\folder.png");
        TreeItem<String> root = new TreeItem<String>("root", new ImageView(image));
        treeView.setRoot(root);
        treeView.setShowRoot(true);
    }
}
