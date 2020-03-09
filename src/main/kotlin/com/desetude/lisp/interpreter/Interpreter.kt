package com.desetude.lisp.interpreter

import com.desetude.lisp.ast.Expression

object Interpreter {
    fun eval(env: Environment, expression: Expression): Expression {
        return when (expression) {
            is Expression.Float, is Expression.Int, is Expression.BuiltInFunction, is Expression.Function, is Expression.T, is Expression.Nil -> expression
            is Expression.Symbol -> env.lookup(expression.name) ?: throw IllegalStateException("Variable with label ${expression.name} not found")
            is Expression.LinkedList -> {
                when(val func = eval(env, expression.value)) {
                    is Expression.BuiltInFunction -> func.handler(env, expression.next)
                    is Expression.Function -> callFunction(env, func, expression.next)
                    else -> throw IllegalStateException("First value in any s-expression must be a function")
                }
            }
        }
    }

    private fun callFunction(env: Environment, func: Expression.Function, args: Expression.LinkedList?): Expression {
        val newEnv = Environment(env)
        var headParams: Expression.LinkedList? = func.params
        var headArgs = args
        while (headParams != null && headArgs != null) {
            val param = headParams.value
            val arg = headArgs.value
            require(param is Expression.Symbol)

            newEnv.set(param.name, eval(env, arg))

            headParams = headParams.next
            headArgs = headArgs.next
        }

        require(headParams == null && headArgs == null) { "Mismatch between number of parameters and number of arguments" }
        return eval(newEnv, func.body)
    }
}