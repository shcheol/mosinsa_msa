package com.mosinsa.order.infra.api.httpclient;

import java.io.IOException;
import java.net.URI;

public interface ApiExecutor {

	String execute(URI uri) throws IOException;
}
