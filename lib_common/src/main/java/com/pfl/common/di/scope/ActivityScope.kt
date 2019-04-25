package com.pfl.common.di.scope

import java.lang.annotation.Documented
import java.lang.annotation.Retention

import javax.inject.Scope

import java.lang.annotation.RetentionPolicy.RUNTIME

/**
 * 区域,范围(约束作用)
 */
@Scope
@Documented
@Retention(value = RUNTIME)
annotation class ActivityScope