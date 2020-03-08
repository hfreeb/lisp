package com.desetude.lisp.interpreter

import com.desetude.lisp.ast.Expression
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class InterpreterTest {
    @Test
    fun eval() {
        Interpreter.eval(Environment(), Expression.LinkedList(Expression.Symbol("print"),
            Expression.LinkedList(Expression.LinkedList(Expression.Symbol("+"), Expression.LinkedList(Expression.Int(10), Expression.LinkedList(Expression.Int(4000)))))
        ))
    }
}