package com.katyshevtseva.fx.dialogconstructor;

import com.katyshevtseva.fx.WindowBuilder;
import com.katyshevtseva.general.NoArgsKnob;

import java.util.Arrays;

public class DialogConstructor {

    public static void constructDialog(NoArgsKnob onConfirmHandler, DcElement... elements) {
        constructDialog(onConfirmHandler, null, elements);
    }

    public static void constructDialog(NoArgsKnob onConfirmHandler, Integer height, DcElement... elements) {
        DialogController controller = new DialogController(Arrays.asList(elements), onConfirmHandler, height);

        new WindowBuilder("/com/katyshevtseva/fx/dialogconstructor/container.fxml")
                .setSize(controller.getWindowSize()).setController(controller).showWindow();
    }

}
