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
    env.set("quote", Expression.BuiltInFunction(BuiltInFunctions::quote))

    while (true) {
        print("> ")
        val input = readLine() ?: continue
        val tokens = Lexer.lex(input)
        val parsed = Parser.parse(tokens)
        val result = Interpreter.eval(env, parsed)
        println("=> ${formatExpression(result)}")
    }
}

private fun formatExpression(expr: Expression): String {
   return when (expr) {
       is Expression.Nil -> "NIL"
       is Expression.T -> "T"
       is Expression.List -> formatList(expr)
       is Expression.Symbol -> expr.name
       is Expression.Int -> expr.value.toString()
       is Expression.Float -> expr.value.toString()
       is Expression.Function -> "<function>"
       is Expression.BuiltInFunction -> "<built-in function>"
   }
}

private fun formatList(list: Expression.List): String {
    val builder = StringBuilder()
    builder.append("(")
    var head: Expression.List? = list
    while (head != null) {
        builder.append(formatExpression(head.value))
        head = head.next
        if (head != null) {
            builder.append(" ")
        }
    }
    builder.append(")")
    return builder.toString()
}
