package utils;

public interface ControllerObservable {
    void addControllerObserver(ControllerObserver observer);
    void removeControllerObserver(ControllerObserver observer);
    void notifyAllControllerObservers(SelectionEvent event);
}
