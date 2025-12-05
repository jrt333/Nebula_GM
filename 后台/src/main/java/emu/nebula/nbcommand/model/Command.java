package emu.nebula.nbcommand.model;

import emu.nebula.nbcommand.model.command.Syntax;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @param name
 * @param description
 * @param syntax
 * @param fullDescription
 */
public record Command(
        String name,
        String description,
        Syntax syntax,
        String fullDescription
) {
    // 为JavaFX属性绑定提供方法
    public StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public StringProperty descriptionProperty() {
        return new SimpleStringProperty(description);
    }
}
