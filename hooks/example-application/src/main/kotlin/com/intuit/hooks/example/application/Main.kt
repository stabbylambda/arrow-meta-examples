package com.intuit.hooks.example.application

import com.intuit.hooks.example.library.Car

fun main(args : Array<String>) {
    val car = Car()
    car.hooks.accelerate.tap("LoggerPlugin") { newSpeed ->
        print("Accelerating to $newSpeed")
    }

    car.go(88)
}
