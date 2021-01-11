package com.avito.logger.handler

import com.avito.logger.LogLevel

class CombinedHandler(
    private val handlers: Collection<LoggingHandler>
) : LoggingHandler {

    override fun write(level: LogLevel, message: String, error: Throwable?) {
        handlers.forEach { it.write(level, message, error) }
    }
}
