package com.desetude.lisp.interpreter

import com.desetude.lisp.ast.Expression

object BuiltInFunctions {
    fun plus(env: Environment, args: Expression.List?): Expression {
        var total = 0

        var head = args
        while (head != null) {
            val expr = Interpreter.eval(env, head.value)
            require(expr is Expression.Int)
            total += expr.value

            head = head.next
        }

        return Expression.Int(total)
    }

    fun minus(env: Environment, args: Expression.List?): Expression {
        requireNotNull(args) { "Two arguments required for -, none given" }
        val a = Interpreter.eval(env, args.value)
        require(a is Expression.Int)

        val bNode = args.next
        requireNotNull(bNode) { "Two arguments required for -, one given" }
        val b = Interpreter.eval(env, bNode.value)
        require(b is Expression.Int)
        require(bNode.next == null) { "Two many arguments given to -, two required" }

        return Expression.Int(a.value - b.value)
    }

    fun multiply(env: Environment, args: Expression.List?): Expression {
        var product = 1

        var head = args
        while (head != null) {
            val expr = Interpreter.eval(env, head.value)
            require(expr is Expression.Int)
            product *= expr.value

            head = head.next
        }

        return Expression.Int(product)
    }

    fun print(env: Environment, args: Expression.List?): Expression {
        if (args == null) {
            return Expression.Nil
        }

        var head = args
        while (head != null) {
            when (val arg = Interpreter.eval(env, head.value)) {
                is Expression.List -> {
                    print("(")
                    print(env, arg)
                    print(")")
                }
                is Expression.Symbol -> print(arg.name)
                is Expression.Int -> print(arg.value)
                is Expression.Float -> print(arg.value)
            }
            head = head.next
            if (head != null) {
                print(" ")
            }
        }

        println()
        return Expression.T
    }

    fun define(env: Environment, args: Expression.List?): Expression {
        requireNotNull(args) { "define requires three arguments, none given" }
        val name = args.value
        require(name is Expression.Symbol) { "Name must be a symbol" }

        val func = createFunction(args.next)
        env.set(name.name, func)
        return func
    }

    fun lambda(env: Environment, args: Expression.List?): Expression {
        return createFunction(args)
    }

    private fun createFunction(definitionHead: Expression.List?): Expression.Function {
        requireNotNull(definitionHead) { "Parameter list required" }
        val parameters = definitionHead.value
        require(parameters is Expression.List) { "Parameters must be given as a list" }

        val afterParams = definitionHead.next ?: throw IllegalArgumentException("Body of function required")
        val body = afterParams.value
        require(afterParams.next == null) { "Too many arguments given" }

        return Expression.Function(parameters, body)
    }

    fun cond(env: Environment, args: Expression.List?): Expression {
        var cond = args
        while (cond != null) {
            val conditional = cond.value
            require(conditional is Expression.List) { "cond takes pairs of conditions and expressions" }
            val condition = conditional.value
            val afterCond = conditional.next
                ?: throw IllegalStateException("Both a condition and an expression are required for cond")
            val expr = afterCond.value
            require(afterCond.next == null) { "Too many arguments given to cond" }

            if (Interpreter.eval(env, condition) != Expression.Nil) {
                return Interpreter.eval(env, expr)
            }

            cond = cond.next
        }

        return Expression.Nil
    }

    fun equal(env: Environment, args: Expression.List?): Expression {
        requireNotNull(args) { "Two arguments are required for equal, none were given" }
        val first = args.value
        val secondNode = args.next
        requireNotNull(secondNode) { "Two arguments are required for equal, one was given" }
        require(secondNode.next == null) { "Too many arguments given to equal, only two are required" }
        val second = secondNode.value

        val v1 = Interpreter.eval(env, first)
        val v2 = Interpreter.eval(env, second)
        return if (v1 == v2) {
            Expression.T
        } else {
            Expression.Nil
        }
    }

    fun quote(env: Environment, args: Expression.List?): Expression {
        require(args != null && args.next == null) { "quote takes one argument" }
        return args.value
    }

    fun car(env: Environment, args: Expression.List?): Expression {
        require(args != null && args.next == null) { "car expects one arguments" }
        val arg = Interpreter.eval(env, args.value)
        require(arg is Expression.List) { "car requires a list" }
        return arg.value
    }

    fun cdr(env: Environment, args: Expression.List?): Expression {
        require(args != null && args.next == null) { "cdr expects one arguments" }
        val arg = Interpreter.eval(env, args.value)
        require(arg is Expression.List) { "cdr requires a list" }
        return arg.next ?: Expression.Nil
    }
}
