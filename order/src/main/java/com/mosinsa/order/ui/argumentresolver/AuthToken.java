package com.mosinsa.order.ui.argumentresolver;

import java.util.Collection;
import java.util.Map;

public record AuthToken(Map<String, Collection<String>> map) {
}
