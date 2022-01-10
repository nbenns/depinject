package com.caesars.ziotodo.domain

import java.util.UUID

import com.caesars.ziotodo.domain.model.{Todo, User}
import com.caesars.ziotodo.domain.ports.output.{TodoRepository, UserRepository}
import zio.{Has, ZIO}

final class TodoService {
  def userBannedError(userId: String) = new Exception(s"User is banned: $userId")

  def addNewUser(firstName: String, lastName: String): ZIO[Has[UserRepository], Throwable, User] = {
    val id = UUID.randomUUID().toString
    val user = User(id, firstName, lastName, false)

    UserRepository.save(user).as(user)
  }

  def banUser(userId: String): ZIO[Has[UserRepository], Throwable, User] =
    for {
      user    <- UserRepository.findById(userId)
      updated  = user.copy(banned = true)
      _       <- UserRepository.save(updated)
    } yield updated

  def addTodo(userId: String, description: String): ZIO[Has[TodoRepository] with Has[UserRepository], Throwable, Todo] =
    for {
      user <- UserRepository.findById(userId)
      _    <- ZIO.fail(userBannedError(userId)).when(user.banned)

      id   = UUID.randomUUID().toString
      todo = Todo(id, userId, description, false)
      _    <- TodoRepository.save(todo)
    } yield todo

  def getTodosForUser(userId: String): ZIO[Has[TodoRepository] with Has[UserRepository], Throwable, List[Todo]] =
    for {
      user  <- UserRepository.findById(userId)
      _     <- ZIO.fail(userBannedError(userId)).when(user.banned)
      todos <- TodoRepository.findAllForUser(user)
    } yield todos
}
