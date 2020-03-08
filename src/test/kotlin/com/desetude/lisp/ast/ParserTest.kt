package com.desetude.lisp.ast

import com.desetude.lisp.token.Token
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ParserTest {
    @Test
    fun parse() {
        assertEquals(Parser.parse(listOf(
            Token.OpeningParenthesis, Token.Symbol("print"),
                Token.OpeningParenthesis, Token.Symbol("+"), Token.IntLiteral(10), Token.IntLiteral(4000), Token.ClosingParenthesis,
            Token.ClosingParenthesis
        )), Expression.LinkedList(Expression.Symbol("print"),
            Expression.LinkedList(Expression.LinkedList(Expression.Symbol("+"), Expression.LinkedList(Expression.Int(10), Expression.LinkedList(Expression.Int(4000)))))
        ))
    }
}