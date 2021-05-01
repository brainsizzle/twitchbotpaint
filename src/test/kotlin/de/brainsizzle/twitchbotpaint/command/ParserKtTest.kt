package de.brainsizzle.twitchbotpaint.command

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ParserKtTest {

    @BeforeEach
    internal fun setUp() {
        initCommands()
    }

    @Test
    fun tokenise() {
        Assertions.assertEquals(listOf("a", "circle", "123"), tokenise("a      circle   123"))
    }

    @Test
    fun clean() {
        Assertions.assertEquals("  grt  6656vbfg gfsdf45fghfsd", clean("ÄÖGRT%$6656vbfg gfsdf45fghfsd"))
        Assertions.assertEquals("a      circle   123", clean("  a  $%$ circle   123   "))
    }

    @Test
    fun parseCommand() {
        Assertions.assertEquals(listOf(Command(findByDefinitionByName(CommandName.Up)!!, listOf())),
                parseCommands( " n "))
        Assertions.assertEquals(listOf(
                Command(findByDefinitionByName(CommandName.Circle)!!, listOf(20)),
                Command(findByDefinitionByName(CommandName.Up)!!, listOf(50))),
                parseCommands( " circle 20 north 50 "))
    }
}