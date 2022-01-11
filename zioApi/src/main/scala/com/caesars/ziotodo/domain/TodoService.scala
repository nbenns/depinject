package com.caesars.ziotodo.domain

import java.util.UUID

import com.caesars.ziotodo.domain.model.{Todo, User}
import com.caesars.ziotodo.domain.ports.output.{TodoRepository, UserRepository}
import zio.{Has, ZIO}

final class TodoService(userRepository: UserRepository, todoRepository: TodoRepository) {
  private def userBannedError(userId: String) = new Exception(s"User is banned: $userId")

  def addNewUser(firstName: String, lastName: String): ZIO[Any, Throwable, User] = {
    val id = UUID.randomUUID().toString
    val user = User(id, firstName, lastName, false)

    userRepository.save(user).as(user)
  }

  def banUser(userId: String): ZIO[Any, Throwable, User] =
    for {
      user    <- userRepository.findById(userId)
      updated  = user.copy(banned = true)
      _       <- userRepository.save(updated)
    } yield updated

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

object TodoService {
  def addNewUser(firstName: String, lastName: String): ZIO[Has[TodoService], Throwable, User] =
    ZIO.serviceWith(_.addNewUser(firstName, lastName))

  def banUser(userId: String): ZIO[Has[TodoService], Throwable, User] =
    ZIO.serviceWith(_.banUser(userId))

  def addTodo(userId: String, description: String): ZIO[Has[TodoService], Throwable, Todo] =
    ZIO.serviceWith(_.addTodo(userId, description))

  def getUserTodoList(userId: String): ZIO[Has[TodoService], Throwable, List[Todo]] =
    ZIO.serviceWith(_.getUserTodoList(userId))
}
