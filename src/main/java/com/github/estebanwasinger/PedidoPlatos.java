package com.github.estebanwasinger;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PedidoPlatos implements Serializable {

    public PedidoPlatos(String plato, LocalDateTime fechaPedido) {
        this.plato = plato;
        this.fechaPedido = fechaPedido.toString();
    }

    private String plato;
    private String fechaPedido;

    public String getPlato() {
        return plato;
    }

    public void setPlato(String plato) {
        this.plato = plato;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    @Override
    public String toString() {
        return plato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PedidoPlatos that = (PedidoPlatos) o;

        return new EqualsBuilder()
                .append(plato, that.plato)
                .append(fechaPedido, that.fechaPedido)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(plato)
                .append(fechaPedido)
                .toHashCode();
    }
}
