package com.caesars.ziotodo.domain.ports.output

import com.caesars.ziotodo.domain.model.User
import zio._

trait UserRepository {
  def findById(id: String): ZIO[Any, Throwable, User]

  def save(user: User): ZIO[Any, Throwable, Unit]
}

object UserRepository {
  def findById(id: String): ZIO[Has[UserRepository], Throwable, User] =
    ZIO.serviceWith(_.findById(id))

  def save(user: User): ZIO[Has[UserRepository], Throwable, Unit] =
    ZIO.serviceWith(_.save(user))
}
