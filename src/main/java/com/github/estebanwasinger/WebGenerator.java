package com.github.estebanwasinger;

import static java.util.stream.Collectors.toList;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.transformer.types.SimpleDataType;
import org.mule.util.IOUtils;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * donna-bot-maven
 *
 * @author Esteban Wasinger (http://github.com/estebanwasinger)
 */
public class WebGenerator implements Callable {

    @Override
    public Object onCall(MuleEventContext eventContext) throws Exception {

        String page = getResourceAsString(eventContext, "admin-page.html");
        String row = getResourceAsString(eventContext, "row.html");
        String uuid = eventContext.getMessage().getInvocationProperty("orgUUID");

        MuleMessage message = eventContext.getMessage();
        Map<String, Object> pedidos = message.getPayload(Map.class);
        String pedidosHtml = pedidos.entrySet().stream()
                .map(entry -> new Fila(row, entry.getKey(),(String) getEntryValue(entry, "id"), (Boolean) getEntryValue(entry, "paid"), (String) getEntryValue(entry, "real_name"), getTotal((List<Map<String, Object>>) getEntryValue(entry, "platos")),  getPlatos((List<Map<String, Object>>) getEntryValue(entry, "platos")), getFechas((List<Map<String, Object>>) getEntryValue(entry, "platos"))))
                .map(fila -> fila.toString())
                .collect(Collectors.joining());

        String format = String.format(page, pedidosHtml, uuid);

        message.setPayload(format, new SimpleDataType<>(String.class, "text/html"));
        return message;
    }

    private Object getEntryValue(Map.Entry<String, Object> entry, String platos) {
        return ((Map) entry.getValue()).get(platos);
    }

    private String getResourceAsString(MuleEventContext eventContext, String fileName) {
        InputStream resourceAsStream = eventContext.getMuleContext().getExecutionClassLoader().getResourceAsStream(fileName);
        return IOUtils.toString(resourceAsStream);
    }

    private List<String> getPlatos(List<Map<String, Object>> platos) {
        return platos.stream().map(plato -> plato.get("name") + " $" + plato.get("precio")).collect(toList());
    }

    private List<String> getFechas(List<Map<String, Object>> platos) {
        return platos.stream().map(plato -> (String) plato.get("fecha")).collect(toList());
    }

    private int getTotal(List<Map<String, Object>> platos){
        return platos.stream().mapToInt(plato -> (Integer) plato.get("precio")).sum();
    }
}
