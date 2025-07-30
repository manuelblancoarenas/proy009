package es.cic25.proy009.controller;

public class IntentoModificacionSecurityException extends RuntimeException {

    // incluimos tres constructores: uno vacío, uno admitiendo mensaje y otro admitiendo mensaje y throwable
    public IntentoModificacionSecurityException() {

    }
    
    public IntentoModificacionSecurityException(String message) {
        super(message);
    }

    public IntentoModificacionSecurityException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
