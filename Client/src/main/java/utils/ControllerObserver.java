package utils;

import dto.ProdusDTO;

public interface ControllerObserver {
    void selectedTrue(ProdusDTO selectedProdus);
    void selectedFalse();
}
