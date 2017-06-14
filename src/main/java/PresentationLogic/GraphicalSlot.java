package PresentationLogic;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by cte on 2017-06-13.
 */

public class GraphicalSlot {

    Circle c;
    Color color;

    public GraphicalSlot(double radius, Color color){

        this.c = new Circle();
        this.c.setRadius(radius);
        this.c.setFill(color);
        this.color = color;
    }

    public Circle getSlot(){
        return c;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
        c.setFill(color);
    }


}
