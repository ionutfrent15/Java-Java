import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartClient extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loaderLogin = new FXMLLoader();
        loaderLogin.setLocation(ClassLoader.getSystemResource("loginview.fxml"));
        Parent rootLogin = loaderLogin.load();
        Scene scene = new Scene(rootLogin);

        LoginController loginController = loaderLogin.getController();
        loginController.setPrimaryStage(primaryStage);
        loginController.setLoginScene(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
