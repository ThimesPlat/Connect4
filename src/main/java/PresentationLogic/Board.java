package PresentationLogic;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Created by cte on 2017-06-11.
 */
public class Board {

    Pane layout;
    Rectangle r;
    GraphicalSlot[][] slots = new GraphicalSlot[6][7];

    public Board(Pane layout, int width, int height){

        this.layout = layout;
        this.r = new Rectangle(width, height);
        r.setHeight(height);
        r.setWidth(width);
        r.setFill(Color.BLUE);


        this.layout.getChildren().add(r);
        double ringSize = 42;
        double spacingX = (width-(ringSize*8))/6;
        double spacingY = (height-(ringSize*8))/6;
        double yStartingPoint = height-(ringSize*6*2)-10;
        for (int i = 0;i<6;i++){
            for (int q = 0;q<7;q++){
                GraphicalSlot s = new GraphicalSlot(ringSize,Color.grayRgb(240));
                Circle c = s.getSlot();
                c.setLayoutY((((ringSize+spacingY)*i)+ringSize)+yStartingPoint);
                c.setLayoutX((((ringSize+spacingX)*q)+ringSize));
                slots[i][q] = s;
                this.layout.getChildren().add(c);
            }

        }


    }

    public Pane getBoardLayout(){
        return this.layout;
    }

    public void changeCircleColor(int row,int col,Color color){
        slots[row][col].setColor(color);
    }

    public GraphicalSlot[][] getSlots(){
        return slots;
    }


}
