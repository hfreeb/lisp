package com.desetude.lisp.ast

import com.desetude.lisp.interpreter.Environment
import kotlin.reflect.KFunction2

sealed class Expression {
    object Nil : Expression()
    object T : Expression()
    data class LinkedList(val value: Expression, val next: LinkedList? = null) : Expression()
    data class Symbol(val name: String) : Expression()
    data class Int(val value: kotlin.Int) : Expression()
    data class Float(val value: kotlin.Float) : Expression()
    data class Function(val params: LinkedList, val body: Expression) : Expression()
    data class BuiltInFunction(val handler: KFunction2<Environment, LinkedList?, Expression>) : Expression()
}
