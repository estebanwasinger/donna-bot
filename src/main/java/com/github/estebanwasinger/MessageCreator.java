package com.github.estebanwasinger;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;
import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import java.util.Map;

public class MessageCreator implements Callable {

    private static final String EMPTY = "";
    private static final String BREAK_LINE = "\n";

    public Object onCall(MuleEventContext muleEventContext) throws Exception {
        Map<String, Pedido> pedidos = muleEventContext.getMessage().getPayload(Map.class);

        return "*Pedidos:* \n" + pedidos.entrySet()
                .stream()
                .map(this::createMessageForPedido)
                .collect(joining(BREAK_LINE, EMPTY, EMPTY));
    }

    private String createMessageForPedido(Map.Entry<String, Pedido> entry) {
        return format("*»* <@%s>: %s", entry.getKey(), entry.getValue());
    }
}
