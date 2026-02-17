package com.theonova.exceptions;

import com.theonova.exceptions.enums.ExceptionEnum;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class MensajeClase {

    private static Environment environment;

    // Inyección del Environment de forma estática
    public MensajeClase(Environment environment) {
        MensajeClase.environment = environment;
    }

    // Métodos estáticos
    public static String getServicio(Class<?> clase) {
        return environment.getProperty(clase.getSimpleName());
    }

    public static String getDescripcion(Class<?> clase) {
        return environment.getProperty(clase.getSimpleName() + ".descripcion");
    }

    public static String getMensaje(Class<?> clase) {
        return environment.getProperty(clase.getSimpleName() + ".mensaje");
    }

    public static String getMensajePersonalizado(Class<?> clase,String mensaje) {
        return environment.getProperty(clase.getSimpleName() + "."+mensaje);
    }

    public static String getError(Class<?> clase) {
        return environment.getProperty(clase.getSimpleName() + ".error");
    }

    public static String getCampoObligatorio(Class<?> clase) {
        return environment.getProperty(clase.getSimpleName() + ".campoObligatorio");
    }

    public static void setMensajeException(Class<?> clase,String... mensajes) {
        throw new GeneralException(clase,environment,mensajes);
    }
    public static void setMensajeExceptionInformativo(Class<?> clase,String... mensajes) {
        throw new GeneralException(clase,environment, ExceptionEnum.TIPO_INFORMACION,mensajes);
    }
    public static void setMensajeExceptionAdvertencia(Class<?> clase,String... mensajes) {
        throw new GeneralException(clase,environment, ExceptionEnum.TIPO_ALVERTENCIA,mensajes);
    }
    public static void setMensajeExceptionError(Class<?> clase,String... mensajes) {
        throw new GeneralException(clase,environment, ExceptionEnum.TIPO_ERROR,mensajes);
    }

    public static String getPath(Class<?> clase) {
        return environment.getProperty(clase.getSimpleName() + ".path");
    }
    public static String getConcepto(Class<?> clase) {
        return environment.getProperty(clase.getSimpleName() + ".concepto");
    }

    public static String getTransaccion(Class<?> clase) {
        return environment.getProperty(clase.getSimpleName() + ".transaccion");
    }

    public static String getNombre(Class<?> clase) {
        return environment.getProperty(clase.getSimpleName() + ".nombre");
    }

    public static String getOK(Class<?> clase) {
        return environment.getProperty(clase.getSimpleName() + ".ok");
    }
}
