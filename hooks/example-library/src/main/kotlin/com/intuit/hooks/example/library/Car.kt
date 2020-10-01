package com.intuit.hooks.example.library

import com.intuit.hooks.EnableHooks
import com.intuit.hooks.SyncHook

// metadebug

@EnableHooks
class Accelerate: SyncHook<(newSpeed: Int) -> Unit>()

class Car() {
    val hooks = Hooks()

    class Hooks {
        val accelerate = AccelerateSyncHook()
    }

    /* I think what I really want is this. Declare the hooks in properties
    on a class and then decorate the class with @EnableHooks, which will create
    a hook for each item in the class. It's basically what arrow optics does for data classes
    but in this case it's for tapable hooks.

    @EnableHooks
    class Hooks {
        val accelerate = SyncHook<(newSpeed: Int) -> Unit>()
        val brake = SyncHook<() -> Unit>()
    }
     */

    fun go(speed: Int) {
        this.hooks.accelerate.call(88)
    }
}

