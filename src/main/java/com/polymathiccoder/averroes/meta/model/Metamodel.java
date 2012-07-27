package com.polymathiccoder.averroes.meta.model;

import java.util.Collections;
import java.util.Map;

import com.google.common.collect.Maps;

public class Metamodel {
	private final Map<String, Object> metadata;

	public Metamodel(final Map<String, Object> metadata) {
		this.metadata = Maps.newHashMap(metadata);
	}

	public Map<String, Object> getMetadata() {
		return Collections.unmodifiableMap(metadata);
	}
}
