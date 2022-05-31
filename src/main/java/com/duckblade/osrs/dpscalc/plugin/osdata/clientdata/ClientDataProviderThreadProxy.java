package com.duckblade.osrs.dpscalc.plugin.osdata.clientdata;

import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ClientDataProvider;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.client.callback.ClientThread;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ClientDataProviderThreadProxy
{

	private final Client client;
	private final ClientThread clientThread;
	private final ClientDataProvider clientDataProvider;

	/**
	 * Silently swallows the runnable if client data is not available.
	 */
	public void tryAcquire(Consumer<ClientDataProvider> runnable)
	{
		clientThread.invokeLater(() ->
		{
			if (client.getGameState() == GameState.LOGGED_IN && client.getLocalPlayer() != null)
			{
				runnable.accept(clientDataProvider);
			}
		});
	}

}
