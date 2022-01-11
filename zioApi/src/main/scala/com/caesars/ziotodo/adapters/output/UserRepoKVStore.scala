package com.caesars.ziotodo.adapters.output

import com.caesars.ziotodo.domain.model.User
import com.caesars.ziotodo.domain.ports.output.UserRepository
import com.caesars.ziotodo.infra.kvstore.KVStore
import zio.ZIO

class UserRepoKVStore(repo: KVStore[String, User]) extends UserRepository {
  override def findById(id: String): ZIO[Any, Throwable, User] =
    repo
      .get(id)
      .commit
      .someOrFail(new Exception(s"User not found: $id"))

  override def save(user: User): ZIO[Any, Throwable, Unit] =
    repo
      .set(user.id, user)
      .commit
}
