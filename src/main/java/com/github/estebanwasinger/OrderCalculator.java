package com.github.estebanwasinger;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import java.util.HashMap;
import java.util.Map;

public class OrderCalculator implements Callable {

    public static final String PEDIDOS = "pedidos";
    public static final String MENU = "menu";

    @Override
    public Object onCall(MuleEventContext eventContext) throws Exception {
        Map<String, Pedido> pedidos = getFlowVar(eventContext, PEDIDOS);
        Map<String, Integer> menu = getFlowVar(eventContext, MENU);

        Integer totalPlatos = pedidos.values()
                .stream()
                .mapToInt(pedido -> pedido
                        .getPlatos()
                        .size())
                .sum();

        HashMap<String, Integer> totalPorPlato = new HashMap<>();
        pedidos.values()
                .stream()
                .flatMap(pedido -> pedido.getPlatos().stream())
                .forEach(pedidoPlatos -> totalPorPlato.compute(pedidoPlatos.getPlato(), (key, val) -> val == null ? 1 : val + 1));

        int totalPlata = totalPorPlato.entrySet().stream().mapToInt(entry -> menu.get(entry.getKey()) * entry.getValue()).sum();

        String totalPorPlatoMsg = totalPorPlato.entrySet()
                .stream()
                .map(entry -> format("*» %s* : %s", entry.getKey(), entry.getValue()))
                .collect(joining("\n", "", "\n"));

        String totalPlatosMsg = format("*Cantidad Platos:* %s   *Total:* $%s", totalPlatos, totalPlata);

        MuleMessage message = eventContext.getMessage();
        message.setInvocationProperty("order", totalPorPlatoMsg + "\n" + totalPlatosMsg);
        return message;
    }

    private <T> T getFlowVar(MuleEventContext eventContext, String flowVarName) {
        return (T) eventContext.getMessage().getInvocationProperty(flowVarName);
    }
}
