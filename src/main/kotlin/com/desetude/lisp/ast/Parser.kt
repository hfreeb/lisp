package com.desetude.lisp.ast

import com.desetude.lisp.token.Token

object Parser {
    fun parse(tokens: List<Token>): Expression {
        return readExpression(TokenIterator(tokens))
    }

    private fun readExpression(tokens: TokenIterator): Expression {
        require(tokens.isNotEmpty()) { "Unexpected EOF" }
        return when (val head = tokens.pop()) {
            is Token.OpeningParenthesis -> {
                val list = readList(tokens)
                require(tokens.pop() is Token.ClosingParenthesis)
                list ?: throw IllegalStateException("You can not create an empty list")
            }
            is Token.ClosingParenthesis -> throw IllegalStateException("Unexpected closing parenthesis")
            is Token.Symbol -> Expression.Symbol(head.name)
            is Token.IntLiteral -> Expression.Int(head.value)
            is Token.FloatLiteral -> Expression.Float(head.value)
            is Token.T -> Expression.T
            is Token.Nil -> Expression.Nil
        }
    }

    private fun readList(tokens: TokenIterator): Expression.List? {
        if (tokens.peak() is Token.ClosingParenthesis) {
            return null
        }

        val head = readExpression(tokens)
        val next = readList(tokens)
        return Expression.List(head, next)
    }
}
