import static com.github.estebanwasinger.OrderCalculator.MENU;
import static com.github.estebanwasinger.OrderCalculator.PEDIDOS;
import static java.util.Collections.singletonMap;
import static org.mockito.Mockito.when;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;

import com.github.estebanwasinger.OrderCalculator;
import com.github.estebanwasinger.Pedido;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

/**
 * donna-bot-maven
 *
 * @author Esteban Wasinger (http://github.com/estebanwasinger)
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderCalculatorTestCase {

    public static final String KATSUKARE = "Katsukare";
    public static final String TONKATSU = "Tonkatsu";
    @Mock
    MuleEventContext muleEventContext;

    @Mock
    MuleMessage muleMessage;

    @Test
    public void test() throws Exception {
        OrderCalculator orderCalculator = new OrderCalculator();
        when(muleEventContext.getMessage()).thenReturn(muleMessage);
        HashMap<String, Integer> menu = new HashMap<>();
        menu.put(KATSUKARE, 110);
        menu.put(TONKATSU, 150);

        when(muleMessage.getInvocationProperty(MENU)).thenReturn(menu);
        Pedido pedido = new Pedido();
        pedido.agregarPlato(KATSUKARE);
        pedido.agregarPlato(KATSUKARE);
        pedido.agregarPlato(KATSUKARE);
        pedido.agregarPlato(KATSUKARE);
        pedido.agregarPlato(KATSUKARE);
        pedido.agregarPlato(TONKATSU);
        pedido.agregarPlato(TONKATSU);
        pedido.agregarPlato(TONKATSU);
        pedido.agregarPlato(TONKATSU);
        pedido.agregarPlato(TONKATSU);
        when(muleMessage.getInvocationProperty(PEDIDOS)).thenReturn(singletonMap("Esteban", pedido));

        Object o = orderCalculator.onCall(muleEventContext);
        System.out.println(o);
    }
}
