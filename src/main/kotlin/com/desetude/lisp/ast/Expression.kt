package com.desetude.lisp.ast

import com.desetude.lisp.interpreter.Environment
import kotlin.reflect.KFunction2

sealed class Expression {
    object Nil : Expression()
    object T : Expression()
    data class List(val value: Expression, val next: List? = null) : Expression()
    data class Symbol(val name: String) : Expression()
    data class Int(val value: kotlin.Int) : Expression()
    data class Float(val value: kotlin.Float) : Expression()
    data class Function(val params: List, val body: Expression) : Expression()
    data class BuiltInFunction(val handler: KFunction2<Environment, List?, Expression>) : Expression()
}
