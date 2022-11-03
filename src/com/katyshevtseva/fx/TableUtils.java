package com.katyshevtseva.fx;

import com.katyshevtseva.general.OneArgKnob;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class TableUtils {

    public static <T> void adjustButtonColumn(
            TableColumn<T, Void> column,
            String buttonText,
            OneArgKnob<T> buttonClickHandler) {
        adjustButtonColumn(column, buttonText, buttonClickHandler, null);
    }

    public static <T> void adjustButtonColumn(
            TableColumn<T, Void> column,
            String buttonText,
            OneArgKnob<T> buttonClickHandler,
            OneArgKnob<Button> buttonTuner) {
        column.setCellFactory(new Callback<TableColumn<T, Void>, TableCell<T, Void>>() {
            @Override
            public TableCell<T, Void> call(final TableColumn<T, Void> param) {
                return new TableCell<T, Void>() {
                    private final Button button = new Button(buttonText);

                    {
                        if (buttonTuner != null)
                            buttonTuner.execute(button);

                        button.setOnAction((ActionEvent event) -> buttonClickHandler.execute(getTableView().getItems().get(getIndex())));
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
            }
        });
    }
}
