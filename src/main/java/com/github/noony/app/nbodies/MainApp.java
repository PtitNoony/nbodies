package com.github.noony.app.nbodies;

import com.github.noony.app.nbodies.fx.ControlLayer;
import com.github.noony.app.nbodies.fx.ExecutionController;
import com.github.noony.app.nbodies.fx.SolarSystem2D;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static final double DEFAULT_WIDTH = 1600;
    public static final double DEFAULT_HEIGHT = 900;

    private double width = DEFAULT_WIDTH;
    private double height = DEFAULT_HEIGHT;

    private SolarSystem solarSystem;

    //
    private SolarSystem2D solarSystem2D;
    private ControlLayer controlLayer;

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, width, height, Color.BLACK);
        scene.getStylesheets().add("/styles/Styles.css");
        //

        GlobalClock globalClock = new GlobalClock();
        solarSystem = new SolarSystem("My Testing Solar System");
        solarSystem.setGlobalClock(globalClock);
        //
        solarSystem2D = new SolarSystem2D();
        solarSystem2D.setSolarSystem(solarSystem);
        //
        controlLayer = new ControlLayer();
        ExecutionController executionController = new ExecutionController(globalClock);

        controlLayer.addController(executionController);
        //
        // mini map
        //

        root.getChildren().addAll(solarSystem2D.getNode(), controlLayer.getNode());
        //
//        Body sun = new Body("Sun", 333000, 2, -0.5, -12000, Color.GOLD);
//        Body sun2 = new Body("Sun2", 333000, 2, 0.5, 12000, Color.RED);
        Body sun = new Body("Sun", 333000, 2, 0, 0, Color.GOLD);
        Body mercure = new Body("Mercure", 0.055, 0.38295, 0.4, 47362, Color.GOLDENROD);
        Body venus = new Body("Venus", 0.815, 0.9499, 0.7, 35020, Color.ANTIQUEWHITE);
        Body earth = new Body("Earth", 1, 1, 1.1, 29782, Color.DEEPSKYBLUE);
        Body mars = new Body("Mars", 0.107, 0.533, 1.5, 24077, Color.ORANGERED);
        solarSystem.addBody(sun);
//        solarSystem.addBody(sun2);
        solarSystem.addBody(mercure);
        solarSystem.addBody(venus);
        solarSystem.addBody(earth);
        solarSystem.addBody(mars);
        //
        scene.widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            // TODO: LOG
            width = newValue.doubleValue();
            updateSize();
        });
        scene.heightProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            // TODO: LOG
            height = newValue.doubleValue();
            updateSize();
        });

        stage.setTitle("Simple solar system creator");
        stage.setScene(scene);
        stage.show();

        Point3D a = new Point3D(10, 0, 0);
        Point3D b = new Point3D(0, 0, 0);

        Point3D ab = new Point3D(a.getX() - b.getX(), a.getY() - b.getY(), a.getZ() - b.getZ());

        Point3D abN = ab.normalize();
        //
        Point3D x = new Point3D(1, 0, 0);
        Point3D y = new Point3D(0, 1, 0);
        Point3D z = new Point3D(0, 0, 1);

//        System.err.println(" scalar x: "+abN.dotProduct(x));
//        System.err.println(" scalar y: "+abN.dotProduct(y));
//        System.err.println(" scalar z: "+abN.dotProduct(z));
//
//        BigDecimal four = BigDecimal.valueOf(4);
//        System.err.println(" four = "+BigIntegerMath.sqrt(four.toBigInteger(), RoundingMode.DOWN));
//        System.err.println(" sqrt -> "+four.pow(-2));
//        BigInteger i = new BigInteger("10000");
////        System.err.println(" > "i.multiply(Bigin));
//        BigDecimal d = new BigDecimal(-1234556789);
//        BigDecimal d2 = new BigDecimal(1234556789);
//        System.err.println(" + "+d.add(d));
//        System.err.println(" - "+d.subtract(d2));
//
    }

    private void updateSize() {
        solarSystem2D.updateSize(width, height);
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application. main() serves only as fallback in case the
     * application can not be launched through deployment artifacts, e.g., in IDEs with limited FX support. NetBeans
     * ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
