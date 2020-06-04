package com.worldline.multiplatform.domain.model

sealed class Either<L, R> {

    val isRight get() = this is Right<L, R>
    val isLeft get() = this is Left<L, R>

    class Left<L, R>(val error: L) : Either<L, R>() {
        override fun toString(): String = "Left $error"
    }

    class Right<L, R>(val success: R) : Either<L, R>() {
        override fun toString(): String = "Right $success"
    }

    suspend infix fun <Rp> map(f: suspend (R) -> Rp): Either<L, Rp> {
        return when (this) {
            is Left -> Left(this.error)
            is Right -> Right(f(this.success))
        }
    }

    suspend infix fun <Rp> flatMap(f: suspend (Right<L, R>) -> Either<L, Rp>): Either<L, Rp> {
        return when (this) {
            is Left -> Left(error)
            is Right -> f(this)
        }
    }

    fun fold(error: (L) -> Unit, success: (R) -> Unit) {
        when (this) {
            is Left -> error(this.error)
            is Right -> success(this.success)
        }
    }

    fun <L, R> Either<L, R>.getOrElse(value: R): R =
        when (this) {
            is Either.Left -> value
            is Either.Right -> success
        }
}
