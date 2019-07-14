package controller;

import dto.ProdusDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utils.*;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class WindowController extends UnicastRemoteObject implements ControllerObservable, IClient, Serializable {

    public TableView produsTableView;
    public TextField denumireTextField;
    public TextField pretTextField;
    public TextField stocTextField;
    public TableColumn produsTableColumn;
    public TableColumn pretTableColumn;
    public TableColumn cantitateTableColumn;
    public Button logoutBtn;
    public ProgressIndicator progressIndicator;

    private Stage primaryStage;
    private Scene loginScene;
    protected IServer server;
    protected ObservableList<ProdusDTO> produsObservableList = FXCollections.observableArrayList();

    private List<ControllerObserver> observers = new ArrayList<>();
    private ProdusDTO selectedProdus;

    public WindowController() throws RemoteException {
    }

    @FXML
    public void initialize(){
        initTable();
    }


    public void setServer(IServer server) throws RemoteException {
        this.server = server;
        server.addClient(this);

        Task<LineChart> task = new Task<LineChart>() {

            @Override
            protected LineChart call() throws Exception {
                for (int i = 0; i < 10; i++) {
                    updateProgress(10 * i, 100);
                }

                loadObservableList();

                updateProgress(100, 100);
                return new LineChart(new NumberAxis(), new NumberAxis());
            }

        };
        progressIndicator.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(evt -> produsTableView.setItems(produsObservableList));
        new Thread(task).start();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setLoginScene(Scene loginScene) {
        this.loginScene = loginScene;
    }

    protected void loadObservableList() throws RemoteException {
        produsObservableList.setAll(server.getAllProdusDTO());
    }

    private void initTable(){
        produsTableColumn.setCellValueFactory(new PropertyValueFactory<ProdusDTO, String>("denumire"));
        pretTableColumn.setCellValueFactory(new PropertyValueFactory<ProdusDTO, Double>("pret"));
        cantitateTableColumn.setCellValueFactory(new PropertyValueFactory<ProdusDTO, Integer>("cantitate"));
        produsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedProdus = (ProdusDTO) newSelection;
                loadDetails();
            }
        });
    }

    private void loadDetails(){
        ProdusDTO dto = (ProdusDTO) produsTableView.getSelectionModel().getSelectedItem();
        String denumire = dto.getDenumire();
        double pret = dto.getPret();
        int cantitate = dto.getCantitate();
        denumireTextField.setText(denumire);
        pretTextField.setText(String.valueOf(pret));
        stocTextField.setText(String.valueOf(cantitate));

        notifyAllControllerObservers(new SelectionEvent(true));
    }

    protected void clearDetails(){
        denumireTextField.setText("");
        pretTextField.setText("");
        stocTextField.setText("");
        produsTableView.getSelectionModel().clearSelection();

        notifyAllControllerObservers(new SelectionEvent(false));
    }

    @Override
    public void addControllerObserver(ControllerObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeControllerObserver(ControllerObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllControllerObservers(SelectionEvent event) {
        if(event.isSelected()){
            observers.forEach(observer -> observer.selectedTrue(selectedProdus));
        }
        else {
            observers.forEach(ControllerObserver::selectedFalse);
        }
    }

    @Override
    public void update(UpdateEvent updateEvent) throws RemoteException {
        if(updateEvent.getEventType().equals(EventType.ADD)){
            produsObservableList.add(updateEvent.getNewData());
        }
        else if(updateEvent.getEventType().equals(EventType.DELETE)){
            produsObservableList.remove(updateEvent.getOldData());
        }
        else if(updateEvent.getEventType().equals(EventType.UPDATE)){
            int index = produsObservableList.indexOf(updateEvent.getOldData());
            produsObservableList.set(index, updateEvent.getNewData());
        }
    }

    public void handlerLogoutBtn(ActionEvent actionEvent) throws RemoteException {
        server.logout(this);
        removeControllerObserver((ControllerObserver) this);
        primaryStage.setScene(loginScene);
    }
}
