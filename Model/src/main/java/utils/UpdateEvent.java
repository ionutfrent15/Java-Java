package utils;

import dto.ProdusDTO;

import java.io.Serializable;

public class UpdateEvent implements Serializable {
    private EventType eventType;
    private ProdusDTO oldData;
    private ProdusDTO newData;

    public UpdateEvent(EventType eventType, ProdusDTO oldData, ProdusDTO newData) {
        this.eventType = eventType;
        this.oldData = oldData;
        this.newData = newData;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public ProdusDTO getOldData() {
        return oldData;
    }

    public void setOldData(ProdusDTO oldData) {
        this.oldData = oldData;
    }

    public ProdusDTO getNewData() {
        return newData;
    }

    public void setNewData(ProdusDTO newData) {
        this.newData = newData;
    }
}
