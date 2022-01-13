package com.caesars.ziotodo.adapters.input

import com.caesars.ziotodo.domain.model.Todo
import com.caesars.ziotodo.domain.ports.input.TodoCommands
import com.caesars.ziotodo.domain.ports.output.{TodoRepository, UserRepository}
import zio.{Has, ZIO, ZLayer}

import java.util.UUID

final class DomainTodoCommands(userRepository: UserRepository, todoRepository: TodoRepository) extends TodoCommands {
  private def userBannedError(userId: String) = new Exception(s"User is banned: $userId")

  def addTodo(userId: String, description: String): ZIO[Any, Throwable, Todo] =
    for {
      user <- userRepository.findById(userId)
      _    <- ZIO.fail(userBannedError(userId)).when(user.banned)

      id    = UUID.randomUUID().toString
      todo  = Todo(id, userId, description, false)
      _    <- todoRepository.save(todo)
    } yield todo

  def getUserTodoList(userId: String): ZIO[Any, Throwable, List[Todo]] =
    for {
      user  <- userRepository.findById(userId)
      _     <- ZIO.fail(userBannedError(userId)).when(user.banned)
      todos <- todoRepository.findAllForUser(user)
    } yield todos
}

object DomainTodoCommands {
  val asLayer: ZLayer[Has[UserRepository] with Has[TodoRepository], Nothing, Has[TodoCommands]] =
    ZLayer.fromServices[UserRepository, TodoRepository, TodoCommands]((ur: UserRepository, tr: TodoRepository) => new DomainTodoCommands(ur, tr))
}
