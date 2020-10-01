import arrow.meta.plugin.testing.CompilerPlugin
import arrow.meta.plugin.testing.CompilerTest
import arrow.meta.plugin.testing.Dependency
import arrow.meta.plugin.testing.assertThis
import com.intuit.hooks.plugin.MetaPlugin
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import arrow.synthetic

class HookTest {
    @Test
    fun test() {

        val hooks = Dependency("hooks:1.0.0-SNAPSHOT")
        val prelude = Dependency("arrow-meta-prelude:1.4.10-SNAPSHOT")

        assertThis(CompilerTest(
                config = {
                    addDependencies(hooks, prelude) + addMetaPlugins(MetaPlugin())
                },
                code = {
                    """
            | import com.intuit.hooks.EnableHooks
            | import com.intuit.hooks.SyncHook
            | 
            | //metadebug 
            | 
            | @EnableHooks
            | class TestHook: SyncHook<(newSpeed: Int) -> Unit>()
          """.source
                },
                assert = {
                    quoteOutputMatches("""
            | import com.intuit.hooks.EnableHooks
            | import com.intuit.hooks.SyncHook
            | 
            | @arrow.synthetic class TestHook: SyncHook<(newSpeed: Int) -> Unit>() {
            |     fun call(newSpeed: Int) {
            |         taps.forEach { (_, f) ->
            |             f.invoke(newSpeed)
            |         }
            |     }
            | }
            """.source)
                }
        ))
        Assertions.assertTrue(true)
    }
}