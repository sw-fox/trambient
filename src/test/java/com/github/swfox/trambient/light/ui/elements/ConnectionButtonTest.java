package com.github.swfox.trambient.light.ui.elements;

import com.github.swfox.trambient.light.ui.UIController;
import com.github.swfox.trambient.light.ui.UIModel;
import com.github.swfox.trambient.light.ui.observer.ValueObservable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class ConnectionButtonTest {
    private UIModel modelMock;
    private ValueObservable<String> ipMock;
    private ValueObservable<String> secretMock;
    private ValueObservable<Boolean> isConnectedMock;
    private UIController controllerMock;

    @BeforeEach
    public void init() {
        modelMock = mock(UIModel.class);
        ipMock = mock(ValueObservable.class);
        secretMock = mock(ValueObservable.class);
        isConnectedMock = mock(ValueObservable.class);
        controllerMock = mock(UIController.class);
        when(modelMock.getGatewayIp()).thenReturn(ipMock);
        when(modelMock.getGatewaySecret()).thenReturn(secretMock);
        when(modelMock.getIsConnected()).thenReturn(isConnectedMock);
    }

    @Test
    public void actionPerformed_connect() {
        ActionEvent eventMock = mock(ActionEvent.class);
        when(ipMock.get()).thenReturn("ip");
        when(secretMock.get()).thenReturn("secret");
        when(isConnectedMock.get()).thenReturn(true);
        ConnectionButton sut = new ConnectionButton(controllerMock, modelMock);
        when(eventMock.getSource()).thenReturn(sut);

        sut.actionPerformed(eventMock);

        verify(controllerMock, times(1)).connect();
    }

    @Test
    public void update_connected() {
        ConnectionButton sut = new ConnectionButton(controllerMock, modelMock);
        when(isConnectedMock.get()).thenReturn(true);

        sut.update();

        assertEquals("Verbunden", sut.getText());
        assertFalse(sut.isEnabled());
    }
}
