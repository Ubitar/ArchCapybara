package com.ubitar.app.common

/**
 * 实现该接口后，通过AppActivityLifecycleCallbacks或AppFragmentLifecycleCallbacks判断是否
 * 使用Immersionbar
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class  IImmersionbar {
}