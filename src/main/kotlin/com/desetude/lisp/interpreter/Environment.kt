package com.desetude.lisp.interpreter

import com.desetude.lisp.ast.Expression

class Environment(private val parent: Environment? = null) {
    private val vars: MutableMap<String, Expression> = mutableMapOf()

    fun lookup(label: String): Expression? {
        val local = vars[label]
        if (local != null) {
            return local
        }

        if (parent != null) {
            val up = parent.lookup(label)
            if (up != null) {
                return up
            }
        }

        return null
    }

    fun set(label: String, expr: Expression) {
        vars[label] = expr
    }
}
