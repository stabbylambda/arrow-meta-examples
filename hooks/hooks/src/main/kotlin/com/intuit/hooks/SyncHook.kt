package com.intuit.hooks

open class SyncHook<F:  Function<*>>() {
    protected val taps = hashMapOf<String, F>()
    fun tap(name: String, f: F) {
        this.taps[name] = f
    }

}
