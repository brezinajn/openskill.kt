package com.brezinajn.openskill.util

fun interface Getter<S, T> {
    operator fun invoke(s: S): T
}

fun interface Setter<S, T> {
    operator fun invoke(s: S, v: T): S
}