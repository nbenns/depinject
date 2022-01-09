package us.wh.zlayerdemo
package domain

import domain.model.{Todo, User}

import zio.*

enum Client {
  case GetUsersTodoList(userId: String)
}

object Client {
  def handle(command: Client): ZIO[UserStore & TodoStore, Throwable, Response] =
    command match {
      case GetUsersTodoList(userId) =>
        for {
          user  <- UserStore.findById(userId)
          _     <- ZIO.fail(new Exception("User is Banned")).when(user.banned)
          todos <- TodoStore.findAllForUser(user)
        } yield Response.TodoListResponse(user, todos)
    }
}
