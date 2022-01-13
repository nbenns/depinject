package com.caesars.ziotodo.domain.ports.input

import com.caesars.ziotodo.domain.model.Todo
import zio.{Has, ZIO}

trait TodoCommands {
  def addTodo(userId: String, description: String): ZIO[Any, Throwable, Todo]

  def getUserTodoList(userId: String): ZIO[Any, Throwable, List[Todo]]
}

object TodoCommands {
  def addTodo(userId: String, description: String): ZIO[Has[TodoCommands], Throwable, Todo] =
    ZIO.serviceWith(_.addTodo(userId, description))

  def getUserTodoList(userId: String): ZIO[Has[TodoCommands], Throwable, List[Todo]] =
    ZIO.serviceWith(_.getUserTodoList(userId))
}
