package us.wh.zlayerdemo
package infrastructure.userrepository

import infrastructure.DBClient

import zio.*

trait UserRepository {
  def findById(id: String): ZIO[Any, Throwable, UserSchema]

  def save(user: UserSchema): ZIO[Any, Throwable, Unit]
}

object UserRepository {
  val inMemory: URLayer[DBClient, UserRepository] = UserInMemoryRepository.apply.toLayer

  def findById(id: String): ZIO[UserRepository, Throwable, UserSchema] =
    ZIO.serviceWithZIO[UserRepository](_.findById(id))

  def save(user: UserSchema): ZIO[UserRepository, Throwable, Unit] =
    ZIO.serviceWithZIO[UserRepository](_.save(user))
}
