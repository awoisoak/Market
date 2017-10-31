package com.awoisoak.market.utils.dagger;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Scope for the App lifecycle
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface AppScope {
}
