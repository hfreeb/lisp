package com.desetude.lisp.interpreter

import com.desetude.lisp.ast.Expression
import org.junit.jupiter.api.Test

internal class InterpreterTest {
    @Test
    fun eval() {
        Interpreter.eval(Environment(), Expression.List(Expression.Symbol("print"),
            Expression.List(Expression.List(Expression.Symbol("+"), Expression.List(Expression.Int(10), Expression.List(Expression.Int(4000)))))
        ))
    }
}
