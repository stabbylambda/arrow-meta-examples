package com.intuit.hooks.plugin

import arrow.meta.CliPlugin
import arrow.meta.Meta
import arrow.meta.invoke
import arrow.meta.quotes.Transform
import arrow.meta.quotes.classDeclaration
import arrow.meta.quotes.typeAlias
import com.intuit.hooks.EnableHooks
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFunctionType
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry
import org.jetbrains.kotlin.psi.debugText.getDebugText
import org.jetbrains.kotlin.psi.stubs.elements.KtClassElementType

val Meta.syncHook: CliPlugin
    get() =
        "SyncHook" {
            meta(
                classDeclaration(this, { isAnnotatedWith<EnableHooks>() } ) { c ->
                    val parameters = c.superTypeListEntries[0] as KtSuperTypeCallEntry
                    val fType = parameters.typeArguments[0].typeReference?.typeElement as KtFunctionType
                    val params = fType.parameters.map { it.getDebugText() }.first()

                    val newSource =
                    """|import com.intuit.hooks.SyncHook
                       |
                       |class ${c.name}SyncHook : SyncHook<${fType.getDebugText()}>() {
                       |    fun call(${params}) {
                       |        taps.forEach { (_, f) ->
                       |            f.invoke(newSpeed)
                       |        }
                       |    }
                       |}
                    """.trimIndent()

                    val originalClassAnnotation = Transform.replace<KtClass>(
                        replacing = c,
                        newDeclaration = newSource.`class`.syntheticScope
                    )
                    originalClassAnnotation
                }
            )
        }
