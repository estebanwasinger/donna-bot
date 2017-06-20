package com.github.estebanwasinger;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.util.stream.Collectors.joining;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pedido implements Serializable {

    private List<PedidoPlatos> platos = new ArrayList<>();
    private boolean isPaid = false;

    public Pedido() {
    }

    public Pedido(List<PedidoPlatos> platos, boolean isPaid) {
        this.platos = platos;
        this.isPaid = isPaid;
    }

    public void agregarPlato(String nombrePlato){
        platos.add(new PedidoPlatos(nombrePlato, now(of("GMT-3"))));
    }

    public void pagar(){
        isPaid = true;
    }

    public List<PedidoPlatos> getPlatos() {
        return platos;
    }

    public void setPlatos(List<PedidoPlatos> platos) {
        this.platos = platos;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Override
    public String toString() {
        return createPlatosMessage() + (isPaid ? " :white_check_mark:" : "");
    }

    private String createPlatosMessage() {
        HashMap<String, Integer> pedidosDePersona = new HashMap<>();
        platos.forEach(pedidoPlatos -> pedidosDePersona.compute(pedidoPlatos.getPlato(), (key, val) -> val == null ? 1 : val + 1));
        return pedidosDePersona.entrySet().stream().map(entry -> format("%s *[%d]*", entry.getKey(), entry.getValue())).collect(joining(", ","",""));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Pedido pedido = (Pedido) o;

        return new EqualsBuilder()
                .append(isPaid, pedido.isPaid)
                .append(platos, pedido.platos)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(platos)
                .append(isPaid)
                .toHashCode();
    }
}
