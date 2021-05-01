package de.brainsizzle.twitchbotpaint.command

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class CommandDefinitionKtTest {

    @Test
    fun testPrintHelp() {
        initCommands()
        Assertions.assertEquals("circle, line, up, down, left, right, color, delete, rotate, help", printHelp())
    }

}