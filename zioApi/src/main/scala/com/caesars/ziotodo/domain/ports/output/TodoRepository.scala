package com.caesars.ziotodo.domain.ports.output

import com.caesars.ziotodo.domain.model.{Todo, User}
import zio._

trait TodoRepository {
  def findById(id: String): ZIO[Any, Throwable, Todo]

  def findAllForUser(user: User): ZIO[Any, Throwable, List[Todo]]

  def save(todo: Todo): ZIO[Any, Throwable, Unit]
}

object TodoRepository {
  def findById(id: String): ZIO[Has[TodoRepository], Throwable, Todo] =
    ZIO.serviceWith[TodoRepository](_.findById(id))

  def findAllForUser(user: User): ZIO[Has[TodoRepository], Throwable, List[Todo]] =
    ZIO.serviceWith(_.findAllForUser(user))

  def save(todo: Todo): ZIO[Has[TodoRepository], Throwable, Unit] =
    ZIO.serviceWith(_.save(todo))
}

