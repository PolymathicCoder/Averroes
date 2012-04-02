package com.polymathiccoder.averroes.meta.validation;

import java.util.List;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public final class ValidationResult {
	private final List<?> errors;
	private final List<?> warnings;
	
	public ValidationResult(List<?> errors, List<?> warnings) {
		this.errors = errors;
		this.warnings = warnings;
	}

	public List<?> getErrors() {
		return errors;
	}

	public List<?> getWarnings() {
		return warnings;
	}
	
	@Override
    public String toString() {
        return Pojomatic.toString(this);
    }

    @Override
    public int hashCode() {
        return Pojomatic.hashCode(this);
    }

    @Override
    public boolean equals(final Object other) {
        return Pojomatic.equals(this, other);
    }
}
