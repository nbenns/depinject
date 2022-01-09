package us.wh.zlayerdemo
package infrastructure.todorepository

import infrastructure.DBClient

import zio.ZIO

import scala.util.Random

private final case class TodoInMemoryRepository(client: DBClient) extends TodoRepository {
  override def findById(id: String): ZIO[Any, Throwable, TodoSchema] = {
    val userId = Random.alphanumeric.take(10).force.mkString
    val description = Random.alphanumeric.take(15).force.mkString
    val completed = Random.nextBoolean()

    ZIO.succeed(TodoSchema(id, userId, description, completed))
  }

  override def findAllByUserId(userId: String): ZIO[Any, Throwable, List[TodoSchema]] = {
    val number = Random.nextInt(8)

    ZIO
      .foreach((1 to (number + 2)).toList) { i =>
        findById(i.toString)
      }
  }

  override def save(todo: TodoSchema): ZIO[Any, Throwable, Unit] = ZIO.unit
}
