package es.cic25.proy009.controller;

public class IntentoCreacionSecurityException extends RuntimeException {

    // incluimos tres constructores: uno vacío, uno admitiendo mensaje y otro admitiendo mensaje y throwable
    public IntentoCreacionSecurityException() {

    }

    public IntentoCreacionSecurityException(String mensaje) {
        super(mensaje);
    }

    public IntentoCreacionSecurityException(String mensaje, Throwable throwable) {
        super(mensaje, throwable);
    }
}
