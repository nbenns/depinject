package com.caesars.ziotodo.adapters.output

import com.caesars.ziotodo.domain.model.{Todo, User}
import com.caesars.ziotodo.domain.ports.output.TodoRepository
import com.caesars.ziotodo.infra.kvstore.KVStore
import zio._

final class TodoRepoKVStore(repo: KVStore[String, Todo]) extends TodoRepository {
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
  val asLayer: ZLayer[Has[KVStore[String, Todo]], Nothing, Has[TodoRepository]] =
    ZLayer.fromService(new TodoRepoKVStore(_))
}
