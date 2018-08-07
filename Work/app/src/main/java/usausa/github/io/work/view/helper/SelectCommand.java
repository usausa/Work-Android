package usausa.github.io.work.view.helper;

public class SelectCommand {

    @FunctionalInterface
    public interface Action {

        void select(int position);
    }

    private final Action action;

    public SelectCommand(final Action action) {
        this.action = action;
    }

    public void execute(final int position) {
        action.select(position);
    }
}
