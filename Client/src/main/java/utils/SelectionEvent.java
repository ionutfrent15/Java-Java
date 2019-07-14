package utils;

public class SelectionEvent {
    private boolean selected;

    public SelectionEvent(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }
}
