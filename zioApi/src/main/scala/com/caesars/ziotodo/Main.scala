package com.caesars.ziotodo

import com.caesars.ziotodo.adapters.input.{DomainTodoCommands, DomainUserCommands}
import com.caesars.ziotodo.adapters.output.{TodoRepoKVStore, UserRepoKVStore}
import com.caesars.ziotodo.domain.model.{Todo, User}
import com.caesars.ziotodo.domain.ports.input.{TodoCommands, UserCommands}
import com.caesars.ziotodo.domain.ports.output.{TodoRepository, UserRepository}
import com.caesars.ziotodo.infra.kvstore.KVStore
import zio._
import zio.magic._
import zio.stm.TMap

object Main extends App {
  val userMap: ZLayer[Any, Nothing, Has[TMap[String, User]]] =
    TMap.make[String, User]().commit.toLayer

  val userStore: ZLayer[Has[TMap[String, User]], Nothing, Has[KVStore[String, User]]] =
    ZLayer.fromService((map: TMap[String, User]) => new KVStore(map))

  val todoMap: ZLayer[Any, Nothing, Has[TMap[String, Todo]]] =
    TMap.make[String, Todo]().commit.toLayer

  val todoStore: ZLayer[Has[TMap[String, Todo]], Nothing, Has[KVStore[String, Todo]]] =
    ZLayer.fromService((map: TMap[String, Todo]) => new KVStore(map))

  val layer: ZLayer[Any, Nothing, Has[UserCommands] with Has[TodoCommands]] =
    ZLayer.wire[Has[UserCommands] with Has[TodoCommands]](
      userMap,
      todoMap,
      userStore,
      todoStore,
      UserRepoKVStore.asLayer,
      TodoRepoKVStore.asLayer,
      DomainUserCommands.asLayer,
      DomainTodoCommands.asLayer
    )

  override def run(args: List[String]): ZIO[ZEnv, Nothing, ExitCode] =
    ZIO.unit.exitCode
}
