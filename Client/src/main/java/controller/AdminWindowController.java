package controller;

import dto.ProdusDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.Produs;
import utils.ControllerObserver;

import java.rmi.RemoteException;

public class AdminWindowController extends WindowController implements ControllerObserver {

    public Button adaugaBtn;
    public Button actualizeazaBtn;
    public Button stergeBtn;


    private ProdusDTO selected;

    public AdminWindowController() throws RemoteException {
    }

    private String validateInput(String denumire, String pret, String cantitate){
        String err = "";
        if(denumire.equals("")){
            err += "Denumire invalida\n";
        }
        if(pret.equals("")){
            err += "Pret invalid\n";
        }
        if(cantitate.equals("")){
            err += "Cantitate invalida\n";
        }
        try{
            Double.parseDouble(pret);
            Integer.parseInt(cantitate);
        }catch (NumberFormatException e){
            err += "Numar invalid";
        }
        return err;
    }

    public void handlerAdaugaBtn(ActionEvent actionEvent) throws RemoteException {
        String denumire = denumireTextField.getText();
        String pret = pretTextField.getText();
        String cantitate = stocTextField.getText();
        String err = validateInput(denumire, pret, cantitate);
        if(!err.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, err, ButtonType.OK);
            alert.showAndWait();
            alert.getResult();
        } else {
            server.adaugaStoc(denumire, Double.parseDouble(pret), Integer.parseInt(cantitate));

            clearDetails();
        }
    }

    public void handlerActualizeazaBtn(ActionEvent actionEvent) throws RemoteException {
        String denumire = denumireTextField.getText();
        String pret = pretTextField.getText();
        String cantitate = stocTextField.getText();
        String err = validateInput(denumire, pret, cantitate);
        if(!err.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR, err, ButtonType.OK);
            alert.showAndWait();
            alert.getResult();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Update " + selected.getDenumire() + " ?", ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                server.actualizeazaProdus(selected.getId(), denumire, Double.parseDouble(pret), Integer.parseInt(cantitate));
                clearDetails();
            }
        }
    }

    public void handlerStergeBtn(ActionEvent actionEvent) throws RemoteException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + selected.getDenumire() + " ?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            server.stergeProdus(selected.getId());
            clearDetails();
        }

    }

    @Override
    public void selectedTrue(ProdusDTO selectedProdus) {
        this.selected = selectedProdus;
        stergeBtn.setDisable(false);
        actualizeazaBtn.setDisable(false);
    }

    @Override
    public void selectedFalse() {
        this.selected = null;
        stergeBtn.setDisable(true);
        actualizeazaBtn.setDisable(true);
    }


}
