package com.desetude.lisp.token

sealed class Token {
    object OpeningParenthesis : Token()
    object ClosingParenthesis : Token()
    object T : Token()
    object Nil : Token()

    data class Symbol(val name: String) : Token()

    data class IntLiteral(val value: Int) : Token()
    data class FloatLiteral(val value: Float) : Token()
}