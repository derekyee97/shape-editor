import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Line;
public class CS2450_Homework_4 extends Application
{
		//global variables 
		Shape selectedShape=null;
		List<Shape> shapesListStore=new ArrayList<>(); 
		BorderPane root=new BorderPane(); 
		Group shapes=new Group();
	    SubScene shapeScene=new SubScene(shapes,700,550,true,SceneAntialiasing.DISABLED);
	    PerspectiveCamera camera=new PerspectiveCamera(true);
	    
	    int idNumCounter=1; //will give unique id number to each shape we create
	    
	    GridPane refCreateShapeWindowStep1=new GridPane();
	    GridPane createSphereMenu=new GridPane();
	    GridPane createCylinderMenu=new GridPane();
	    GridPane createBoxMenu=new GridPane();
	    
	    Button submitShapeChoice=new Button("Submit");
	    Button submitSphere=new Button("Submit");
	    Button submitCylinder=new Button("Submit");
	    Button submitBox=new Button("Submit");
	    Button addShapeButton=new Button("Add Shape");
	    Button testButton=new Button("Test");
	    
	    RadioButton sphereB=new RadioButton("Sphere");
	    RadioButton boxB=new RadioButton("Box");
	    RadioButton cylinderB=new RadioButton("Cylinder");
	    
	    TextField xPositionSphere=new TextField("Please enter an integer to place shape in the x direction");
	    TextField xPositionCylinder=new TextField("Please enter an integer to place shape in the x direction");
	    TextField xPositionBox=new TextField("Please enter an integer to place shape in the x direction");   
    	TextField yPositionBox=new TextField("Please enter an integer to place shape in the y direction");
    	TextField yPositionSphere=new TextField("Please enter an integer to place shape in the y direction");
    	TextField yPositionCylinder=new TextField("Please enter an integer to place shape in the y direction");
    	TextField sphereRadius=new TextField("Please enter an integer for sphere radius");
    	TextField cylinderRadius=new TextField("Please enter an integer for cylinder radius");
    	TextField cylinderHeight=new TextField("Please enter an integer for cylinder height");
    	TextField boxHeight=new TextField("Please enter an integer for box height");
    	TextField boxLength=new TextField("Please enter an integer for box length");
    	TextField boxWidth=new TextField("Please enter an integer for box width");
	    
	    VBox createShapesMenu; 
	    VBox sphereMenu;
	    VBox boxMenu;
	    VBox cylinderMenu;
	    
	    
		public static void main(String[] args) 
	    {
	        launch(args);
	    }
	    public void start(Stage primaryStage) 
	    {
	       createShapeMenu();
	       //emanuel rob
	       createShapesMenu.setAlignment(Pos.CENTER);sphereMenu.setAlignment(Pos.CENTER);
	       boxMenu.setAlignment(Pos.CENTER);cylinderMenu.setAlignment(Pos.CENTER);
	       refCreateShapeWindowStep1.setAlignment(Pos.CENTER); createSphereMenu.setAlignment(Pos.CENTER);
	       createBoxMenu.setAlignment(Pos.CENTER);createCylinderMenu.setAlignment(Pos.CENTER);
	       camera.getTransforms().addAll(new Translate(0,0,-100));
	       shapeScene.setCamera(camera);
	       
	       xPositionBox.textProperty().addListener(new changeTextListener(xPositionBox));yPositionBox.textProperty().addListener(new changeTextListener(yPositionBox));
	       xPositionSphere.textProperty().addListener(new changeTextListener(xPositionSphere));yPositionSphere.textProperty().addListener(new changeTextListener(yPositionSphere));
	       xPositionCylinder.textProperty().addListener(new changeTextListener(xPositionCylinder));yPositionCylinder.textProperty().addListener(new changeTextListener(yPositionCylinder));
	       sphereRadius.textProperty().addListener(new changeTextListener(sphereRadius));
	       cylinderRadius.textProperty().addListener(new changeTextListener(cylinderRadius)); cylinderHeight.textProperty().addListener(new changeTextListener(cylinderHeight));
	       boxHeight.textProperty().addListener(new changeTextListener(boxHeight)); boxWidth.textProperty().addListener(new changeTextListener(boxWidth)); boxLength.textProperty().addListener(new changeTextListener(boxLength));
	       
	       
	       MenuBar menuBar=new MenuBar();
	       Menu fileChoice=new Menu("File");
	       menuBar.getMenus().add(fileChoice);
	       MenuItem saveChoice=new MenuItem("Save");
	       MenuItem openChoice=new MenuItem("Open");
	       fileChoice.getItems().addAll(saveChoice,openChoice);
	       
	       shapeScene.setFill(Color.LIGHTBLUE);
	       
	       ListView<String> colorList=new ListView();
	       Label changeColor =new Label("Select Color");
	       colorList.getItems().addAll("blue","red","pink","black","orange","green","purple");
	       colorList.setPrefSize(120,150);
	       Label rotateLabel=new Label("Slide to rotate");
	       Slider rotateSlider=new Slider();
	       rotateSlider.setMin(0);rotateSlider.setMax(360);
	       rotateSlider.setMajorTickUnit(10);
	       rotateSlider.setMinorTickCount(5);
	       rotateSlider.setBlockIncrement(10);
	       //rotateSlider.setShowTickLabels(true);
	       Label leftRightLabel=new Label("Slide to slide shape to left/right");
	       Slider leftRightSlider=new Slider();
	       leftRightSlider.setMin(0);
	       leftRightSlider.setMajorTickUnit(10);leftRightSlider.setMinorTickCount(5);
	       leftRightSlider.setBlockIncrement(10);
	       Label upDownLabel=new Label("Slide to slide shape to up/down");
	       Slider upDownSlider=new Slider();
	       upDownSlider.setMin(0);
	       upDownSlider.setMajorTickUnit(10);leftRightSlider.setMinorTickCount(5);
	       upDownSlider.setBlockIncrement(10);      
	       Label scaleLabel=new Label("Slide to change scale ");
	       Slider scaleSlider=new Slider();
	       scaleSlider.setMin(0);
	       scaleSlider.setMajorTickUnit(10);leftRightSlider.setMinorTickCount(5);
	       scaleSlider.setBlockIncrement(10);
	       VBox rightMenu=new VBox(10,changeColor,colorList,rotateLabel,rotateSlider,leftRightLabel,leftRightSlider,upDownLabel,upDownSlider,scaleLabel,scaleSlider);
	       
	       
	       
	       rightMenu.setPadding(new Insets(10));
	       
	       
	       root.setCenter(shapeScene);
	       root.setTop(menuBar);
	       root.setRight(rightMenu);
	       root.setBottom(addShapeButton);
	       root.setLeft(testButton);
	       
	       
	    	
	    	
	    	
	       Scene scene = new Scene(root,1000,650);
	       primaryStage.setScene(scene);
	       primaryStage.show();
	       
	      
	       //HANDLING BUTTON CLICKS
	       
	       //handling opening a file to display shapes saved
	       openChoice.setOnAction(event->
	       {
	    	   
	       });
	      
	       addShapeButton.setOnAction(event->
	       {
	    	   root.setCenter(createShapesMenu);
	       });
	       testButton.setOnAction(event->{
	    	   System.out.println(selectedShape.shape);
	       });
	       
	       //when user selects shape they want to create
	       submitShapeChoice.setOnAction(event->{
	    	   
	    	   
//	    	   for(Shape x:shapesListStore)
//	    	   {
//	    		   System.out.println(x.shape);
//	    	   }
	    	   if(sphereB.isSelected())
	    	   {
	    		   root.setCenter(sphereMenu);
	    	   }
	    	   else if(boxB.isSelected()) 
	    	   {
	    		   root.setCenter(boxMenu);
	    	   }
	    	   else if(cylinderB.isSelected()) 
	    	   {
	    		   root.setCenter(cylinderMenu);
	    	   }
	       });
	       //HANDLING SPHERE CREATION
	       submitSphere.setOnAction(event->
	       {
	    	   Shape creatingSphere=new Shape("sphere");
	    	   creatingSphere.xPosition=Integer.parseInt(xPositionSphere.getText());
	    	   creatingSphere.yPosition=Integer.parseInt(yPositionSphere.getText());
	    	   creatingSphere.radius=Integer.parseInt(sphereRadius.getText());
	    	   creatingSphere.idNum=idNumCounter;
	    	   idNumCounter=idNumCounter+1;   
	    	   Sphere createdSphere=new Sphere(creatingSphere.radius);
	    	   createdSphere.setId(Integer.toString(creatingSphere.idNum));
	    	   createdSphere.setOnMouseClicked(ActionEvent->
	    	   {
	    		   selectedShape=creatingSphere;
	    	   });
//	    	   Translate translator=new Translate(10,0,0);
//	    	   createdSphere.getTransforms().add(translator);
	    	   shapes.getChildren().addAll(createdSphere);
	    	   creatingSphere.thisSphere=createdSphere;
	    	   root.setCenter(shapeScene);
	    	   shapesListStore.add(creatingSphere);
	    	   
	       });
	       submitBox.setOnAction(event->
	       {
	    	   Shape creatingBox=new Shape("box");
	    	   creatingBox.xPosition=Integer.parseInt(xPositionBox.getText());
	    	   creatingBox.yPosition=Integer.parseInt(yPositionBox.getText());
	    	   creatingBox.length=Integer.parseInt(boxLength.getText());
	    	   creatingBox.width=Integer.parseInt(boxWidth.getText());
	    	   creatingBox.height=Integer.parseInt(boxHeight.getText());
	    	   creatingBox.idNum=idNumCounter;
	    	   idNumCounter=idNumCounter+1;
	    	   Box createdBox=new Box(creatingBox.width,creatingBox.height,creatingBox.length);
	    	   createdBox.setId(Integer.toString(creatingBox.idNum));
	    	   createdBox.setOnMouseClicked(ActionEvent->{
	    		   selectedShape=creatingBox;
	    	   });
//	    	   Rotate rotate=new Rotate(45,Rotate.X_AXIS);
//	    	   createdBox.getTransforms().addAll(rotate);
	    	   shapes.getChildren().addAll(createdBox);
	    	   root.setCenter(shapeScene);
	    	   creatingBox.thisBox=createdBox;
	    	   shapesListStore.add(creatingBox);
	    	   
	       });
	       submitCylinder.setOnAction(event->{
	    	   Shape creatingCylinder=new Shape("cylinder");
	    	   creatingCylinder.xPosition=Integer.parseInt(xPositionCylinder.getText());
	    	   creatingCylinder.yPosition=Integer.parseInt(yPositionCylinder.getText());
	    	   creatingCylinder.radius=Integer.parseInt(cylinderRadius.getText());
	    	   creatingCylinder.height=Integer.parseInt(cylinderHeight.getText());
	    	   creatingCylinder.idNum=idNumCounter;
	    	   idNumCounter++;
	    	   Cylinder createdCylinder=new Cylinder(creatingCylinder.radius,creatingCylinder.height);
	    	   createdCylinder.setId(Integer.toString(creatingCylinder.idNum));
	    	   createdCylinder.setOnMouseClicked(ActionEvent->{
	    		  selectedShape=creatingCylinder; 
	    	   });
	    	   shapes.getChildren().addAll(createdCylinder);
	    	   root.setCenter(shapeScene);
	    	   creatingCylinder.thisCylinder=createdCylinder;
	    	   shapesListStore.add(creatingCylinder);
	       });
	       
	       
	       
	    }
	    private void addShape(Stage primaryStage, Shape shape)
	    {
	    	
	    	
	    }
	    
	    private void createShapeMenu()
	    {
	    	Label chooseShapeL=new Label("Please select a shape to create");
	    	
	    	ToggleGroup chooseShapeGroup= new ToggleGroup();
	    	sphereB.setToggleGroup(chooseShapeGroup);boxB.setToggleGroup(chooseShapeGroup); cylinderB.setToggleGroup(chooseShapeGroup);
	    	HBox chooseShapeButtons=new HBox(10,sphereB,boxB,cylinderB);
	    	refCreateShapeWindowStep1.add(chooseShapeL, 0, 0);refCreateShapeWindowStep1.add(chooseShapeButtons, 0, 1);
	    	chooseShapeButtons.setAlignment(Pos.CENTER);
	    	createShapesMenu=new VBox(10,chooseShapeL,chooseShapeButtons,submitShapeChoice);
	    	
	    	
	    	Label sphereL=new Label("Sphere Menu");Label boxL=new Label("Box Menu"); Label cylinderL=new Label("Cylinder Menu");
	    	
	    	Label xPositionL=new Label("X Position: ");Label xPositionSphereL=new Label("X Position: ");Label xPositionCylinderL=new Label("X Position");
	    	Label yPositionL=new Label("Y Position: ");Label yPositionSphereL=new Label("Y Position: ");Label yPositionCylinderL=new Label("Y Position");
	    	Label sphereRadiusL=new Label("Sphere Radius: "); 
	    	Label cylinderRadiusL=new Label("Cylinder Radius: "); Label cylinderHeightL=new Label("Cylinder Height: ");
	    	Label boxHeightL=new Label("Box Height: "); Label boxLengthL=new Label("Box Length: "); Label boxWidthL=new Label("Box Width: ");
	    	
	    	createSphereMenu.add(sphereRadiusL, 0, 0);createSphereMenu.add(sphereRadius, 1, 0);
	    	createSphereMenu.add(xPositionSphereL, 0, 1);createSphereMenu.add(xPositionSphere, 1, 1);
	    	createSphereMenu.add(yPositionSphereL, 0, 2);createSphereMenu.add(yPositionSphere, 1, 2);
	    	sphereMenu=new VBox(10,sphereL,createSphereMenu,submitSphere);
	    	
	    	createCylinderMenu.add(cylinderRadiusL, 0, 0);createCylinderMenu.add(cylinderRadius, 1, 0);
	    	createCylinderMenu.add(cylinderHeightL, 0, 1);createCylinderMenu.add(cylinderHeight, 1, 1);
	    	createCylinderMenu.add(xPositionCylinderL, 0, 2);createCylinderMenu.add(xPositionCylinder, 1, 2);
	    	createCylinderMenu.add(yPositionCylinderL, 0, 3);createCylinderMenu.add(yPositionCylinder, 1, 3);
	    	cylinderMenu=new VBox(10,cylinderL,createCylinderMenu,submitCylinder);
	    	
	    	createBoxMenu.add(boxHeightL, 0, 0);createBoxMenu.add(boxHeight, 1, 0);
	    	createBoxMenu.add(boxWidthL, 0, 1);createBoxMenu.add(boxWidth, 1, 1);
	    	createBoxMenu.add(boxLengthL, 0, 2);createBoxMenu.add(boxLength, 1, 2);
	    	createBoxMenu.add(xPositionL, 0, 3);createBoxMenu.add(xPositionBox, 1, 3);
	    	createBoxMenu.add(yPositionL, 0, 4);createBoxMenu.add(yPositionBox, 1, 4);
	    	boxMenu=new VBox(10,boxL,createBoxMenu,submitBox);
	    	
	    	
	    	
	    }

	    
private class Shape
{
	String shape="";
	int xPosition;
	int yPosition;
	String color="";
	int length=0,width=0,height=0,radius=0,idNum=0;
	Sphere thisSphere=null;
	Box thisBox=null;
	Cylinder thisCylinder=null;
	public Shape(String shapeType)
	{
		shape=shapeType;
	}
	public Shape()
	{
		shape="void";
	}
	
	
	
}

//will only allow textfields to input digits only if necessary 
class changeTextListener implements ChangeListener<String>
{
	TextField field;
	public changeTextListener(TextField textField)
	{
		field=textField;
	}
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
	{
		if (!newValue.matches("\\d*")) 
		{
          field.setText(newValue.replaceAll("[^\\d]", ""));
        }
		
	}
	
}

}
