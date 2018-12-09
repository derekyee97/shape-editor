/***************************************************
*
*   @authors: Kimberlyn Krishnan, Kim Ngan Nguyen, Derek Yee
*   @Assignment: Homework #4 Group Shape Editor
*   @Date Due: 12/13/18
***************************************************
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
	Button test = new Button("test");

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

	FileChooser fileChooser = new FileChooser();
	Scanner fileReader;
	PrintWriter printWriter;
	FileWriter fileWriter;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		createShapeMenu();
		// emanuel rob
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
		camera.getTransforms().addAll(new Translate(0, 0, -100));
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
			String selected = backgroundColorList.getSelectionModel().getSelectedItem();
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
			String selected2 = shapeColorList.getSelectionModel().getSelectedItem();
			if (selected2 != null) {
				if ((selectedShape.shape).equals("sphere")) {
					switch (selected2) {
					case "blue":
						selectedShape.thisSphere.setMaterial(new PhongMaterial(Color.ROYALBLUE));
						selectedShape.color = Color.ROYALBLUE;
						break;
					case "red":
						selectedShape.thisSphere.setMaterial(new PhongMaterial(Color.RED));
						selectedShape.color = Color.RED;
						break;
					case "pink":
						selectedShape.thisSphere.setMaterial(new PhongMaterial(Color.PINK));
						selectedShape.color = Color.PINK;
						break;
					case "black":
						selectedShape.thisSphere.setMaterial(new PhongMaterial(Color.BLACK));
						selectedShape.color = Color.BLACK;
						break;
					case "orange":
						selectedShape.thisSphere.setMaterial(new PhongMaterial(Color.ORANGE));
						selectedShape.color = Color.ORANGE;
						break;
					case "green":
						selectedShape.thisSphere.setMaterial(new PhongMaterial(Color.GREEN));
						selectedShape.color = Color.GREEN;
						break;
					case "purple":
						selectedShape.thisSphere.setMaterial(new PhongMaterial(Color.PLUM));
						selectedShape.color = Color.PLUM;
						break;
					}
				}
				if ((selectedShape.shape).equals("cylinder")) {
					switch (selected2) {
					case "blue":
						selectedShape.thisCylinder.setMaterial(new PhongMaterial(Color.ROYALBLUE));
						selectedShape.color = Color.ROYALBLUE;
						break;
					case "red":
						selectedShape.thisCylinder.setMaterial(new PhongMaterial(Color.RED));
						selectedShape.color = Color.RED;
						break;
					case "pink":
						selectedShape.thisCylinder.setMaterial(new PhongMaterial(Color.PINK));
						selectedShape.color = Color.PINK;
						break;
					case "black":
						selectedShape.thisCylinder.setMaterial(new PhongMaterial(Color.BLACK));
						selectedShape.color = Color.BLACK;
						break;
					case "orange":
						selectedShape.thisCylinder.setMaterial(new PhongMaterial(Color.ORANGE));
						selectedShape.color = Color.ORANGE;
						break;
					case "green":
						selectedShape.thisCylinder.setMaterial(new PhongMaterial(Color.GREEN));
						selectedShape.color = Color.GREEN;
						break;
					case "purple":
						selectedShape.thisCylinder.setMaterial(new PhongMaterial(Color.PLUM));
						selectedShape.color = Color.PLUM;
						break;
					}
				}
				if ((selectedShape.shape).equals("box")) {
					switch (selected2) {
					case "blue":
						selectedShape.thisBox.setMaterial(new PhongMaterial(Color.ROYALBLUE));
						selectedShape.color = Color.ROYALBLUE;
						break;
					case "red":
						selectedShape.thisBox.setMaterial(new PhongMaterial(Color.RED));
						selectedShape.color = Color.RED;
						break;
					case "pink":
						selectedShape.thisBox.setMaterial(new PhongMaterial(Color.PINK));
						selectedShape.color = Color.PINK;
						break;
					case "black":
						selectedShape.thisBox.setMaterial(new PhongMaterial(Color.BLACK));
						selectedShape.color = Color.BLACK;
						break;
					case "orange":
						selectedShape.thisBox.setMaterial(new PhongMaterial(Color.ORANGE));
						selectedShape.color = Color.ORANGE;
						break;
					case "green":
						selectedShape.thisBox.setMaterial(new PhongMaterial(Color.GREEN));
						selectedShape.color = Color.GREEN;
						break;
					case "purple":
						selectedShape.thisBox.setMaterial(new PhongMaterial(Color.PLUM));
						selectedShape.color = Color.PLUM;
						break;
					}
				}

			}
		});

		Label rotateLabel = new Label("Slide to rotate");
		Slider rotateSlider = new Slider();
		rotateSlider.setMin(0);
		rotateSlider.setMax(360);
		rotateSlider.setMajorTickUnit(10);
		rotateSlider.setMinorTickCount(5);
		rotateSlider.setBlockIncrement(10);
		// rotateSlider.setShowTickLabels(true);

		Label leftRightLabel = new Label("Move shape left/right");
		HBox hbox1 = new HBox(10, left, right);
		hbox1.setAlignment(Pos.CENTER);

		left.setOnAction(event -> {
			if ((selectedShape.shape).equals("box")) {
				selectedShape.xPosition = selectedShape.xPosition - 1;
				selectedShape.thisBox.setTranslateX(selectedShape.xPosition);
			}
			if ((selectedShape.shape).equals("cylinder")) {
				selectedShape.xPosition = selectedShape.xPosition - 1;
				selectedShape.thisCylinder.setTranslateX(selectedShape.xPosition);
			}
			if ((selectedShape.shape).equals("sphere")) {
				selectedShape.xPosition = selectedShape.xPosition - 1;
				selectedShape.thisSphere.setTranslateX(selectedShape.xPosition);
			}
		});

		right.setOnAction(event -> {
			if ((selectedShape.shape).equals("box")) {
				selectedShape.xPosition = selectedShape.xPosition + 1;
				selectedShape.thisBox.setTranslateX(selectedShape.xPosition);
			}
			if ((selectedShape.shape).equals("cylinder")) {
				selectedShape.xPosition = selectedShape.xPosition + 1;
				selectedShape.thisCylinder.setTranslateX(selectedShape.xPosition);
			}
			if ((selectedShape.shape).equals("sphere")) {
				selectedShape.xPosition = selectedShape.xPosition + 1;
				selectedShape.thisSphere.setTranslateX(selectedShape.xPosition);
			}
		});

		up.setOnAction(event -> {
			if ((selectedShape.shape).equals("box")) {
				selectedShape.yPosition = selectedShape.yPosition - 1;
				selectedShape.thisBox.setTranslateY(selectedShape.yPosition);
			}
			if ((selectedShape.shape).equals("cylinder")) {
				selectedShape.yPosition = selectedShape.yPosition - 1;
				selectedShape.thisCylinder.setTranslateY(selectedShape.yPosition);
			}
			if ((selectedShape.shape).equals("sphere")) {
				selectedShape.yPosition = selectedShape.yPosition - 1;
				selectedShape.thisSphere.setTranslateY(selectedShape.yPosition);
			}
		});

		down.setOnAction(event -> {
			if ((selectedShape.shape).equals("box")) {
				selectedShape.yPosition = selectedShape.yPosition + 1;
				selectedShape.thisBox.setTranslateY(selectedShape.yPosition);
			}
			if ((selectedShape.shape).equals("cylinder")) {
				selectedShape.yPosition = selectedShape.yPosition + 1;
				selectedShape.thisCylinder.setTranslateY(selectedShape.yPosition);
			}
			if ((selectedShape.shape).equals("sphere")) {
				selectedShape.yPosition = selectedShape.yPosition + 1;
				selectedShape.thisSphere.setTranslateY(selectedShape.yPosition);
			}
		});
		test.setOnAction(event -> {
			System.out.println("shape: " + selectedShape.shape);
			System.out.println("id: " + selectedShape.idNum);
			System.out.println("Color: " + selectedShape.color);
			System.out.println("X: " + selectedShape.xPosition);
			System.out.println("Y: " + selectedShape.yPosition);
//            	   System.out.println("X Rotate: "+selectedShape.xrotate.toString());
//            	   System.out.println("Y Rotate: "+selectedShape.yrotate.toString());
//            	   System.out.println("Z Rotate: "+selectedShape.zrotate.toString());
		});

		Label upDownLabel = new Label("Move shape up/down");
		HBox hbox2 = new HBox(10, up, down);
		hbox2.setAlignment(Pos.CENTER);
		Label scaleLabel = new Label("Slide to change scale ");
		Slider scaleSlider = new Slider();
		scaleSlider.setMin(0);
		scaleSlider.setMajorTickUnit(10);
		scaleSlider.setMinorTickCount(5);
		scaleSlider.setBlockIncrement(10);
		VBox rightMenu = new VBox(10, BchangeColor, backgroundColorList, SchangeColor, shapeColorList, leftRightLabel,
				hbox1, upDownLabel, hbox2, rotateLabel, rotateSlider, scaleLabel, scaleSlider);

		rightMenu.setPadding(new Insets(10));

		root.setPadding(new Insets(0, 35, 20, 0));
		root.setCenter(shapeScene);
		root.setTop(menuBar);
		root.setRight(rightMenu);
		HBox center = new HBox(10, addShapeButton);
		center.setAlignment(Pos.CENTER);
		root.setBottom(center);
		root.setLeft(test);

		Scene scene = new Scene(root, 1000, 650);
		primaryStage.setScene(scene);
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
			Sphere createdSphere = new Sphere(creatingSphere.radius);
			createdSphere.setId(Integer.toString(creatingSphere.idNum));
			int spherex = Integer.parseInt(xPositionSphere.getText());
			int spherey = Integer.parseInt(yPositionSphere.getText());
			createdSphere.getTransforms().add(new Translate(spherex, -spherey, 0));
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
			idNumCounter = idNumCounter + 1;
			Box createdBox = new Box(creatingBox.width, creatingBox.height, creatingBox.length);

			Rotate rotateX = new Rotate(45, Rotate.X_AXIS);
			createdBox.getTransforms().addAll(rotateX);
			int boxx = Integer.parseInt(xPositionBox.getText());
			int boxy = Integer.parseInt(yPositionBox.getText());
			createdBox.getTransforms().add(new Translate(boxx, -boxy, 0));
			createdBox.setId(Integer.toString(creatingBox.idNum));
			createdBox.setOnMouseClicked(ActionEvent -> {
				selectedShape = creatingBox;
				selectedShape.xPosition = boxx;
				selectedShape.yPosition = -boxy;
			});
//	    	   Rotate rotate=new Rotate(45,Rotate.X_AXIS);
//	    	   createdBox.getTransforms().addAll(rotate);
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
			idNumCounter++;
			Cylinder createdCylinder = new Cylinder(creatingCylinder.radius, creatingCylinder.height);
			// test z rotate
			Rotate rotateX = new Rotate(45, Rotate.X_AXIS);
			createdCylinder.getTransforms().addAll(rotateX);
			int testx = Integer.parseInt(xPositionCylinder.getText());
			int testy = Integer.parseInt(yPositionCylinder.getText());
			createdCylinder.getTransforms().add(new Translate(testx, -testy, 0));
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
					System.out.println(tokens[0]);
				}
				fileReader.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
								+ shape.color);
					} else if (shape.shape.equals("box")) {
						printWriter.println("box" + "," + Integer.toString(shape.xPosition) + ","
								+ Integer.toString(shape.yPosition) + "," + Integer.toString(shape.length) + ","
								+ Integer.toString(shape.width) + "," + Integer.toString(shape.height) + ","
								+ shape.color);
					} else if (shape.shape.equals("cylinder")) {
						printWriter.println("cylinder" + "," + Integer.toString(shape.xPosition) + ","
								+ Integer.toString(shape.yPosition) + "," + Integer.toString(shape.radius) + ","
								+ Integer.toString(shape.height) + "," + shape.color);
					}

				}

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

		Label sphereL = new Label("Sphere Menu");
		Label boxL = new Label("Box Menu");
		Label cylinderL = new Label("Cylinder Menu");
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

		root.setCenter(shapeScene);
	}

	private class Shape {
		String shape = "";
		int xPosition = 0;
		int yPosition = 0;
		Rotate zrotate;
		Rotate xrotate;
		Rotate yrotate;
		Color color = null;
		int length = 0, width = 0, height = 0, radius = 0, idNum = 0;
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
