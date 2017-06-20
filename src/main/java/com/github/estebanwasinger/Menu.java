package com.github.estebanwasinger;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.mule.transformer.types.SimpleDataType;

import java.io.InputStream;

/**
 * donna-bot-maven
 *
 * @author Esteban Wasinger (http://github.com/estebanwasinger)
 */
public class Menu implements Callable{

    private static final String APPLICATION_JSON = "application/json";
    private static final String MENU_FILE = "menu.json";

    public Object onCall(MuleEventContext muleEventContext) throws Exception {
        InputStream resourceAsStream = muleEventContext.getMuleContext().getExecutionClassLoader().getResourceAsStream(MENU_FILE);
        muleEventContext.getMessage().setPayload(resourceAsStream, new SimpleDataType<>(InputStream.class, APPLICATION_JSON));
        return muleEventContext.getMessage();
    }
}
