package io.github.deanalvero.rn.cmp.parser

import io.github.deanalvero.rn.cmp.data.AttributeValue

object StyleParser {
    fun parse(styleString: String): Map<String, AttributeValue> {
        val trimmed = styleString.trim().removePrefix("{").removeSuffix("}").trim()
        if (trimmed.isEmpty()) return emptyMap()

        val properties = mutableMapOf<String, AttributeValue>()
        var current = 0

        while (current < trimmed.length) {
            while (current < trimmed.length && trimmed[current].isWhitespace()) current++
            if (current >= trimmed.length) break

            val keyStart = current
            while (current < trimmed.length && trimmed[current] != ':') current++
            if (current >= trimmed.length) break

            val key = trimmed.substring(keyStart, current).trim()
                .removeSurrounding("'").removeSurrounding("\"")
            current++

            while (current < trimmed.length && trimmed[current].isWhitespace()) current++

            val value = parseValue(trimmed, current)
            current = value.second

            properties[key] = value.first

            while (current < trimmed.length && (trimmed[current].isWhitespace() || trimmed[current] == ',')) {
                current++
            }
        }

        return properties
    }

    private fun parseValue(input: String, start: Int): Pair<AttributeValue, Int> {
        var current = start

        return when {
            input[current] == '\'' || input[current] == '"' -> {
                val quote = input[current]
                current++
                val valueStart = current
                while (current < input.length && input[current] != quote) current++
                val value = input.substring(valueStart, current)
                current++
                AttributeValue.StringValue(value) to current
            }

            input.substring(current).contains('(') &&
                    input.substring(current).indexOf('(') < input.substring(current).let {
                val commaIdx = it.indexOf(',')
                if (commaIdx == -1) it.length else commaIdx
            } -> {
                val valueStart = current
                var depth = 0
                while (current < input.length) {
                    when (input[current]) {
                        '(' -> depth++
                        ')' -> {
                            depth--
                            if (depth == 0) {
                                current++
                                break
                            }
                        }
                        ',' -> if (depth == 0) break
                    }
                    current++
                }
                val value = input.substring(valueStart, current).trim()
                AttributeValue.StringValue(value) to current
            }

            else -> {
                val valueStart = current
                while (current < input.length && input[current] != ',' && input[current] != '}') {
                    current++
                }
                val value = input.substring(valueStart, current).trim()

                when {
                    value.toIntOrNull() != null -> AttributeValue.NumberValue(value.toInt()) to current
                    value.toDoubleOrNull() != null -> AttributeValue.NumberValue(value.toDouble()) to current
                    value.equals("true", ignoreCase = true) -> AttributeValue.BooleanValue(true) to current
                    value.equals("false", ignoreCase = true) -> AttributeValue.BooleanValue(false) to current
                    else -> AttributeValue.StringValue(value) to current
                }
            }
        }
    }
}
