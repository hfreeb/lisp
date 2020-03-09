package com.desetude.lisp.token

object Lexer {
    private val ATOM_PATTERN = Regex("\\A([^\\s()'][^\\s()]*)(\\s|\\(|\\)|$)")

    fun lex(program: String): List<Token> {
        val tokens = mutableListOf<Token>()

        var buffer = program
        while (buffer.isNotEmpty()) {
            val match = ATOM_PATTERN.find(buffer)
            if (match != null) {
                val symbol = match.groupValues[1]
                tokens.add(identifyAtom(symbol))
                buffer = buffer.drop(symbol.length)
                continue
            }

            val char = buffer.first()
            when {
                char == '\'' -> tokens.add(Token.Apostrophe)
                char == '(' -> tokens.add(Token.OpeningParenthesis)
                char == ')' -> tokens.add(Token.ClosingParenthesis)
                char.isWhitespace() -> {}
                else -> throw IllegalStateException("Failed to tokenize character: $char")
            }
            buffer = buffer.drop(1)
        }

        return tokens
    }

    private fun identifyAtom(symbol: String): Token {
        if (symbol.equals("nil", ignoreCase = true)) {
            return Token.Nil
        } else if (symbol.equals("t", ignoreCase = true)) {
            return Token.T
        }

        symbol.toIntOrNull()?.let {
            return Token.IntLiteral(it)
        }
        symbol.toFloatOrNull()?.let {
            return Token.FloatLiteral(it)
        }

        return Token.Symbol(symbol)
    }
}
