package com.desetude.lisp.ast

import com.desetude.lisp.token.Token

class TokenIterator(private val tokens: List<Token>) {
    private var i = 0

    fun peak(): Token {
        return tokens[i]
    }

    fun pop(): Token {
        return tokens[i++]
    }

    fun isNotEmpty(): Boolean {
        return i < tokens.size
    }
}