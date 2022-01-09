package us.wh.zlayerdemo
package domain

import domain.model.User
import infrastructure.userrepository.*

import zio.*

final case class UserStore(repo: UserRepository) {
  private def toModel(userTable: UserSchema): User =
    User(
      id = userTable.id,
      firstName = userTable.firstName,
      lastName = userTable.lastName,
      banned = userTable.banned
    )

  private def fromModel(user: User): UserSchema =
    UserSchema(
      id = user.id,
      firstName = user.firstName,
      lastName = user.lastName,
      banned = user.banned
    )

  def findById(id: String): ZIO[Any, Throwable, User] = repo.findById(id).map(toModel)

  def save(user: User): ZIO[Any, Throwable, Unit] = repo.save(fromModel(user))
}

object UserStore {
  val live: ZLayer[UserRepository, Nothing, UserStore] = UserStore.apply.toLayer

  def findById(id: String): ZIO[UserStore, Throwable, User] =
    ZIO.serviceWithZIO(_.findById(id))

  def save(user: User): ZIO[UserStore, Throwable, Unit] =
    ZIO.serviceWithZIO(_.save(user))
}
