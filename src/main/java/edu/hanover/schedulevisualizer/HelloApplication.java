package edu.hanover.schedulevisualizer;

import edu.hanover.schedulevisualizer.core.Context;
import edu.hanover.schedulevisualizer.ui.App;
import edu.hanover.schedulevisualizer.ui.draganddrop.DragAndDropController;
import edu.hanover.schedulevisualizer.ui.draganddrop.DropExecutor;
import edu.hanover.schedulevisualizer.ui.draganddrop.DropTarget;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private final App app = new App();

    @Override
    public void start(final Stage stage) throws IOException {
        stage.getIcons().add(new Image(HelloApplication.class.getResourceAsStream("icon.png")));
        app.startApp(stage);
        final Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                Context.getInstance().getData();
            } catch (InterruptedException ignored) {
                /*
                 * Logging potentially necessary here.
                 */
            }
        });
        thread.start();
    }

    public static void main(final String[] args) {
        DragAndDropController.getInstance().setDropExecutor(new DropExecutor() {
            // Add methods to call on Context.getInstance()
            public boolean executeTheDrop(final DropTarget target, final Dragboard db) {
                final Long courseId = Long.valueOf(db.getString());
                final String timeslotId = target.getTimeslotId();
                System.out.println("Hi!");
                Context.getInstance().assignTimeslot(courseId, timeslotId);
                return true;
            }

        });1
        launch();
    }
}