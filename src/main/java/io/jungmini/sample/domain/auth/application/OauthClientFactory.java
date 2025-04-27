package io.jungmini.sample.domain.auth.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jungmini.sample.domain.auth.model.OAuthProvider;

/**
 * @author    : kimjungmin
 * Created on : 2025. 4. 24.
 */
@Component
public class OauthClientFactory {
	private final Map<OAuthProvider, OauthClient> clientMap;

	public OauthClientFactory(List<OauthClient> clients) {
		// 무상태라서 Concurrent 안써도 될거 같?
		clientMap = new HashMap<>();
		clients.forEach(client -> clientMap.put(client.getProvider(), client));
	}

	// Provider가 검증 되서 들어오기 때문에 Null X
	public OauthClient getClient(OAuthProvider provider) {
		return clientMap.get(provider);
	}
}
