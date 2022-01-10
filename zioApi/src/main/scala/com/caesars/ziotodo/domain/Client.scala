package com.caesars.ziotodo.domain

import com.caesars.ziotodo.adapters.output.TodoRepoKVStore
import com.caesars.ziotodo.domain.ports.output.UserRepository

object Client {
  def handle(command: Client): ZIO[UserRepository & TodoRepoKVStore, Throwable, Response] =
    command match {
      case GetUsersTodoList(userId) =>
        for {
          user  <- UserRepository.findById(userId)
          _     <- ZIO.fail(new Exception("User is Banned")).when(user.banned)
          todos <- TodoRepoKVStore.findAllForUser(user)
        } yield Response.TodoListResponse(user, todos)
    }
}
