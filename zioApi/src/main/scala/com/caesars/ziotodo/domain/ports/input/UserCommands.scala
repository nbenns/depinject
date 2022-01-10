package com.caesars.ziotodo.domain.ports.input

import com.caesars.ziotodo.domain.TodoService
import com.caesars.ziotodo.domain.model.User
import zio.{Has, ZIO}

trait UserCommands {
  def createUser(firstName: String, lastName: String): ZIO[Any, Throwable, User]

  def banUser(id: String): ZIO[Has[TodoService.type], Throwable, User]
}
