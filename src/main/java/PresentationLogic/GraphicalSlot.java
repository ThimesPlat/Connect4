package PresentationLogic;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by cte on 2017-06-13.
 */

public class GraphicalSlot {

    private Circle c;
    private Color color;

    public GraphicalSlot(double radius, Color color){

        this.c = new Circle();
        this.c.setRadius(radius);
        this.c.setFill(color);
        this.color = color;
    }

    public Circle getSlot(){
        return c;
    }

    public void setColor(Color color) {
        this.color = color;
        c.setFill(color);
    }


}
