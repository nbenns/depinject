package us.wh.zlayerdemo
package domain

import domain.TodoStore
import domain.model.{Todo, User}

import us.wh.zlayerdemo.infrastructure.todorepository.{TodoRepository, TodoSchema}
import zio.*

final case class TodoStore(repo: TodoRepository) {
  private def toModel(todoSchema: TodoSchema): Todo =
    Todo(
      id = todoSchema.id,
      userId = todoSchema.userId,
      description = todoSchema.description,
      completed = todoSchema.completed
    )

  private def fromModel(todo: Todo): TodoSchema =
    TodoSchema(
      id = todo.id,
      userId = todo.userId,
      description = todo.description,
      completed = todo.completed
    )

  def findById(id: String): ZIO[Any, Throwable, Todo] =
    repo.findById(id).map(toModel)

  def findAllForUser(user: User): ZIO[Any, Throwable, List[Todo]] =
    repo
      .findAllByUserId(user.id)
      .map(_.map(toModel))

  def save(todo: Todo): ZIO[Any, Throwable, Unit] =
    repo.save(fromModel(todo))
}

object TodoStore {
  val live: ZLayer[TodoRepository, Nothing, TodoStore] = TodoStore.apply.toLayer

  def findById(id: String): ZIO[TodoStore, Throwable, Todo] =
    ZIO.serviceWithZIO[TodoStore](_.findById(id))

  def findAllForUser(user: User): ZIO[TodoStore, Throwable, List[Todo]] =
    ZIO.serviceWithZIO(_.findAllForUser(user))

  def save(todo: Todo): ZIO[TodoStore, Throwable, Unit] =
    ZIO.serviceWithZIO(_.save(todo))
}
