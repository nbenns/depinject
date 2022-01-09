package us.wh.zlayerdemo
package infrastructure.userrepository

import infrastructure.DBClient
import scala.util.Random
import zio.*

private final case class UserInMemoryRepository(client: DBClient) extends UserRepository {
  def findById(id: String): ZIO[Any, Throwable, UserSchema] = {
    val firstName = Random.alphanumeric.take(10).force.mkString
    val lastName = Random.alphanumeric.take(15).force.mkString
    val banned = Random.nextBoolean()

    ZIO.succeed(UserSchema(id, firstName, lastName, banned))
  }

  def save(user: UserSchema): ZIO[Any, Throwable, Unit] = ZIO.unit
}
