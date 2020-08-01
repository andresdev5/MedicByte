package ec.edu.espe.medicbyte.common.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jonathan Andres J.
 */
public class UIEventArguments {
    private List<Object> args = new ArrayList<>();

    public UIEventArguments(Object... args) {
        this.args.addAll(Arrays.asList(args));
    }

    public <T> T get(int index) {
        if (index >= this.args.size()) {
            return null;
        }

        return (T) this.args.get(index);
    }
}
