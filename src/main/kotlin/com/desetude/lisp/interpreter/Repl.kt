package com.desetude.lisp.interpreter

import com.desetude.lisp.ast.Expression
import com.desetude.lisp.ast.Parser
import com.desetude.lisp.token.Lexer

fun main() {
    val env = Environment()
    env.set("print", Expression.BuiltInFunction(BuiltInFunctions::print))
    env.set("*", Expression.BuiltInFunction(BuiltInFunctions::multiply))
    env.set("+", Expression.BuiltInFunction(BuiltInFunctions::plus))
    env.set("-", Expression.BuiltInFunction(BuiltInFunctions::minus))
    env.set("define", Expression.BuiltInFunction(BuiltInFunctions::define))
    env.set("lambda", Expression.BuiltInFunction(BuiltInFunctions::lambda))
    env.set("cond", Expression.BuiltInFunction(BuiltInFunctions::cond))
    env.set("equal", Expression.BuiltInFunction(BuiltInFunctions::equal))

    while (true) {
        print("> ")
        val input = readLine() ?: continue
        val tokens = Lexer.lex(input)
        val parsed = Parser.parse(tokens)
        val result = when (val expr = Interpreter.eval(env, parsed)) {
            is Expression.Nil -> "NIL"
            is Expression.T -> "T"
            is Expression.LinkedList -> "<list>"
            is Expression.Symbol -> "<symbol>"
            is Expression.Int -> expr.value.toString()
            is Expression.Float -> expr.value.toString()
            is Expression.Function -> "<function>"
            is Expression.BuiltInFunction -> "<built-in function>"
        }
        println("=> $result")
    }
}
