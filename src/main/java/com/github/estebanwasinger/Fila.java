package com.github.estebanwasinger;

import static java.util.stream.Collectors.joining;

import org.mule.util.StringUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * donna-bot-maven
 *
 * @author Esteban Wasinger (http://github.com/estebanwasinger)
 */
public class Fila {

    private String htmlTemplate;
    private final String nombre;
    private String id;
    private Boolean paid;
    private String realName;
    private int total;
    private final List<String> pedidos;
    private List<String> fechas;

    Fila(String htmlTemplate, String nombre, String id, Boolean paid, String realName, int total, List<String> pedidos, List<String> fechas){
        this.htmlTemplate = htmlTemplate;

        this.nombre = nombre;
        this.id = id;
        this.paid = paid;
        this.realName = realName;
        this.total = total;
        this.pedidos = pedidos;
        this.fechas = fechas;
    }


    @NotNull
    private String getText() {
        return paid ? "Pagado" : "Pagar";
    }

    @NotNull
    private String getButtonClass() {
        return paid ? "btn btn-default" : "btn btn-primary";
    }


    @Override
    public String toString() {
        return String.format(htmlTemplate, getName(), toPList(pedidos), toPList(fechas), total, getButtonClass(), id, id, getText());
    }

    private String getName() {
        return StringUtils.isBlank(realName) ? nombre : realName;
    }

    private String toPList(List<String> pedidos) {
        return pedidos.stream().collect(joining("<p>","<p>",""));
    }
}
