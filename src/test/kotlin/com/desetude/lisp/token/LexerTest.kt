package com.desetude.lisp.token

import org.junit.jupiter.api.Assertions.*

internal class LexerTest {
    @org.junit.jupiter.api.Test
    fun tokenize() {
        assertEquals(Lexer.lex("(print (+ 10 4000))"), listOf(
            Token.OpeningParenthesis, Token.Symbol("print"),
                Token.OpeningParenthesis, Token.Symbol("+"), Token.IntLiteral(10), Token.IntLiteral(4000), Token.ClosingParenthesis,
            Token.ClosingParenthesis
        ))
    }
}