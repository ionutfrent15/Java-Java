package controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Admin;
import model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import utils.ControllerObserver;
import utils.IServer;

import java.io.IOException;
import java.rmi.RemoteException;

public class LoginController {
    public TextField userTextBox;
    public PasswordField passwordField;
    public Button loginBtn;
    public Label validateLabel;
    public CheckBox adminCheckBox;
    public ProgressIndicator progressIndicator;

    private IServer server;
    private Stage primaryStage;
    private Scene loginScene;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setLoginScene(Scene loginScene) {
        this.loginScene = loginScene;
    }

    private Scene showWindow(String resouceName){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClassLoader.getSystemResource(resouceName));
        Parent root;
        try {
            root = loader.load();
            WindowController controller = loader.getController();
            controller.setServer(server);
            controller.addControllerObserver((ControllerObserver) controller);
            controller.setPrimaryStage(primaryStage);
            controller.setLoginScene(loginScene);
            Scene scene = new Scene(root);
            return scene;
//            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Scene showAdminWindow(){
        return showWindow("adminview.fxml");
    }

    private Scene showUserWindow(){
        return showWindow("agentview.fxml");
    }

    private void connectToServer(){
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        server = (IServer) factory.getBean("service");
    }

    public void handlerLoginBtn(ActionEvent actionEvent) throws RemoteException {
        connectToServer();

        String username = userTextBox.getText();
        String password = passwordField.getText();
        boolean validate;
        boolean selected = adminCheckBox.isSelected();
        if(selected){
            validate = server.loginAdmin(new Admin(username, password));
        }
        else {
            validate = server.loginUser(new User(username, password));
        }
        if(validate){
            validateLabel.setText("");
            final Scene[] scene = new Scene[1];
            Task<LineChart> task = new Task<LineChart>() {

                @Override
                protected LineChart call() throws Exception {
                    for (int i = 0; i < 10; i++) {
                        updateProgress(10 * i, 100);
                    }
                    if(selected) {
                        scene[0] = showAdminWindow();
                    }
                    else scene[0] = showUserWindow();
                    updateProgress(100, 100);
                    return new LineChart(new NumberAxis(), new NumberAxis());
                }

            };
            progressIndicator.progressProperty().bind(task.progressProperty());
            task.setOnSucceeded(evt -> primaryStage.setScene(scene[0]));
            new Thread(task).start();
        }
        else {
            validateLabel.setText("Invalid");
        }
    }
}
