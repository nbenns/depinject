package com.caesars.ziotodo.adapters.output

import com.caesars.ziotodo.domain.model.{Todo, User}
import com.caesars.ziotodo.domain.ports.output.TodoRepository
import com.caesars.ziotodo.infra.kvstore.KVStore
import zio._

final case class TodoRepoKVStore(repo: KVStore[String, Todo]) extends TodoRepository {
  def findById(id: String): ZIO[Any, Throwable, Todo] =
    repo
      .get(id)
      .commit
      .someOrFail(new Exception(s"todo not found: $id"))

  def findAllForUser(user: User): ZIO[Any, Throwable, List[Todo]] =
    repo
      .getWith(_.userId == user.id)
      .commit

  def save(todo: Todo): ZIO[Any, Throwable, Unit] =
    repo
      .set(todo.id, todo)
      .commit
}

object TodoRepoKVStore {
  val live: ZLayer[Has[KVStore[String, Todo]], Nothing, Has[TodoRepoKVStore]] = (TodoRepoKVStore.apply _).toLayer

  def findById(id: String): ZIO[Has[TodoRepoKVStore], Throwable, Todo] =
    ZIO.serviceWith[TodoRepoKVStore](_.findById(id))

  def findAllForUser(user: User): ZIO[Has[TodoRepoKVStore], Throwable, List[Todo]] =
    ZIO.serviceWith(_.findAllForUser(user))

  def save(todo: Todo): ZIO[Has[TodoRepoKVStore], Throwable, Unit] =
    ZIO.serviceWith(_.save(todo))
}
