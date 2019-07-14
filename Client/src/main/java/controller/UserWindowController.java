package controller;

import dto.ProdusDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Stoc;
import utils.ControllerObserver;

import java.rmi.RemoteException;

public class UserWindowController extends WindowController implements ControllerObserver {
    public Button comandaBtn;
    public Spinner cantitateSpinner;
    public Label labelCantitate;

    private ProdusDTO selected;

    public UserWindowController() throws RemoteException {
    }

    @Override
    public void selectedTrue(ProdusDTO selectedProdus) {
        this.selected = selectedProdus;
        int maxValue = selected.getCantitate();
        if(maxValue > 0){
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxValue, 1);
            cantitateSpinner.setValueFactory(valueFactory);
            comandaBtn.setDisable(false);
            cantitateSpinner.setDisable(false);
        }
        else {
            comandaBtn.setDisable(true);
            cantitateSpinner.setDisable(true);
        }
    }

    @Override
    public void selectedFalse() {
        this.selected = null;
        int maxValue = selected.getCantitate();
        if(maxValue > 0) {
            comandaBtn.setDisable(false);
            cantitateSpinner.setDisable(false);
        }
        else {
            comandaBtn.setDisable(true);
            cantitateSpinner.setDisable(true);
        }
    }

    public void handlerComandaBtn(ActionEvent actionEvent) throws RemoteException {
        int cantitate = (int) cantitateSpinner.getValue();
        server.comandaProdus(selected.getId(), selected.getCantitate() - cantitate);
    }
}
