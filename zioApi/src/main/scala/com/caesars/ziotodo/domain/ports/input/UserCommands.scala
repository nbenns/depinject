package com.caesars.ziotodo.domain.ports.input

import com.caesars.ziotodo.domain.model.User
import zio.{Has, ZIO}

trait UserCommands {
  def addNewUser(firstName: String, lastName: String): ZIO[Any, Throwable, User]

  def banUser(id: String): ZIO[Any, Throwable, User]
}

object UserCommands {
  def addNewUser(firstName: String, lastName: String): ZIO[Has[UserCommands], Throwable, User] =
    ZIO.serviceWith(_.addNewUser(firstName, lastName))

  def banUser(userId: String): ZIO[Has[UserCommands], Throwable, User] =
    ZIO.serviceWith(_.banUser(userId))
}