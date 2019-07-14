package utils;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
    void update(UpdateEvent updateEvent) throws RemoteException;
}
