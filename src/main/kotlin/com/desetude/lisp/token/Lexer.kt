package com.desetude.lisp.token

object Lexer {
    private val ATOM_PATTERN = Regex("\\A([^\\s()]+)(\\s|\\(|\\)|$)")

    fun lex(program: String): List<Token> {
        val tokens = mutableListOf<Token>()

        var buffer = program
        while (buffer.isNotEmpty()) {
            val match = ATOM_PATTERN.find(buffer)
            if (match != null) {
                val symbol = match.groupValues[1]
                if (symbol.equals("nil", ignoreCase = true)) {
                    tokens.add(Token.Nil)
                } else if (symbol.equals("t", ignoreCase = true)) {
                    tokens.add(Token.T)
                } else {
                    val intValue = symbol.toIntOrNull()
                    if (intValue != null) {
                        tokens.add(Token.IntLiteral(intValue))
                    } else {
                        val floatValue = symbol.toFloatOrNull()
                        if (floatValue != null) {
                            tokens.add(Token.FloatLiteral(floatValue))
                        } else {
                            tokens.add(Token.Symbol(symbol))
                        }
                    }
                }

                buffer = buffer.drop(symbol.length)
                continue
            }

            val char = buffer[0]
            when {
                char == '(' -> tokens.add(Token.OpeningParenthesis)
                char == ')' -> tokens.add(Token.ClosingParenthesis)
                char.isWhitespace() -> {
                }
                else -> throw IllegalStateException("Failed to tokenize character: $char")
            }
            buffer = buffer.drop(1)
        }

        return tokens
    }
}