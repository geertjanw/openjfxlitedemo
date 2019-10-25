package OpenJFXLiteDemo;

import com.dukescript.api.javafx.beans.FXBeanInfo;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import static net.java.html.json.Models.applyBindings;

public final class Demo implements FXBeanInfo.Provider {
    private final StringProperty desc = new SimpleStringProperty(this, "desc", "Buy Milk");
    private final ListProperty<String> todos = new SimpleListProperty<>(this, "todos", FXCollections.observableArrayList());
    private final IntegerBinding numTodos = Bindings.createIntegerBinding(todos::size, todos);

    void addTodo() {
        todos.getValue().add(desc.getValue().toUpperCase());
        desc.setValue("");
    }

    private final FXBeanInfo info = FXBeanInfo.newBuilder(this)
            .property(desc)
            .property(todos)
            .property("numTodos", numTodos)
            .action("addTodo", this::addTodo)
            .build();

    @Override
    public FXBeanInfo getFXBeanInfo() {
        return info;
    }

    public static void onPageLoad() {
        Demo model = new Demo();
        applyBindings(model);
    }

}