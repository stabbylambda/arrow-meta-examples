package com.intuit.hooks.plugin

import kotlin.contracts.ExperimentalContracts

import arrow.meta.CliPlugin
import arrow.meta.Meta
import arrow.meta.phases.CompilerContext

class MetaPlugin : Meta {
    @ExperimentalContracts
    override fun intercept(ctx: CompilerContext): List<CliPlugin> =
        listOf(
            syncHook
        )
}
