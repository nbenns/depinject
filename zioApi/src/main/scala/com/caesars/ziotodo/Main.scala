package com.caesars.ziotodo

import com.caesars.ziotodo.adapters.output.{TodoRepoKVStore, UserRepoKVStore}
import com.caesars.ziotodo.domain.model.{Todo, User}
import com.caesars.ziotodo.domain.ports.output.{TodoRepository, UserRepository}
import com.caesars.ziotodo.infra.kvstore.KVStore
import zio._
import zio.magic._
import zio.stm.TMap

object Main extends App {
  type Environment = UserRepository with TodoRepoKVStore

  val userMap: ZLayer[Any, Nothing, Has[TMap[String, User]]] =
    TMap.make[String, User]().commit.toLayer

  val userStore: ZLayer[Has[TMap[String, User]], Nothing, Has[KVStore[String, User]]] =
    ZLayer.fromService((map: TMap[String, User]) => new KVStore(map))

  val todoMap: ZLayer[Any, Nothing, Has[TMap[String, Todo]]] =
    TMap.make[String, Todo]().commit.toLayer

  val todoStore: ZLayer[Has[TMap[String, Todo]], Nothing, Has[KVStore[String, Todo]]] =
    ZLayer.fromService((map: TMap[String, Todo]) => new KVStore(map))

  val userRepository: ZLayer[Has[KVStore[String, User]], Nothing, Has[UserRepository]] =
    ZLayer.fromService((store: KVStore[String, User]) => new UserRepoKVStore(store))

  val todoRepository: ZLayer[Has[KVStore[String, Todo]], Nothing, Has[TodoRepository]] =
    ZLayer.fromService((store: KVStore[String, Todo]) => new TodoRepoKVStore(store))

  def layer: ZLayer[Any, Nothing, Has[UserRepository] with Has[TodoRepository]] =
    ZLayer.wire[Has[UserRepository] with Has[TodoRepository]](
      userMap,
      todoMap,
      userStore,
      todoStore,
      userRepository,
      todoRepository
    )
//    (userMap >>> userStore >>> userRepository) ++ (todoMap >>> todoStore >>> todoRepository)

  override def run(args: List[String]): ZIO[ZEnv, Nothing, ExitCode] =
    ZIO.unit.exitCode
}
