package com.supotuco.baseproject.command

interface CommandHandler<T> {

    fun execute(command: T)
}