import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.awt.Event;
import java.io.File;
import javafx.stage.FileChooser.ExtensionFilter;
import java.util.List;

public class testi extends Application{
    public static void main(String[] args){

        launch(args);

    }

    //Windowsize
    private final int WINDOW_WIDTH = 1200;  //Default window width
    private final int WINDOW_HEIGHT = 700;  //Default window height
    private final Rectangle2D MAX_WINDOW_SIZE =
    Screen.getPrimary().getBounds();    //For fullscreen images size limit

    private List<File> selectedFiles;   //Selecting files with file manager
    private VBox layout;    //Layout for program
    private ImageView testImageView;    //Viewing images
    private Image showNewImage; //switching image
    private int imageCounter = 0;   //Keeping track of shown image
    private double newImageHeight;  //For images height
    private double newImageWidth;   //For images width

    //Buttons
    private Button fileButton;  //Select files
    private Button nextButton; //Button for next image
    private Button previousButton; //Button for previous image

    @Override
    public void start(Stage primaryStage){

        //System.out.println(MAX_WINDOW_SIZE);    //TROUBLESHOOT
        //Setup
        primaryStage.setTitle("Image Viewer");
        layout = new VBox();
        Scene primaryScene = new Scene(layout, WINDOW_WIDTH, WINDOW_HEIGHT);

        //Image
        testImageView = new ImageView();
        testImageView.setFitWidth(WINDOW_WIDTH);
        testImageView.setFitHeight(WINDOW_HEIGHT);

        //Buttons
        fileButton = new Button("Images");
        fileButton.setOnAction(e -> chooseFile(testImageView));
        nextButton = new Button("Next");
        nextButton.setOnAction(e -> nextImage());
        previousButton = new Button("Previous");
        previousButton.setOnAction(e -> previousImage());

        layout.setOnKeyPressed(e -> {
            switch (e.getCode()){
                case LEFT: previousImage();
                break;
                case RIGHT: nextImage();
                break;
                case F11: {
                    if (primaryStage.isFullScreen() == false){
                        primaryStage.setFullScreen(true);
                    }
                }
                break;
                case F9: startSlideshow();
                break;
                default:;
            }
        });

        //Add elements
        layout.getChildren().addAll(fileButton, nextButton, previousButton,
        testImageView);

        //Final setup
        primaryStage.setScene(primaryScene);
        primaryStage.show();

    }

    protected void chooseFile(ImageView viewer){   //Select files for viewing

        imageCounter = 0;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose your pictures");

        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Image Files", "*.png", "*.jpg"),
            new ExtensionFilter("PNG", "*.png"),
            new ExtensionFilter("JPG", "*.jpg"));

        selectedFiles = fileChooser.showOpenMultipleDialog(null);
        
        //System.out.println(selectedFiles);    //TROUBLESHOOT

        showNewImage();

    }

    protected void nextImage(){

        if (imageCounter >= selectedFiles.size() - 1) {
            imageCounter = 0;
        } else {
            imageCounter += 1;
        }
        showNewImage();

    }

    protected void previousImage(){

        if (imageCounter <= 0) {
            imageCounter = selectedFiles.size() - 1;
        } else {
            imageCounter -= 1;
        }
        showNewImage();

    }

    protected void showNewImage(){

        showNewImage = new Image("file:" + selectedFiles.get(imageCounter));
        newImageWidth = showNewImage.getWidth();
        newImageHeight = showNewImage.getHeight();

        if (MAX_WINDOW_SIZE.getWidth() < newImageWidth){
            newImageWidth = MAX_WINDOW_SIZE.getWidth();
        }
        if (MAX_WINDOW_SIZE.getHeight() < newImageHeight){
            newImageHeight = MAX_WINDOW_SIZE.getHeight();
        }
        
        testImageView.setFitWidth(newImageWidth);
        testImageView.setFitHeight(newImageHeight);

        testImageView.setImage(showNewImage);

    }

    protected void startSlideshow(){

    }

}