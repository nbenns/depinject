package us.wh.zlayerdemo
package infrastructure.todorepository

import infrastructure.DBClient

import zio.*

trait TodoRepository {
  def findById(id: String): ZIO[Any, Throwable, TodoSchema]
  def findAllByUserId(userId: String): ZIO[Any, Throwable, List[TodoSchema]]
  def save(todo: TodoSchema): ZIO[Any, Throwable, Unit]
}

object TodoRepository {
  val inMemory: URLayer[DBClient, TodoRepository] = TodoInMemoryRepository.apply.toLayer
}