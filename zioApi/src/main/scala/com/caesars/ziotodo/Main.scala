package com.caesars.ziotodo

import com.caesars.ziotodo.adapters.output.TodoRepoKVStore
import com.caesars.ziotodo.domain.Client
import com.caesars.ziotodo.domain.ports.output.UserRepository
import com.caesars.ziotodo.infrastructure.DBClient
import com.caesars.ziotodo.infrastructure.todotable.TodoTable
import com.caesars.ziotodo.infrastructure.usertable.UserTable
import com.ceasars.zioapi.domain.UserStore
import zio.*

object Main extends ZIOApp {
  override type Environment = UserRepository & TodoRepoKVStore
  override implicit val tag: Tag[Environment] = Tag[Environment]

  override def layer: ZLayer[ZIOAppArgs, Any, UserRepository & TodoRepoKVStore] =
    ZLayer.wire[UserRepository & TodoRepoKVStore](
      DBClient.live,
      UserTable.inMemory,
      TodoTable.inMemory,
      UserRepository.live,
      TodoRepoKVStore.live
    )

  override def run: ZIO[ZEnv & Environment, Throwable, Unit] =
    Client
      .handle(Client.GetUsersTodoList("123"))
      .flatMap(printLine(_))
}
