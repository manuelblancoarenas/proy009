package es.cic25.proy009.controller;

public class IdsNoCoincidenException extends RuntimeException {

    // incluimos tres constructores: uno vacío, uno admitiendo mensaje y otro admitiendo mensaje y throwable
    public IdsNoCoincidenException() {

    }
    
    public IdsNoCoincidenException(String message) {
        super(message);
    }

    public IdsNoCoincidenException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
