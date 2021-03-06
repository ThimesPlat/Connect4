package PresentationLogic;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Created by cte on 2017-06-11.
 */
public class GraphicalBoard {

    private Pane layout;
    private Rectangle r;
    private GraphicalSlot[][] slots = new GraphicalSlot[6][7];

    public GraphicalBoard(Pane layout, double width, double height){
        this.layout = layout;
        this.r = new Rectangle(width, height);
        r.setHeight(height);
        r.setWidth(width);
        r.setFill(Color.BLUE);

        this.layout.getChildren().add(r);
        double ringSize = 42;
        double spacingX = (width-(ringSize*8))/6;
        double spacingY = (height-(ringSize*8))/8;
        double yStartingPoint = height-(ringSize*6*2)-20;
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

    public void clearBoard(){
        for (int i = 0;i<6;i++){
            for (int q = 0;q<7;q++){
                slots[i][q].setColor(Color.grayRgb(240));
            }

        }
    }

}
