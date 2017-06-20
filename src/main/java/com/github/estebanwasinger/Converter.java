package com.github.estebanwasinger;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import java.util.Map;

public class Converter implements Callable {

    private static final String MENU = "menu";
    private static final String USER = "user";
    private static final String PEDIDOS = "pedidos";

    public Object onCall(MuleEventContext muleEventContext) throws Exception {
        MuleMessage message = muleEventContext.getMessage();
        Map<String, String> payload = (Map<String, String>) message.getPayload();
        Map<String, Pedido> pedidos = message.getInvocationProperty(PEDIDOS);

        String menu = payload.get(MENU);
        String user = payload.get(USER);

        if(pedidos.containsKey(user)){
            Pedido pedido = pedidos.get(user);
            pedido.agregarPlato(menu);
        } else {
            Pedido pedido = new Pedido();
            pedido.agregarPlato(menu);
            pedidos.put(user, pedido);
        }

        return pedidos;
    }
}
