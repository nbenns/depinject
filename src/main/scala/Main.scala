package us.wh.zlayerdemo

import us.wh.zlayerdemo.infrastructure.DBClient
import us.wh.zlayerdemo.infrastructure.userrepository.UserRepository
import us.wh.zlayerdemo.domain.{UserStore, *}
import us.wh.zlayerdemo.infrastructure.todorepository.TodoRepository
import zio.*
import zio.Console.*

object Main extends ZIOApp {
  override type Environment = UserStore & TodoStore
  override implicit val tag: Tag[Environment] = Tag[Environment]

  override def layer: ZLayer[ZIOAppArgs, Any, UserStore & TodoStore] =
    ZLayer.wire[UserStore & TodoStore](
      DBClient.live,
      UserRepository.inMemory,
      TodoRepository.inMemory,
      UserStore.live,
      TodoStore.live
    )

  override def run: ZIO[ZEnv & Environment, Throwable, Unit] =
    Client
      .handle(Client.GetUsersTodoList("123"))
      .flatMap(printLine(_))
}