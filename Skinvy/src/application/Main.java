package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	
	Image image = new Image("/pic/LogoB.png");
	
	public static Stage stg;
	@Override
	public void start(Stage primaryStage) throws IOException{
			stg = primaryStage;
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
			primaryStage.setTitle("Skinvy");
			primaryStage.setScene(new Scene(root));
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(image);
			primaryStage.show();
		}
	
	public void changeSceneToLogin(String fxml)throws IOException{
		Parent pane = FXMLLoader.load(getClass().getResource(fxml));
		stg.getIcons().add(image);
		stg.setTitle("Skinvy");
		stg.setScene(new Scene(pane));
		stg.setResizable(false);
		stg.show();
	}
	
	public void changeScene(String fxml)throws IOException{
		Parent pane = FXMLLoader.load(getClass().getResource(fxml));
		stg.getIcons().add(image);
		stg.setTitle("Skinvy");
		stg.setScene(new Scene(pane));
		stg.setResizable(false);
		stg.setX(300);
		stg.setY(50);
		stg.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
