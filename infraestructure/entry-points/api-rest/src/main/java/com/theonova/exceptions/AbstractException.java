package com.theonova.exceptions;

import com.theonova.exceptions.dto.GeneralMessage;
import com.theonova.exceptions.enums.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.*;

@Slf4j
public abstract class AbstractException extends RuntimeException {
    private List<GeneralMessage> mensajes;
    public final HttpStatus status;

    Environment environment;

    public AbstractException(HttpStatus status) {
        super();
        this.status = status;
        this.mensajes = new ArrayList<>();
    }
    /*public AbstractException(String code,String tipo, String mensaje,HttpStatus status) {
        super(mensaje);
        this.mensajes = new ArrayList<>();
        GeneralMessage mensajeGeneral =new GeneralMessage(code, "", LocalDateTime.now(),tipo, "ERROR", "", mensaje, "","");
        setearServicioErrorTecnico (generalMessage);
        this.mensajes.add (generalMessage);
        this.status = status;
    }*/

    public AbstractException(Environment environment, Class clase, String codigo, HttpStatus status, String tipo, String... datos){
        super();
        // Obtener el mensaje base desde el environment
        String mensajeTemplate = datos[0];  // Primer dato es el mensaje a formatear
        // Usar los demás valores de datos como parámetros para el formato
        String[] parametros =new String[]{};

        if(datos.length>0){
            parametros=Arrays.copyOfRange(datos, 1, datos.length);  // Todos los parámetros después del primero
        }

        this.mensajes = new ArrayList<>();
        GeneralMessage generalMessage =new GeneralMessage();
        generalMessage.setTransactionCode(UUID.randomUUID().toString());
        generalMessage.setType(tipo);
        generalMessage.setResponse(environment.getProperty(clase.getSimpleName() + ".mensaje"));
        generalMessage.setCode(codigo);
        generalMessage.setTransactionStatus(status.getReasonPhrase());
        generalMessage.setResponseDate(Instant.now());
        // Descripción del mensaje con todos los datos
        String descripcion = environment.getProperty(clase.getSimpleName() +"."+ mensajeTemplate);
        if (descripcion != null&&parametros.length>0) {
            // Usar MessageFormat con todos los parámetros de datos para la descripción
            generalMessage.setDescription(MessageFormat.format(descripcion, (Object[]) parametros));
        }else if (descripcion != null) {
            // Usar MessageFormat con todos los parámetros de datos para la descripción
            generalMessage.setDescription(descripcion);
        }
        generalMessage.setResponse("");
        generalMessage.setService(environment.getProperty(clase.getSimpleName()));
        setearServicioErrorTecnico (generalMessage);
        this.mensajes.add (generalMessage);
        this.status = status;
        log.info (generalMessage.toString());
    }
    public AbstractException(Environment environment, Class clase, String codigo, HttpStatus status, String... datos){
        super();
        // Obtener el mensaje base desde el environment
        String mensajeTemplate = datos[0];  // Primer dato es el mensaje a formatear
        // Usar los demás valores de datos como parámetros para el formato
        String[] parametros =new String[]{};

        if(datos.length>0){
            parametros=Arrays.copyOfRange(datos, 1, datos.length);  // Todos los parámetros después del primero
        }

        this.mensajes = new ArrayList<>();
        GeneralMessage generalMessage =new GeneralMessage();
        generalMessage.setTransactionCode(UUID.randomUUID().toString());
        generalMessage.setType(ExceptionEnum.TIPO_ALVERTENCIA.getName());
        generalMessage.setResponse(environment.getProperty(clase.getSimpleName() + ".mensaje"));
        generalMessage.setCode(codigo);
        generalMessage.setTransactionStatus(status.getReasonPhrase());
        generalMessage.setResponseDate(Instant.now());
        // Descripción del mensaje con todos los datos
        String descripcion = environment.getProperty(clase.getSimpleName() +"."+ mensajeTemplate);
        if (descripcion != null&&parametros.length>0) {
            // Usar MessageFormat con todos los parámetros de datos para la descripción
            generalMessage.setDescription(MessageFormat.format(descripcion, (Object[]) parametros));
        }else if (descripcion != null) {
            // Usar MessageFormat con todos los parámetros de datos para la descripción
            generalMessage.setDescription(descripcion);
        }
        if (generalMessage.getResponse()==null)generalMessage.setResponse("");
        generalMessage.setService(environment.getProperty(clase.getSimpleName()));
        setearServicioErrorTecnico (generalMessage);
        this.mensajes.add (generalMessage);
        this.status = status;
        log.info (generalMessage.toString());
    }

    public AbstractException(Class clase, String mensajePersonalizado, String dato, String codigo, HttpStatus status){
        super();
        this.mensajes = new ArrayList<>();
        GeneralMessage generalMessage =new GeneralMessage();
        generalMessage.setTransactionCode(UUID.randomUUID().toString());
        generalMessage.setType(ExceptionEnum.TIPO_INFORMACION.getName());
        generalMessage.setResponse(MessageFormat.format(
                Objects.requireNonNull(environment.getProperty(clase.getSimpleName()+"."+mensajePersonalizado)),dato));
        generalMessage.setCode(codigo);
        generalMessage.setTransactionStatus(status.getReasonPhrase());
        generalMessage.setResponseDate(Instant.now());
        generalMessage.setDescription(MessageFormat.format(
                Objects.requireNonNull(environment.getProperty(clase.getSimpleName()+".descripcion")),dato));
        generalMessage.setService(MessageFormat.format(
                Objects.requireNonNull(environment.getProperty(clase.getSimpleName())),dato));
        setearServicioErrorTecnico (generalMessage);
        this.mensajes.add (generalMessage);
        this.status = status;
        log.info (generalMessage.toString());
    }
    public AbstractException(Class clase, String dato, String codigo, HttpStatus status){
        super();
        this.mensajes = new ArrayList<>();
        GeneralMessage generalMessage =new GeneralMessage();
        generalMessage.setTransactionCode(UUID.randomUUID().toString());
        generalMessage.setType(ExceptionEnum.TIPO_INFORMACION.getName());
        generalMessage.setResponse(MessageFormat.format(
                Objects.requireNonNull(environment.getProperty(clase.getSimpleName()+".mensaje")),dato));
        generalMessage.setCode(codigo);
        generalMessage.setTransactionStatus(status.getReasonPhrase());
        generalMessage.setResponseDate(Instant.now());
        generalMessage.setDescription(MessageFormat.format(
                Objects.requireNonNull(environment.getProperty(clase.getSimpleName()+".descripcion")),dato));
        generalMessage.setService(MessageFormat.format(
                Objects.requireNonNull(environment.getProperty(clase.getSimpleName())),dato));
        setearServicioErrorTecnico (generalMessage);
        this.mensajes.add (generalMessage);
        this.status = status;
        log.info (generalMessage.toString());
    }
    public AbstractException(String servicio, String codigo, String mensaje, HttpStatus status) {
        super(mensaje);
        this.mensajes = new ArrayList<>();
        GeneralMessage generalMessage =new GeneralMessage();
        generalMessage.setTransactionCode(UUID.randomUUID().toString());
        generalMessage.setService(servicio);
        generalMessage.setType(ExceptionEnum.TIPO_INFORMACION.getName());
        generalMessage.setResponse(mensaje);
        generalMessage.setCode(codigo);
        generalMessage.setTransactionStatus(status.getReasonPhrase());
        generalMessage.setResponseDate(Instant.now());
        generalMessage.setDescription("");
        generalMessage.setTechnicalDetail("");
        //setearServicioErrorTecnico (generalMessage);
        this.mensajes.add (generalMessage);
        this.status = status;
        log.info (generalMessage.toString());
    }
    public AbstractException(String servicio, String codigo, String mensaje, HttpStatus status, String descripcion) {
        super(mensaje);
        this.mensajes = new ArrayList<>();
        GeneralMessage generalMessage =new GeneralMessage();
        generalMessage.setTransactionCode(UUID.randomUUID().toString());
        generalMessage.setService(servicio);
        generalMessage.setType(ExceptionEnum.TIPO_INFORMACION.getName());
        generalMessage.setResponse(mensaje);
        generalMessage.setCode(codigo);
        generalMessage.setTransactionStatus(status.getReasonPhrase());
        generalMessage.setResponseDate(Instant.now());
        generalMessage.setDescription(descripcion);
        generalMessage.setTechnicalDetail("");
        //setearServicioErrorTecnico (generalMessage);
        this.mensajes.add (generalMessage);
        this.status = status;
        log.info (generalMessage.toString());
    }

    public AbstractException(
            String transaccionCode,
            Instant responseDate,
            String type,
            String status,
            String code,
            String response,
            String description,
            HttpStatus statusHttp
    ) {
        super(response);
        this.mensajes = new ArrayList<>();
        GeneralMessage generalMessage =new GeneralMessage(transaccionCode, "", responseDate,type, status, code, response, description,"");
        setearServicioErrorTecnico (generalMessage);
        this.mensajes.add (generalMessage);
        this.status = statusHttp;
    }

    public List<GeneralMessage> getMessages() {
        return mensajes;
    }

    public void addMessage(GeneralMessage message) {
        this.mensajes.add(message);
    }

    /**
     * Permite quitar la extensión ".java" de un nombre de archivo
     * @param nombreArchivo
     * @return devuelve el nombre sin la extensión
     */
    private String quitarExtensionJava(String nombreArchivo) {
        if (nombreArchivo != null && nombreArchivo.endsWith(".java")) {
            return nombreArchivo.substring(0, nombreArchivo.length() - 5);
        }
        return nombreArchivo; // Retorna el nombre original si no tiene la extensión
    }

    /***
     * Permite establecer el nombre del servicio y la clase que está llamando al error
      * @param generalMessage
     */
    private void setearServicioErrorTecnico(GeneralMessage generalMessage){
        StackTraceElement[] stackTrace = getStackTrace();
        String claseMensaje="";
        String metodoMensaje="";
        String claseServicio="";
        String metodoServicio="";
        if (stackTrace.length > 0) {
            StackTraceElement origenMensaje = stackTrace[0];
            claseMensaje=origenMensaje.getClassName();
            metodoMensaje=origenMensaje.getMethodName();
            StackTraceElement origenServicio = stackTrace[1];
            claseServicio=quitarExtensionJava(origenServicio.getFileName());
            metodoServicio=origenServicio.getMethodName();
        }
        //mensajeGeneral.setServicio(claseServicio+":"+metodoServicio);
        generalMessage.setTechnicalDetail(claseMensaje+":"+metodoMensaje);
    }

    public AbstractException(String codigo, String mensaje, HttpStatus status) {
        super(mensaje);
        this.mensajes = new ArrayList<>();
        GeneralMessage generalMessage =new GeneralMessage();
        generalMessage.setTransactionCode(UUID.randomUUID().toString());
        generalMessage.setType(ExceptionEnum.TIPO_INFORMACION.getName());
        generalMessage.setResponse(mensaje);
        generalMessage.setCode(codigo);
        generalMessage.setTransactionStatus(status.getReasonPhrase());
        generalMessage.setResponseDate(Instant.now());
        generalMessage.setDescription("");
        setearServicioErrorTecnico (generalMessage);
        this.mensajes.add (generalMessage);
        this.status = status;
        log.info (generalMessage.toString());
    }
}