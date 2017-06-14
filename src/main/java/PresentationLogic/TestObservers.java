package PresentationLogic;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cte on 2017-06-13.
 */
public class TestObservers extends Observable {

    BooleanProperty i = new SimpleBooleanProperty(true);

    public void hejsan(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    i.set(!i.get());
                });
            }
        }, 100, 100);
    }


}
