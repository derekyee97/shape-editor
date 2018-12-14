/**********************************************************
 * 
 * 
 * @authors: Derek Yee, Kimberlyn Krishnan, Ngan Nguyen
 * 
 * @date due: 12/14/2018
 * 
 * @class: CS2450 T/TH 4-5:15pm 
 * 
 * @assignment: Homework 4 Group Project Shape Editor
 ************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Translate;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;

public class CS2450_Homework_4 extends Application {
	// global variables
	PerspectiveCamera camera = new PerspectiveCamera(true);
	static Shape selectedShape = null;
	List<Shape> shapesListStore = new ArrayList<>();
	BorderPane root = new BorderPane();
	Group shapes = new Group();
	SubScene shapeScene = new SubScene(shapes, 700, 550, true, SceneAntialiasing.DISABLED);
	// PerspectiveCamera camera=new PerspectiveCamera(true);

	int idNumCounter = 1; // will give unique id number to each shape we create

	GridPane refCreateShapeWindowStep1 = new GridPane();
	GridPane createSphereMenu = new GridPane();
	GridPane createCylinderMenu = new GridPane();
	GridPane createBoxMenu = new GridPane();

	Button submitShapeChoice = new Button("Submit");
	Button submitSphere = new Button("Submit");
	Button submitCylinder = new Button("Submit");
	Button submitBox = new Button("Submit");
	Button addShapeButton = new Button("Add Shape");
	Button left = new Button("Left");
	Button right = new Button("Right");
	Button up = new Button("Up");
	Button down = new Button("Down");
	Button hRotate=new Button("Rotate X Axis");
	Button vRotate=new Button("Rotate Y Axis");
	Button zRotate=new Button("Rotate Z Axis");
		
	RadioButton sphereB = new RadioButton("Sphere");
	RadioButton boxB = new RadioButton("Box");
	RadioButton cylinderB = new RadioButton("Cylinder");

	TextField xPositionSphere = new TextField("Please enter an integer to place shape in the x direction");
	TextField xPositionCylinder = new TextField("Please enter an integer to place shape in the x direction");
	TextField xPositionBox = new TextField("Please enter an integer to place shape in the x direction");
	TextField yPositionBox = new TextField("Please enter an integer to place shape in the y direction");
	TextField yPositionSphere = new TextField("Please enter an integer to place shape in the y direction");
	TextField yPositionCylinder = new TextField("Please enter an integer to place shape in the y direction");
	TextField sphereRadius = new TextField("Please enter an integer for sphere radius");
	TextField cylinderRadius = new TextField("Please enter an integer for cylinder radius");
	TextField cylinderHeight = new TextField("Please enter an integer for cylinder height");
	TextField boxHeight = new TextField("Please enter an integer for box height");
	TextField boxLength = new TextField("Please enter an integer for box length");
	TextField boxWidth = new TextField("Please enter an integer for box width");

	VBox createShapesMenu;
	VBox sphereMenu;
	VBox boxMenu;
	VBox cylinderMenu;

	HBox rotateButtons=new HBox(10,hRotate,vRotate,zRotate);
		
	FileChooser fileChooser = new FileChooser();
	Scanner fileReader;
	PrintWriter printWriter;
	FileWriter fileWriter;
	String selected="";
	String selected2="";
	Slider scaleSlider = new Slider(0.0,100.0,50.0);

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		createShapeMenu();
		
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt")); // only want to open text
																							// files
		createShapesMenu.setAlignment(Pos.CENTER);
		sphereMenu.setAlignment(Pos.CENTER);
		boxMenu.setAlignment(Pos.CENTER);
		cylinderMenu.setAlignment(Pos.CENTER);
		refCreateShapeWindowStep1.setAlignment(Pos.CENTER);
		createSphereMenu.setAlignment(Pos.CENTER);
		createBoxMenu.setAlignment(Pos.CENTER);
		createCylinderMenu.setAlignment(Pos.CENTER);
		rotateButtons.setAlignment(Pos.CENTER);
		camera.getTransforms().addAll(new Translate(0, 0, -100)); //HERE
		
		
		shapeScene.setCamera(camera);
		

		xPositionBox.textProperty().addListener(new changeTextListener(xPositionBox));
		yPositionBox.textProperty().addListener(new changeTextListener(yPositionBox));
		xPositionSphere.textProperty().addListener(new changeTextListener(xPositionSphere));
		yPositionSphere.textProperty().addListener(new changeTextListener(yPositionSphere));
		xPositionCylinder.textProperty().addListener(new changeTextListener(xPositionCylinder));
		yPositionCylinder.textProperty().addListener(new changeTextListener(yPositionCylinder));
		sphereRadius.textProperty().addListener(new changeTextListener(sphereRadius));
		cylinderRadius.textProperty().addListener(new changeTextListener(cylinderRadius));
		cylinderHeight.textProperty().addListener(new changeTextListener(cylinderHeight));
		boxHeight.textProperty().addListener(new changeTextListener(boxHeight));
		boxWidth.textProperty().addListener(new changeTextListener(boxWidth));
		boxLength.textProperty().addListener(new changeTextListener(boxLength));

		MenuBar menuBar = new MenuBar();
		Menu fileChoice = new Menu("File");
		menuBar.getMenus().add(fileChoice);
		MenuItem saveChoice = new MenuItem("Save");
		MenuItem openChoice = new MenuItem("Open");
		fileChoice.getItems().addAll(saveChoice, openChoice);

		shapeScene.setFill(Color.LIGHTBLUE);

		ListView<String> backgroundColorList = new ListView();
		Label BchangeColor = new Label("Select Background Color: ");
		backgroundColorList.getItems().addAll("blue", "red", "pink", "black", "orange", "green", "purple");
		backgroundColorList.setPrefSize(120, 150);

		backgroundColorList.getSelectionModel().selectedItemProperty().addListener((source, o, n) -> {
			selected = backgroundColorList.getSelectionModel().getSelectedItem();
			if (selected != null) {
				switch (selected) {
				case "blue":
					shapeScene.setFill(Color.ROYALBLUE);
					break;
				case "red":
					shapeScene.setFill(Color.RED);
					break;
				case "pink":
					shapeScene.setFill(Color.PINK);
					break;
				case "black":
					shapeScene.setFill(Color.BLACK);
					break;
				case "orange":
					shapeScene.setFill(Color.ORANGE);
					break;
				case "green":
					shapeScene.setFill(Color.GREEN);
					break;
				case "purple":
					shapeScene.setFill(Color.PLUM);
					break;
				}
			}
		});

		ListView<String> shapeColorList = new ListView();
		Label SchangeColor = new Label("Select Shape Color: ");
		shapeColorList.getItems().addAll("blue", "red", "pink", "black", "orange", "green", "purple");
		shapeColorList.setPrefSize(120, 150);

		shapeColorList.getSelectionModel().selectedItemProperty().addListener((source, o, n) -> {
			selected2 = shapeColorList.getSelectionModel().getSelectedItem();
			if (selected2 != null) {
				if ((selectedShape.shape).equals("sphere")) {
					switch (selected2) {
					case "blue":
						selectedShape.thisSphere.setMaterial(new PhongMaterial(Color.ROYALBLUE));
						selectedShape.color = selected2;
						break;
					case "red":
						selectedShape.thisSphere.setMaterial(new PhongMaterial(Color.RED));
						selectedShape.color = selected2;
						break;
					case "pink":
						selectedShape.thisSphere.setMaterial(new PhongMaterial(Color.PINK));
						selectedShape.color = selected2;
						break;
					case "black":
						selectedShape.thisSphere.setMaterial(new PhongMaterial(Color.BLACK));
						selectedShape.color = selected2;
						break;
					case "orange":
						selectedShape.thisSphere.setMaterial(new PhongMaterial(Color.ORANGE));
						selectedShape.color = selected2;
						break;
					case "green":
						selectedShape.thisSphere.setMaterial(new PhongMaterial(Color.GREEN));
						selectedShape.color = selected2;
						break;
					case "purple":
						selectedShape.thisSphere.setMaterial(new PhongMaterial(Color.PLUM));
						selectedShape.color = selected2;
						break;
					}
				}
				if ((selectedShape.shape).equals("cylinder")) {
					switch (selected2) {
					case "blue":
						selectedShape.thisCylinder.setMaterial(new PhongMaterial(Color.ROYALBLUE));
						selectedShape.color = selected2;
						break;
					case "red":
						selectedShape.thisCylinder.setMaterial(new PhongMaterial(Color.RED));
						selectedShape.color = selected2;
						break;
					case "pink":
						selectedShape.thisCylinder.setMaterial(new PhongMaterial(Color.PINK));
						selectedShape.color = selected2;
						break;
					case "black":
						selectedShape.thisCylinder.setMaterial(new PhongMaterial(Color.BLACK));
						selectedShape.color = selected2;
						break;
					case "orange":
						selectedShape.thisCylinder.setMaterial(new PhongMaterial(Color.ORANGE));
						selectedShape.color = selected2;
						break;
					case "green":
						selectedShape.thisCylinder.setMaterial(new PhongMaterial(Color.GREEN));
						selectedShape.color = selected2;
						break;
					case "purple":
						selectedShape.thisCylinder.setMaterial(new PhongMaterial(Color.PLUM));
						selectedShape.color = selected2;
						break;
					}
				}
				if ((selectedShape.shape).equals("box")) {
					switch (selected2) {
					case "blue":
						selectedShape.thisBox.setMaterial(new PhongMaterial(Color.ROYALBLUE));
						selectedShape.color = selected2;
						break;
					case "red":
						selectedShape.thisBox.setMaterial(new PhongMaterial(Color.RED));
						selectedShape.color = selected2;
						break;
					case "pink":
						selectedShape.thisBox.setMaterial(new PhongMaterial(Color.PINK));
						selectedShape.color = selected2;
					case "black":
						selectedShape.thisBox.setMaterial(new PhongMaterial(Color.BLACK));
						selectedShape.color =  selected2;
						break;
					case "orange":
						selectedShape.thisBox.setMaterial(new PhongMaterial(Color.ORANGE));
						selectedShape.color = selected2;
						break;
					case "green":
						selectedShape.thisBox.setMaterial(new PhongMaterial(Color.GREEN));
						selectedShape.color = selected2;
						break;
					case "purple":
						selectedShape.thisBox.setMaterial(new PhongMaterial(Color.PLUM));
						selectedShape.color =  selected2;
						break;
					}
				}

			}
		});

		
		
		
		
		Label rotateLabel = new Label("Push corresponding buttons to rotate/zoom in that direction");
		//HANDLING ROTATES
		vRotate.setOnAction(event->{
			selectedShape.verticleAngle+=5;
			Rotate rotate=new Rotate(selectedShape.verticleAngle,Rotate.X_AXIS);
			if(selectedShape.verticleAngle==360)
			{
				selectedShape.verticleAngle=0;
			}
			if(selectedShape.shape.equals("cylinder"))
			{
				selectedShape.thisCylinder.getTransforms().add(rotate);
			}
			else if(selectedShape.shape.equals("box"))
			{
				selectedShape.thisBox.getTransforms().add(rotate);
			}
			else if(selectedShape.shape.equals("sphere"))
			{
				selectedShape.thisSphere.getTransforms().add(rotate);
			}
		});
		hRotate.setOnAction(event->{
			selectedShape.horizontalAngle+=5;
			Rotate rotate=new Rotate(selectedShape.horizontalAngle,Rotate.Y_AXIS);
			if(selectedShape.horizontalAngle==360)
			{
				selectedShape.horizontalAngle=0;
			}
			if(selectedShape.shape.equals("cylinder"))
			{
				selectedShape.thisCylinder.getTransforms().add(rotate);
			}
			else if(selectedShape.shape.equals("box"))
			{
				selectedShape.thisBox.getTransforms().add(rotate);
			}
			else if(selectedShape.shape.equals("sphere"))
			{
				selectedShape.thisSphere.getTransforms().add(rotate);
			}
		});
		zRotate.setOnAction(event->{
			selectedShape.zAngle+=5;
			Rotate rotate=new Rotate(selectedShape.zAngle,Rotate.Z_AXIS);
			if(selectedShape.zAngle==360)
			{
				selectedShape.zAngle=0;
			}
			if(selectedShape.shape.equals("cylinder"))
			{
				selectedShape.thisCylinder.getTransforms().add(rotate);
			}
			else if(selectedShape.shape.equals("box"))
			{
				selectedShape.thisBox.getTransforms().add(rotate);
			}
			else if(selectedShape.shape.equals("sphere"))
			{
				selectedShape.thisSphere.getTransforms().add(rotate);
			}
		});
               
	
	
		
		
		
		
		
		
		
	
	
	
                
		Label leftRightLabel = new Label("Move shape left/right");
		HBox hbox1 = new HBox(10, left, right);
		hbox1.setAlignment(Pos.CENTER);

		left.setOnAction(event -> {
                    selectedShape.xPosition = selectedShape.xPosition - 1;
                    if ((selectedShape.shape).equals("box")) {
                            selectedShape.thisBox.setTranslateX(selectedShape.xPosition);
                    }
                    if ((selectedShape.shape).equals("cylinder")) {
                            selectedShape.thisCylinder.setTranslateX(selectedShape.xPosition);
                    }
                    if ((selectedShape.shape).equals("sphere")) {
                            selectedShape.thisSphere.setTranslateX(selectedShape.xPosition);
                    }
		});

		right.setOnAction(event -> {
                    selectedShape.xPosition = selectedShape.xPosition + 1;
                    if ((selectedShape.shape).equals("box")) {
                            selectedShape.thisBox.setTranslateX(selectedShape.xPosition);
                    }
                    if ((selectedShape.shape).equals("cylinder")) {
                            selectedShape.thisCylinder.setTranslateX(selectedShape.xPosition);
                    }
                    if ((selectedShape.shape).equals("sphere")) {
                            selectedShape.thisSphere.setTranslateX(selectedShape.xPosition);
                    }
		});

		up.setOnAction(event -> {
                    selectedShape.yPosition = selectedShape.yPosition - 1;
			if ((selectedShape.shape).equals("box")) {
				selectedShape.thisBox.setTranslateY(selectedShape.yPosition);
			}
			if ((selectedShape.shape).equals("cylinder")) {
				selectedShape.thisCylinder.setTranslateY(selectedShape.yPosition);
			}
			if ((selectedShape.shape).equals("sphere")) {
				selectedShape.thisSphere.setTranslateY(selectedShape.yPosition);
			}
		});

		down.setOnAction(event -> {
                    selectedShape.yPosition = selectedShape.yPosition + 1;
                    if ((selectedShape.shape).equals("box")) {
                            selectedShape.thisBox.setTranslateY(selectedShape.yPosition);
                    }
                    if ((selectedShape.shape).equals("cylinder")) {
                            selectedShape.thisCylinder.setTranslateY(selectedShape.yPosition);
                    }
                    if ((selectedShape.shape).equals("sphere")) {
                            selectedShape.thisSphere.setTranslateY(selectedShape.yPosition);
                    }
		});
		Label upDownLabel = new Label("Move shape up/down");
		HBox hbox2 = new HBox(10, up, down);
		hbox2.setAlignment(Pos.CENTER);
		Label scaleLabel = new Label("Slide to change scale ");
		///////////////////////////////////////////
       
		scaleSlider.setShowTickLabels(true);
        scaleSlider.setShowTickMarks(true);
        scaleSlider.valueProperty().addListener((observable, oldvalue, newvalue) -> 
        	{
        		double size=(scaleSlider.getValue())/50;
                selectedShape.scale.setX(size);
                selectedShape.scale.setY(size);
                selectedShape.scale.setZ(size);
                            
             });
               
		//ADDING ALL NECESSARY NODES TO RIGHT SIDE OF MENU
        VBox rightMenu = new VBox(10, BchangeColor, backgroundColorList, SchangeColor, shapeColorList, leftRightLabel,
				hbox1, upDownLabel, hbox2, rotateLabel, rotateButtons,scaleLabel, scaleSlider);

		rightMenu.setPadding(new Insets(10));

		root.setPadding(new Insets(0, 35, 20, 0));
		root.setCenter(shapeScene);
		root.setTop(menuBar);
		root.setRight(rightMenu);
		HBox center = new HBox(10, addShapeButton);
		center.setAlignment(Pos.CENTER);
		root.setBottom(center);
		

		Scene myScene = new Scene(root, 1200, 650);
        myScene.getStylesheets().add("./res/shapeCreatorCSS.css");
		primaryStage.setScene(myScene);
		primaryStage.setFullScreen(true);
		primaryStage.show();

		// HANDLING BUTTON CLICKS AND MENU

		// handling opening a file to display shapes saved
		openChoice.setOnAction(event -> {

		});

		addShapeButton.setOnAction(event -> {
			root.setCenter(createShapesMenu);
		});

		// when user selects shape they want to create
		submitShapeChoice.setOnAction(event -> {
//	    	   for(Shape x:shapesListStore)
//	    	   {
//	    		   System.out.println(x.shape);
//	    	   }
			if (sphereB.isSelected()) {
				root.setCenter(sphereMenu);
			} else if (boxB.isSelected()) {
				root.setCenter(boxMenu);
			} else if (cylinderB.isSelected()) {
				root.setCenter(cylinderMenu);
			}
		});
		// HANDLING SPHERE CREATION
		submitSphere.setOnAction(event -> {
			Shape creatingSphere = new Shape("sphere");
			creatingSphere.xPosition = Integer.parseInt(xPositionSphere.getText());
			creatingSphere.yPosition = Integer.parseInt(yPositionSphere.getText());
			creatingSphere.radius = Integer.parseInt(sphereRadius.getText());
			creatingSphere.idNum = idNumCounter;
			idNumCounter = idNumCounter + 1;
			creatingSphere.scale=new Scale(1,1,1);
			creatingSphere.xrotate=new Rotate(0,Rotate.X_AXIS);
			creatingSphere.yrotate=new Rotate(0,Rotate.Y_AXIS);
			
			
			Sphere createdSphere = new Sphere(creatingSphere.radius);
			createdSphere.setId(Integer.toString(creatingSphere.idNum));
			int spherex = Integer.parseInt(xPositionSphere.getText());
			int spherey = Integer.parseInt(yPositionSphere.getText());
			createdSphere.getTransforms().addAll(creatingSphere.xrotate,creatingSphere.yrotate);
			createdSphere.getTransforms().add(new Translate(spherex, -spherey, 0));
			createdSphere.getTransforms().addAll(creatingSphere.scale);
			createdSphere.setOnMouseClicked(ActionEvent -> {
				selectedShape = creatingSphere;
				selectedShape.xPosition = spherex;
				selectedShape.yPosition = -spherey;
			});
			// Translate translator=new Translate(10,0,0);
			// createdSphere.getTransforms().add(translator);
			shapes.getChildren().addAll(createdSphere);
			creatingSphere.thisSphere = createdSphere;
			root.setCenter(shapeScene);
			shapesListStore.add(creatingSphere);
		});
		submitBox.setOnAction(event -> {
			Shape creatingBox = new Shape("box");
			creatingBox.xPosition = Integer.parseInt(xPositionBox.getText());
			creatingBox.yPosition = Integer.parseInt(yPositionBox.getText());
			creatingBox.length = Integer.parseInt(boxLength.getText());
			creatingBox.width = Integer.parseInt(boxWidth.getText());
			creatingBox.height = Integer.parseInt(boxHeight.getText());
			creatingBox.idNum = idNumCounter;
			creatingBox.scale=new Scale(1,1,1);
			creatingBox.xrotate=new Rotate(0,Rotate.X_AXIS);
			creatingBox.yrotate=new Rotate(0,Rotate.Y_AXIS);
			idNumCounter = idNumCounter + 1;
			Box createdBox = new Box(creatingBox.width, creatingBox.height, creatingBox.length);
		
			createdBox.getTransforms().addAll(creatingBox.xrotate,creatingBox.yrotate);
			createdBox.getTransforms().addAll(creatingBox.scale);
			int boxx = Integer.parseInt(xPositionBox.getText());
			int boxy = Integer.parseInt(yPositionBox.getText());
			createdBox.getTransforms().add(new Translate(boxx, -boxy, 0));
			createdBox.setId(Integer.toString(creatingBox.idNum));
			createdBox.setOnMouseClicked(ActionEvent -> {
				selectedShape = creatingBox;
				selectedShape.xPosition = boxx;
				selectedShape.yPosition = -boxy;
			});
	    	 
			shapes.getChildren().addAll(createdBox);
			root.setCenter(shapeScene);
			creatingBox.thisBox = createdBox;
			shapesListStore.add(creatingBox);

		});
		submitCylinder.setOnAction(event -> {
			Shape creatingCylinder = new Shape("cylinder");
			creatingCylinder.xPosition = Integer.parseInt(xPositionCylinder.getText());
			creatingCylinder.yPosition = Integer.parseInt(yPositionCylinder.getText());
			creatingCylinder.radius = Integer.parseInt(cylinderRadius.getText());
			creatingCylinder.height = Integer.parseInt(cylinderHeight.getText());
			creatingCylinder.idNum = idNumCounter;
			creatingCylinder.scale=new Scale(1,1,1);
			creatingCylinder.xrotate=new Rotate(0,Rotate.X_AXIS);
			creatingCylinder.yrotate=new Rotate(0,Rotate.Y_AXIS);
			idNumCounter++;
			Cylinder createdCylinder = new Cylinder(creatingCylinder.radius, creatingCylinder.height);
			// test z rotate
			
			createdCylinder.getTransforms().addAll(creatingCylinder.xrotate,creatingCylinder.yrotate);
			int testx = Integer.parseInt(xPositionCylinder.getText());
			int testy = Integer.parseInt(yPositionCylinder.getText());
			createdCylinder.getTransforms().addAll(new Translate(testx, -testy, 0));
			createdCylinder.getTransforms().addAll(creatingCylinder.scale);
			createdCylinder.setId(Integer.toString(creatingCylinder.idNum));
			createdCylinder.setOnMouseClicked(ActionEvent -> {
				selectedShape = creatingCylinder;
				selectedShape.xPosition = testx;
				selectedShape.yPosition = -testy;
				
				
			});
			shapes.getChildren().addAll(createdCylinder);
			root.setCenter(shapeScene);
			creatingCylinder.thisCylinder = createdCylinder;
			shapesListStore.add(creatingCylinder);
		});

		openChoice.setOnAction(event -> {
			erase();
			File fileChosen = fileChooser.showOpenDialog(primaryStage);
			try {
				fileReader = new Scanner(fileChosen);
				String lineToBeRead = "";
				String[] tokens;
				while (fileReader.hasNext()) {
					lineToBeRead = fileReader.nextLine();
					tokens = lineToBeRead.split(",");
					
					if(tokens[0].equals("sphere"))
					{

						Shape creatingSphere = new Shape("sphere");
						creatingSphere.xPosition = Integer.parseInt(tokens[1]);
						creatingSphere.yPosition = Integer.parseInt(tokens[2]);
						creatingSphere.radius = Integer.parseInt(tokens[3]);
						creatingSphere.color=tokens[4];
						creatingSphere.idNum = idNumCounter;
						idNumCounter = idNumCounter + 1;
						creatingSphere.scale=new Scale(1,1,1);
						creatingSphere.scale.setX(Double.parseDouble(tokens[5]));
						creatingSphere.scale.setY(Double.parseDouble(tokens[5]));
						creatingSphere.scale.setZ(Double.parseDouble(tokens[5]));
						creatingSphere.xrotate=new Rotate(0,Rotate.X_AXIS);
						creatingSphere.yrotate=new Rotate(0,Rotate.Y_AXIS);
						
						
						Sphere createdSphere = new Sphere(creatingSphere.radius);
						createdSphere.setId(Integer.toString(creatingSphere.idNum));
						int spherex = creatingSphere.xPosition;
						int spherey = creatingSphere.yPosition;
						createdSphere.getTransforms().addAll(creatingSphere.xrotate,creatingSphere.yrotate);
						createdSphere.getTransforms().add(new Translate(spherex, spherey, 0));
						createdSphere.getTransforms().addAll(creatingSphere.scale);
						
						createdSphere.setOnMouseClicked(ActionEvent -> {
							selectedShape = creatingSphere;
							selectedShape.xPosition = spherex;
							selectedShape.yPosition = spherey;
						});
						// Translate translator=new Translate(10,0,0);
						// createdSphere.getTransforms().add(translator);
						
						shapes.getChildren().addAll(createdSphere);
						creatingSphere.thisSphere = createdSphere;
						setColor(creatingSphere,creatingSphere.color,creatingSphere.shape);
						root.setCenter(shapeScene);
						
						shapesListStore.add(creatingSphere);
					}
					
					else if(tokens[0].equals("box"))
					{
						Shape creatingBox = new Shape("box");
						creatingBox.xPosition = Integer.parseInt(tokens[1]);
						creatingBox.yPosition = Integer.parseInt(tokens[2]);
						creatingBox.length = Integer.parseInt(tokens[3]);
						creatingBox.width = Integer.parseInt(tokens[4]);
						creatingBox.height = Integer.parseInt(tokens[5]);
						creatingBox.idNum = idNumCounter;
						creatingBox.scale=new Scale(1,1,1);
						creatingBox.scale.setX(Double.parseDouble(tokens[7]));
						creatingBox.scale.setY(Double.parseDouble(tokens[7]));
						creatingBox.scale.setZ(Double.parseDouble(tokens[7]));
						creatingBox.horizontalAngle=Double.parseDouble(tokens[8]);
						creatingBox.verticleAngle=Double.parseDouble(tokens[9]);
						creatingBox.zAngle=Double.parseDouble(tokens[10]);
						creatingBox.xrotate=new Rotate(0,Rotate.X_AXIS);
						creatingBox.yrotate=new Rotate(0,Rotate.Y_AXIS);
						creatingBox.color=tokens[6];
						idNumCounter = idNumCounter + 1;
						Box createdBox = new Box(creatingBox.width, creatingBox.height, creatingBox.length);
					
						createdBox.getTransforms().addAll(creatingBox.xrotate,creatingBox.yrotate);
						createdBox.getTransforms().addAll(creatingBox.scale);
						int boxx = creatingBox.xPosition;
						int boxy =  creatingBox.yPosition;
						createdBox.getTransforms().add(new Translate(boxx, boxy, 0));
						createdBox.getTransforms().addAll(new Rotate(selectedShape.verticleAngle,Rotate.X_AXIS));
						createdBox.getTransforms().addAll(new Rotate(selectedShape.horizontalAngle,Rotate.Y_AXIS));
						createdBox.getTransforms().addAll(new Rotate(selectedShape.zAngle,Rotate.Z_AXIS));
						createdBox.setId(Integer.toString(creatingBox.idNum));
						createdBox.setOnMouseClicked(ActionEvent -> {
							selectedShape = creatingBox;
							selectedShape.xPosition = boxx;
							selectedShape.yPosition = boxy;
						});
				    	 
						shapes.getChildren().addAll(createdBox);
						root.setCenter(shapeScene);
						creatingBox.thisBox = createdBox;
						setColor(creatingBox,creatingBox.color,creatingBox.shape);
						shapesListStore.add(creatingBox);
					}
					else if(tokens[0].equals("cylinder"))
					{
						Shape creatingCylinder = new Shape("cylinder");
						creatingCylinder.xPosition = Integer.parseInt(tokens[1]);
						creatingCylinder.yPosition = Integer.parseInt(tokens[2]);
						creatingCylinder.radius = Integer.parseInt(tokens[3]);
						creatingCylinder.height = Integer.parseInt(tokens[4]);
						creatingCylinder.idNum = idNumCounter;
						creatingCylinder.scale=new Scale(1,1,1);
						creatingCylinder.scale.setX(Double.parseDouble(tokens[6]));
						creatingCylinder.scale.setY(Double.parseDouble(tokens[6]));
						creatingCylinder.scale.setZ(Double.parseDouble(tokens[6]));
						creatingCylinder.xrotate=new Rotate(0,Rotate.X_AXIS);
						creatingCylinder.yrotate=new Rotate(0,Rotate.Y_AXIS);
						creatingCylinder.horizontalAngle=Double.parseDouble(tokens[7]);
						creatingCylinder.verticleAngle=Double.parseDouble(tokens[8]);
						creatingCylinder.zAngle=Double.parseDouble(tokens[9]);
						idNumCounter++;
						Cylinder createdCylinder = new Cylinder(creatingCylinder.radius, creatingCylinder.height);
						// test z rotate
						
						createdCylinder.getTransforms().addAll(creatingCylinder.xrotate,creatingCylinder.yrotate);
						int testx = creatingCylinder.xPosition;
						int testy = creatingCylinder.yPosition;
						createdCylinder.getTransforms().addAll(new Translate(testx, testy, 0));
						createdCylinder.getTransforms().addAll(creatingCylinder.scale);
						createdCylinder.getTransforms().addAll(new Rotate(selectedShape.verticleAngle,Rotate.X_AXIS));
						createdCylinder.getTransforms().addAll(new Rotate(selectedShape.horizontalAngle,Rotate.Y_AXIS));
						createdCylinder.getTransforms().addAll(new Rotate(selectedShape.zAngle,Rotate.Z_AXIS));
						createdCylinder.setId(Integer.toString(creatingCylinder.idNum));
						createdCylinder.setOnMouseClicked(ActionEvent -> {
							selectedShape = creatingCylinder;
							selectedShape.xPosition = testx;
							selectedShape.yPosition = testy;
							
							
						});
						shapes.getChildren().addAll(createdCylinder);
						root.setCenter(shapeScene);
						creatingCylinder.thisCylinder = createdCylinder;
						setColor(creatingCylinder,creatingCylinder.color,creatingCylinder.shape);
						shapesListStore.add(creatingCylinder);
					}
					else if(tokens[0].equals("background"))
					{
						switch (tokens[1]) {
						case "blue":
							shapeScene.setFill(Color.ROYALBLUE);
							
							break;
						case "red":
							shapeScene.setFill(Color.RED);
							break;
						case "pink":
							shapeScene.setFill(Color.PINK);
							break;
						case "black":
							shapeScene.setFill(Color.BLACK);
							break;
						case "orange":
							shapeScene.setFill(Color.ORANGE);
							break;
						case "green":
							shapeScene.setFill(Color.GREEN);
							break;
						case "purple":
							shapeScene.setFill(Color.PLUM);
							break;
						}
					}
				}
				fileReader.close();
			} catch (FileNotFoundException e) {
				Alert error=new Alert(AlertType.ERROR);
				error.setContentText("Text File has wrongly formatted data");
				error.showAndWait();
			}

		});

		// SAVING A FILE
		saveChoice.setOnAction(event -> {
			File filePath = fileChooser.showSaveDialog(primaryStage);
			try {
				fileWriter = new FileWriter(filePath.getPath());//,true);  //add true if want to save contents of file if already created
				printWriter = new PrintWriter(fileWriter);
				for (Shape shape : shapesListStore) {
					// sphere file save format is: "sphere" followed by xPosition,
					// yposition,radius,color (all comma seperated)
					if (shape.shape.equals("sphere")) {
						printWriter.println("sphere" + "," + Integer.toString(shape.xPosition) + ","
								+ Integer.toString(shape.yPosition) + "," + Integer.toString(shape.radius) + ","
								+ shape.color+","+Double.toString(shape.scale.getX()));
					} else if (shape.shape.equals("box")) {
						printWriter.println("box" + "," + Integer.toString(shape.xPosition) + ","
								+ Integer.toString(shape.yPosition) + "," + Integer.toString(shape.length) + ","
								+ Integer.toString(shape.width) + "," + Integer.toString(shape.height) + ","
								+ shape.color+","+Double.toString(shape.scale.getX())+","+Double.toString(shape.horizontalAngle)+","
								+Double.toString(shape.verticleAngle)+","+Double.toString(shape.zAngle));
		
					} else if (shape.shape.equals("cylinder")) {
						printWriter.println("cylinder" + "," + Integer.toString(shape.xPosition) + ","
								+ Integer.toString(shape.yPosition) + "," + Integer.toString(shape.radius) + ","
								+ Integer.toString(shape.height) + "," + shape.color+","+Double.toString(shape.scale.getX())+","+Double.toString(shape.horizontalAngle)+","
								+Double.toString(shape.verticleAngle)+","+Double.toString(shape.zAngle));
					}

				}
				printWriter.println("background"+","+selected); //save background color 

				printWriter.close();
			}

			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

	}

	private void addShape(Stage primaryStage, Shape shape) {

	}

	private void createShapeMenu() {
		Label chooseShapeL = new Label("Please select a shape to create");

		ToggleGroup chooseShapeGroup = new ToggleGroup();
		sphereB.setToggleGroup(chooseShapeGroup);
		boxB.setToggleGroup(chooseShapeGroup);
		cylinderB.setToggleGroup(chooseShapeGroup);
		HBox chooseShapeButtons = new HBox(10, sphereB, boxB, cylinderB);
		refCreateShapeWindowStep1.add(chooseShapeL, 0, 0);
		refCreateShapeWindowStep1.add(chooseShapeButtons, 0, 1);
		chooseShapeButtons.setAlignment(Pos.CENTER);
		createShapesMenu = new VBox(10, chooseShapeL, chooseShapeButtons, submitShapeChoice);

		Label sphereL = new Label("Sphere Menu: please keep values between 1-10");
		Label boxL = new Label("Box Menu: please keep values between 1-10");
		Label cylinderL = new Label("Cylinder Menu: please keep values between 1-10");
		Label xPositionL = new Label("X Position: ");
		Label xPositionSphereL = new Label("X Position: ");
		Label xPositionCylinderL = new Label("X Position");
		Label yPositionL = new Label("Y Position: ");
		Label yPositionSphereL = new Label("Y Position: ");
		Label yPositionCylinderL = new Label("Y Position");
		Label sphereRadiusL = new Label("Sphere Radius: ");
		Label cylinderRadiusL = new Label("Cylinder Radius: ");
		Label cylinderHeightL = new Label("Cylinder Height: ");
		Label boxHeightL = new Label("Box Height: ");
		Label boxLengthL = new Label("Box Length: ");
		Label boxWidthL = new Label("Box Width: ");

		createSphereMenu.add(sphereRadiusL, 0, 0);
		createSphereMenu.add(sphereRadius, 1, 0);
		createSphereMenu.add(xPositionSphereL, 0, 1);
		createSphereMenu.add(xPositionSphere, 1, 1);
		createSphereMenu.add(yPositionSphereL, 0, 2);
		createSphereMenu.add(yPositionSphere, 1, 2);
		sphereMenu = new VBox(10, sphereL, createSphereMenu, submitSphere);

		createCylinderMenu.add(cylinderRadiusL, 0, 0);
		createCylinderMenu.add(cylinderRadius, 1, 0);
		createCylinderMenu.add(cylinderHeightL, 0, 1);
		createCylinderMenu.add(cylinderHeight, 1, 1);
		createCylinderMenu.add(xPositionCylinderL, 0, 2);
		createCylinderMenu.add(xPositionCylinder, 1, 2);
		createCylinderMenu.add(yPositionCylinderL, 0, 3);
		createCylinderMenu.add(yPositionCylinder, 1, 3);
		cylinderMenu = new VBox(10, cylinderL, createCylinderMenu, submitCylinder);

		createBoxMenu.add(boxHeightL, 0, 0);
		createBoxMenu.add(boxHeight, 1, 0);
		createBoxMenu.add(boxWidthL, 0, 1);
		createBoxMenu.add(boxWidth, 1, 1);
		createBoxMenu.add(boxLengthL, 0, 2);
		createBoxMenu.add(boxLength, 1, 2);
		createBoxMenu.add(xPositionL, 0, 3);
		createBoxMenu.add(xPositionBox, 1, 3);
		createBoxMenu.add(yPositionL, 0, 4);
		createBoxMenu.add(yPositionBox, 1, 4);
		boxMenu = new VBox(10, boxL, createBoxMenu, submitBox);
	}

	// starts over new
	private void erase() {
		shapesListStore = new ArrayList<Shape>();
		idNumCounter = 1;
		shapes = new Group();
		shapeScene = new SubScene(shapes, 700, 550, true, SceneAntialiasing.DISABLED);
		shapeScene.setFill(Color.LIGHTBLUE);
		camera=new PerspectiveCamera(true);
		camera.getTransforms().addAll(new Translate(0, 0, -100)); //HERE
		shapeScene.setCamera(camera);
		
		scaleSlider=new Slider(0.0,100.0,50.0);
		root.setCenter(shapeScene);
	}
	private void setColor(Shape inputtedShape, String inputtedColor,String shape)
	{
			Color color=Color.WHITE; 
			switch (inputtedColor) {
			case "blue":
				color=Color.ROYALBLUE;
				
				break;
			case "red":
				color=Color.RED;
				break;
			case "pink":
				color=Color.PINK;
				break;
			case "black":
				color=Color.BLACK;
				break;
			case "orange":
				color=Color.ORANGE;
				break;
			case "green":
				color=Color.GREEN;
				break;
			case "purple":
				color=Color.PLUM;
				break;
			}
			if(shape.equals("box"))
			{
				inputtedShape.thisBox.setMaterial(new PhongMaterial(color));
			}
			else if(shape.equals("sphere"))
			{
				inputtedShape.thisSphere.setMaterial(new PhongMaterial(color));
			}
			else if(shape.equals("cylinder"))
			{
				inputtedShape.thisCylinder.setMaterial(new PhongMaterial(color));
			}
	}
	private class Shape {
		String shape = "";
		int xPosition = 0;
		int yPosition = 0;
		
		Rotate xrotate;
		Rotate yrotate;
       	Scale scale;
		String color = "";
		int length = 0, width = 0, height = 0, radius = 0, idNum = 0;
		double horizontalAngle=0,verticleAngle=0,zAngle=0;
		Sphere thisSphere = null;
		Box thisBox = null;
		Cylinder thisCylinder = null;

		public Shape(String shapeType) {
			shape = shapeType;
		}

		public Shape() {
			shape = "void";
		}
	}

	// will only allow textfields to input digits only if necessary
	class changeTextListener implements ChangeListener<String> {
		TextField field;

		public changeTextListener(TextField textField) {
			field = textField;
		}

		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			if (!newValue.matches("\\d*")) {
				field.setText(newValue.replaceAll("[^\\d]", ""));
			}

		}
	}

}
